package com.puj.proyectoensenarte.learning

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.profile.ZoomInsigniaActivity
import java.text.SimpleDateFormat
import java.util.*

class Lesson5Activity : AppCompatActivity() {

    private var currentExerciseIndex = 1
    private var totalPoints = 0
    private var previousXpPoints = 0
    private var lessonStartTime: Long = 0 // T@iempo de inicio de la lección@
    private var streakDays = 0
    private var errorCount = 0

    private val lessonName = "Lección 5"
    private lateinit var exercises: Map<String, Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        canAccessLesson(lessonNumber = 5) { canAccess ->
            if (canAccess) {
                // Permitir el acceso, continuar con la configuración de la lección
                lessonStartTime = System.currentTimeMillis() // Registrar la hora de inicio@

                // Cargar los puntos actuales del usuario antes de iniciar la lección
                loadUserInfo { xpPoints, streak ->
                    previousXpPoints = xpPoints
                    streakDays = streak
                    loadLessonFromFirebase()
                }
            } else {
                // Bloquear el acceso y mostrar un mensaje al usuario@
                Toast.makeText(this, "Completa las lecciones anteriores para acceder a esta lección.", Toast.LENGTH_SHORT).show()
                finish() // Finalizar la actividad para que el usuario no pueda acceder a la lección
            }
        }
    }

    private fun canAccessLesson(lessonNumber: Int, onSuccess: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            val highestLessonCompleted = document.getLong("highestLessonCompleted")?.toInt() ?: 0

            // Permitir el acceso solo si el progreso alcanza el requisito
            onSuccess(lessonNumber <= highestLessonCompleted + 1)
        }.addOnFailureListener { e ->
            Log.e("LessonActivity", "Error al verificar el acceso a la lección", e)
            onSuccess(false)
        }
    }

    private fun loadUserInfo(onSuccess: (Int, Int) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val xpPoints = document.getLong("xpPoints")?.toInt() ?: 0
                        val streak = document.getLong("streakDays")?.toInt() ?: 0
                        onSuccess(xpPoints, streak)
                    } else {
                        onSuccess(0, 0)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("lesson5Activity", "Error al cargar la información del usuario", e)
                    onSuccess(0, 0)
                }
        } else {
            onSuccess(0, 0)
        }
    }

    private fun updateUserStreak() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        // Formato de fecha
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date()) // Fecha de hoy

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val lastExerciseDate = document.getString("lastExerciseDate") ?: ""
                var previousStreakDays = document.getLong("streakDays")?.toInt() ?: 0

                // Caso 1: El último ejercicio fue hoy
                if (lastExerciseDate == todayDate) {
                    // Si por alguna razón `streakDays` está en 0, lo ponemos en 1
                    if (previousStreakDays == 0) {
                        previousStreakDays = 1
                        userRef.update("streakDays", previousStreakDays)
                            .addOnSuccessListener {
                                Log.d("lesson5Activity", "Racha iniciada en 1 para el día de hoy.")
                            }
                            .addOnFailureListener { e ->
                                Log.e("lesson5Activity", "Error al actualizar la racha para el día de hoy", e)
                            }
                    } else {
                        Log.d("lesson5Activity", "El usuario ya completó un ejercicio hoy. Racha actual: $previousStreakDays días.")
                    }
                } else {
                    // Caso 2: El último ejercicio fue ayer o en una fecha anterior
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, -1)
                    val yesterdayDate = dateFormat.format(calendar.time)

                    // Si el último ejercicio fue ayer, incrementamos la racha; si no, la reiniciamos a 1
                    streakDays = if (lastExerciseDate == yesterdayDate) {
                        previousStreakDays + 1
                    } else {
                        1
                    }

                    // Actualizar la base de datos con la nueva racha y la fecha del último ejercicio
                    userRef.update(
                        mapOf(
                            "streakDays" to streakDays,
                            "lastExerciseDate" to todayDate
                        )
                    ).addOnSuccessListener {
                        Log.d("lesson5Activity", "Racha actualizada exitosamente: $streakDays días.")
                    }.addOnFailureListener { e ->
                        Log.e("lesson5Activity", "Error al actualizar la racha", e)
                    }
                }
            } else {
                Log.e("lesson5Activity", "No se encontraron datos del usuario en Firestore.")
            }
        }.addOnFailureListener { e ->
            Log.e("lesson5Activity", "Error al obtener los datos del usuario", e)
        }
    }

    private fun updateUserPointsForLesson(lessonName: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                // Obtener el estado de completado sin errores y los puntos actuales
                val completedWithoutErrors = document.getBoolean("${lessonName}_completedWithoutErrors") ?: false
                val previousXpPoints = document.getLong("xpPoints")?.toInt() ?: 0
                val pointsToAdd = totalPoints

                // Caso 1: Si ya fue completada sin errores anteriormente, no se suman más puntos@
                if (completedWithoutErrors) {

                    val updatedPoints = previousXpPoints + 5
                    userRef.update(
                        mapOf(
                            "xpPoints" to updatedPoints,
                            "${lessonName}_completedWithoutErrors" to true
                        )
                    ).addOnSuccessListener {
                        Log.d("lesson5Activity", "Puntos actualizados: $updatedPoints y lección marcada como completada por repasar.")
                    }.addOnFailureListener { e ->
                        Log.e("lesson5Activity", "Error al actualizar puntos y marca de lección", e)
                    }
                    return@addOnSuccessListener
                }


                // Caso 2: Si se completa ahora sin errores y no estaba previamente marcada como tal@
                if (errorCount == 0) {
                    val updatedPoints = previousXpPoints + pointsToAdd
                    userRef.update(
                        mapOf(
                            "xpPoints" to updatedPoints,
                            "${lessonName}_completedWithoutErrors" to true
                        )
                    ).addOnSuccessListener {
                        Log.d("lesson5Activity", "Puntos actualizados: $updatedPoints y lección marcada como completada sin errores.")
                    }.addOnFailureListener { e ->
                        Log.e("lesson5Activity", "Error al actualizar puntos y marca de lección", e)
                    }
                } else {
                    // Caso 3: Si la lección se completa con errores, solo sumamos puntos esta vez
                    val updatedPoints = previousXpPoints + pointsToAdd
                    userRef.update("xpPoints", updatedPoints)
                        .addOnSuccessListener {
                            Log.d("lesson5Activity", "Puntos por lección completada con errores actualizados: $updatedPoints.")
                        }.addOnFailureListener { e ->
                            Log.e("lesson5Activity", "Error al actualizar puntos para lección completada con errores", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
            Log.e("lesson5Activity", "Error al obtener datos del usuario", e)
        }
    }

    private fun loadLessonFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("lessons").document("lesson5")

        lessonRef.get().addOnSuccessListener { document ->
            if (document != null) {
                exercises = document.get("exercises") as Map<String, Map<String, Any>>
                loadNextExercise()
            } else {
                Log.e("lesson5Activity", "Documento no encontrado en Firestore")
            }
        }.addOnFailureListener { exception ->
            Log.e("LessonActivity", "Error fetching lesson", exception)
        }
    }

    private fun loadNextExercise() {
        val currentExerciseKey = "exercise$currentExerciseIndex"
        Log.d("lesson5Activity", "Intentando cargar ejercicio: $currentExerciseKey")

        if (exercises.containsKey(currentExerciseKey)) {
            val exercise = exercises[currentExerciseKey]!!
            val exerciseType = exercise["exerciseType"] as String
            Log.d("lesson5Activity", "Tipo de ejercicio: $exerciseType")


            when (exerciseType) {
                "video_selection" -> launchExercise1(exercise)
                "ordering" -> launchOrderingExercise(exercise)
                "matching" -> launchMatchingExercise(exercise)
                "matching_videos" -> launchMatchingVideosExercise(exercise)
                "selection" -> launchSelectWordExercise(exercise)
                "selection2" -> launchSelectWordExercise2(exercise)
                else -> Toast.makeText(this, "Tipo de ejercicio no soportado: $exerciseType", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Lección completada; revisar si se debe otorgar pantalla de puntos@
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(uid)

            userRef.get().addOnSuccessListener { document ->
                val completedWithoutErrors = document.getBoolean("lesson5_completedWithoutErrors") ?: false

                if (!completedWithoutErrors && errorCount > 0) {
                    // Solo mostrar pantalla si esta es una completación válida para otorgar puntos@
                    Toast.makeText(this, "Lección completada con $totalPoints puntos", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LeccionTerminadaActivity::class.java)
                    intent.putExtra("totalPoints", totalPoints)
                    startActivity(intent)
                }
                else if (completedWithoutErrors && errorCount >= 0) {
                    val intent = Intent(this, LeccionRepasadaActivity::class.java)
                    intent.putExtra("totalPoints", 5)
                    startActivity(intent)
                }


                // Actualizar fecha de finalización de la lección@
                updateCompletionDate("lesson5")

                // Actualizar puntos y racha según el caso
                updateUserStreak()
                if(streakDays>=2){
                    checkInsigniaConstancia()
                }
                updateUserPointsForLesson("lesson5")
                if (errorCount == 0) {
                    checkAchievements()
                }
                updateFinishedFirstTime()
                updatePerfectLessonCount()
                checkExperiencia()
                checkColeccionador()
                checkDedicatedExplorerBadge("caribe")
                updateFinishedFirstTime()
                incrementReviewCounter()
                updateLevelCompletion(5)
                updateProgress(5)
                finish()
            }.addOnFailureListener { e ->
                Log.e("lesson5Activity", "Error al verificar lección completada: ", e)
            }
        }
    }

    //para no dejar que haga lecciones  más avanzadas que la esta
    private fun updateProgress(lessonNumber: Int) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            val highestLessonCompleted = document.getLong("highestLessonCompleted")?.toInt() ?: 0

            // Si la lección actual es mayor que el progreso registrado, actualizamos
            if (lessonNumber > highestLessonCompleted) {
                userRef.update("highestLessonCompleted", lessonNumber)
                    .addOnSuccessListener {
                        Log.d("LessonActivity", "Progreso actualizado a la lección $lessonNumber")
                    }
                    .addOnFailureListener { e ->
                        Log.e("LessonActivity", "Error al actualizar el progreso", e)
                    }
            }
        }.addOnFailureListener { e ->
            Log.e("LessonActivity", "Error al obtener datos del usuario", e)
        }
    }


    private fun updateLevelCompletion(lessonId: Int) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                var fieldToUpdate = ""
                when (lessonId) {
                    in 1..3 -> fieldToUpdate = "num_lessons_andina" // Nivel Andino
                    in 4..6 -> fieldToUpdate = "num_lessons_caribe" // Nivel Caribe
                    in 7..9 -> fieldToUpdate = "num_lessons_amazonas" // Nivel Amazonas
                }

                if (fieldToUpdate.isNotEmpty()) {
                    userRef.update(fieldToUpdate, FieldValue.increment(1))
                        .addOnSuccessListener {
                            Log.d("LessonCompletion", "$fieldToUpdate incrementado correctamente")
                            checkLevelCompletionAndUnlockInsignia()
                        }
                        .addOnFailureListener { e ->
                            Log.e("LessonCompletion", "Error al incrementar $fieldToUpdate", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
            Log.e("LessonCompletion", "Error al obtener los datos del usuario", e)
        }
    }

    private fun checkLevelCompletionAndUnlockInsignia() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                // Obtener las lecciones completadas sin errores como booleanos
                val lesson1CompletedWithoutErrors = document.getBoolean("lesson1_completedWithoutErrors") ?: false
                val lesson2CompletedWithoutErrors = document.getBoolean("lesson2_completedWithoutErrors") ?: false
                val lesson3CompletedWithoutErrors = document.getBoolean("lesson3_completedWithoutErrors") ?: false
                val lesson4CompletedWithoutErrors = document.getBoolean("lesson4_completedWithoutErrors") ?: false
                val lesson5CompletedWithoutErrors = document.getBoolean("lesson5_completedWithoutErrors") ?: false
                val lesson6CompletedWithoutErrors = document.getBoolean("lesson6_completedWithoutErrors") ?: false
                val lesson7CompletedWithoutErrors = document.getBoolean("lesson7_completedWithoutErrors") ?: false
                val lesson8CompletedWithoutErrors = document.getBoolean("lesson8_completedWithoutErrors") ?: false
                val lesson9CompletedWithoutErrors = document.getBoolean("lesson9_completedWithoutErrors") ?: false

                // Verificar si se ha completado el nivel Andino (lesson1, lesson2, lesson3)
                if (lesson1CompletedWithoutErrors && lesson2CompletedWithoutErrors && lesson3CompletedWithoutErrors) {
                    unlockInsignia("Insignia de la sabiduría andina")
                }
                // Verificar si se ha completado el nivel Caribe (lesson4, lesson5, lesson6)
                if (lesson4CompletedWithoutErrors && lesson5CompletedWithoutErrors && lesson6CompletedWithoutErrors) {
                    unlockInsignia("Insignia del navegante caribeño")
                }
                // Verificar si se ha completado el nivel Amazonas (lesson7, lesson8, lesson9)
                if (lesson7CompletedWithoutErrors && lesson8CompletedWithoutErrors && lesson9CompletedWithoutErrors) {
                    unlockInsignia("Insignia del explorador amazónico")
                }
            }
        }.addOnFailureListener { e ->
            Log.e("CheckLevelCompletion", "Error al verificar el progreso de nivel", e)
        }
    }

    private fun updateCompletionDate(lessonId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("users").document(uid).collection("completedLessons").document(lessonId)

        // Obtener la fecha actual y formatearla a "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())

        val completionData = mapOf(
            "completionDate" to formattedDate // Guardar la fecha en el formato deseado
        )

        lessonRef.set(completionData) // Usa `set` para crear o actualizar el documento
            .addOnSuccessListener {
                Log.d("LessonActivity", "Fecha de finalización de $lessonId actualizada correctamente.")
            }
            .addOnFailureListener { e ->
                Log.e("LessonActivity", "Error al actualizar la fecha de finalización para $lessonId", e)
            }
    }

    private fun incrementReviewCounter() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            val finishedFirstTime = document.getBoolean("lesson5_completedWithoutErrors") ?: false

            if (finishedFirstTime) {
                userRef.update("num_practice_lessons", FieldValue.increment(1))
                    .addOnSuccessListener {
                        Log.d("lesson5Activity", "Contador de lección práctica incrementado.")

                        // Verificar si ha alcanzado el número para desbloquear la insignia@
                        userRef.get().addOnSuccessListener { updatedDoc ->
                            val practiceCount = updatedDoc.getLong("num_practice_lessons") ?: 0
                            if (practiceCount >= 3) {
                                unlockInsignia("Insignia de revisión")
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("lesson5Activity", "Error al incrementar el contador de lección práctica", e)
                    }
            }
        }.addOnFailureListener { e ->
            Log.e("lesson5Activity", "Error al verificar 'finished_first_time'", e)
        }
    }

    private fun updateFinishedFirstTime() {
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("lessons").document("lesson5")

        // Actualizar el campo solo si es la primera vez que se completa la lección@
        lessonRef.update("finished_first_time", true)
            .addOnSuccessListener {
                Log.d("lesson5Activity", "Campo finished_first_time actualizado correctamente en Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("lesson5Activity", "Error al actualizar el campo finished_first_time en Firestore", e)
            }
    }

    private fun updatePerfectLessonCount() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val currentPerfectLessons = document.getLong("num_perfect_lessons")?.toInt() ?: 0

                // Incrementar contador si la lección se completó sin errores (independienteme@nte de si es la primera vez o no)
                if (errorCount == 0) {
                    val updatedPerfectLessons = currentPerfectLessons + 1
                    userRef.update("num_perfect_lessons", updatedPerfectLessons)
                        .addOnSuccessListener {
                            Log.d("lesson5Activity", "Lecciones perfectas actualizadas: $updatedPerfectLessons")

                            // Verificar si el usuario ha alcanzado 5 lecciones perfecta@s para desbloquear la insignia
                            if (updatedPerfectLessons >= 5) {
                                unlockInsignia("Insignia de Excelencia")
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("lesson5Activity", "Error al actualizar el contador de lecciones perfectas", e)
                        }
                }
            }
        }.addOnFailureListener { e ->
            Log.e("lesson5Activity", "Error al obtener los datos del usuario", e)
        }
    }


    private fun checkAchievements() {
        checkAprendizRapido()
        //checkExplorador()
        checkLeccionPerfecta()
        //checkRevisor()
        //checkNaveganteCaribe()
        //checkSabiduriaAndina()
        //checkExploradorAmazonico()
    }

    private fun checkAprendizRapido() {
        // Calcular el tiempo en segundos
        val lessonEndTime = System.currentTimeMillis()
        val elapsedTime = (lessonEndTime - lessonStartTime) / 1000 // Tiempo en segundos@

        Log.d("lesson5Activity", "Tiempo transcurrido en la lección: $elapsedTime segundos")

        // Verificar si el tiempo es menor o igual a 5 minutos (300 segundos)
        if (elapsedTime <= 300) {
            unlockInsignia("Insignia de Aprendiz Rápido")
        }
    }
    private fun checkLeccionPerfecta() {
        if (errorCount == 0) {
            unlockInsignia("Insignia Lección Perfecta")
        }
    }
    private fun checkExperiencia() {
        if (totalPoints >= 50) {
            unlockInsignia("Insignia de Experiencia")
        }
    }

    private fun checkColeccionador(){
        unlockInsignia("Insignia coleccionador de señas")
    }

    private fun checkInsigniaConstancia() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        // Formato de fecha
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date()) // Fecha de hoy

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val lastExerciseDate = document.getString("lastExerciseDate") ?: ""
                var streakDays = document.getLong("streakDays")?.toInt() ?: 0

                // Calcular la fecha de ayer
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -1)
                val yesterdayDate = dateFormat.format(calendar.time)

                // Si el usuario realizó la actividad ayer o hoy, no se reinicia la racha
                if (lastExerciseDate == yesterdayDate || lastExerciseDate == todayDate) {
                    // No se hace nada aquí, racha continua
                } else {
                    // Si el último ejercicio no fue ayer ni hoy, reiniciar la racha
                    streakDays = 1
                    userRef.update("streakDays", streakDays)
                }

                // Otorgar insignia de constancia si alcanzó los 5 días consecutivos@
                if (streakDays >= 2) {
                    unlockInsignia("Insignia de Constancia")
                }
            }
        }.addOnFailureListener { e ->
            Log.e("ZoomInsigniaActivity", "Error al obtener los datos del usuario", e)
        }
    }

    private fun checkDedicatedExplorerBadge(level: String) {
        Log.e("lesson5Activity", "ENTRA A VERIFICAR INSIGNIA EXPLORADOR")

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        // Determinar el rango de lecciones por nivel
        val levelLessons = when (level) {
            "andino" -> listOf("lesson1", "lesson2", "lesson3")
            "caribe" -> listOf("lesson4", "lesson5", "lesson6")
            "amazonico" -> listOf("lesson7", "lesson8", "lesson9")
            else -> return // Salir si el nivel no es válido
        }

        // Variable para almacenar las fechas de finalización de las lecciones en este nivel@
        val completionDates = mutableListOf<String>()

        // Iterar sobre cada lección en el nivel y obtener la fecha de finalización
        for (lesson in levelLessons) {
            userRef.collection("completedLessons").document(lesson).get()
                .addOnSuccessListener { document ->
                    val completionDate = document.getString("completionDate")
                    if (completionDate != null) {
                        completionDates.add(completionDate)
                    }

                    // Cuando hemos revisado todas las lecciones en el nivel, verificamos si las fechas coinciden
                    if (completionDates.size == levelLessons.size) {
                        // Verificar si todas las fechas de finalización son iguales
                        val allDatesMatch = completionDates.distinct().size == 1
                        if (allDatesMatch) {
                            unlockInsignia("Insignia de explorador dedicado")
                            Log.d("lesson5ActivityEXPLORER", "Insignia de explorador dedicado desbloqueada")
                        } else {
                            Log.d("lesson5ActivityEXPLORER", "No todas las lecciones están completadas en la misma fecha para desbloquear la insignia")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("lesson5ActivityEXPLORER", "Error al verificar lección $lesson", e)
                }
        }
    }

    private fun unlockInsignia(insigniaName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val insigniaCollectionRef = db.collection("users").document(uid).collection("insignias")

        insigniaCollectionRef.whereEqualTo("name", insigniaName).get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    val insigniaDoc = result.documents[0]
                    val status = insigniaDoc.getString("status")

                    // Verificar si la insignia ya está activada
                    if (status == "activated") {
                        Log.d("lesson5Activity", "Insignia $insigniaName ya está activada. No se requiere desbloquear nuevamente.")
                        return@addOnSuccessListener
                    }

                    // Si no está activada, procede a activarla
                    val insigniaImageUrl = insigniaDoc.getString("url") ?: ""
                    val insigniaDescription = insigniaDoc.getString("description") ?: ""

                    insigniaDoc.reference.update("status", "activated")
                        .addOnSuccessListener {
                            Log.d("lesson5Activity", "Insignia $insigniaName activada correctamente.")

                            // Lanzar la actividad de ZoomInsigniaActivity con la descripción y URL correcta
                            val intent = Intent(this, ZoomInsigniaActivity::class.java).apply {
                                putExtra("insignia_name", insigniaName)
                                putExtra("insignia_image", insigniaImageUrl)
                                putExtra("insigniaDescription", insigniaDescription)
                            }
                            startActivity(intent)

                            Toast.makeText(this, "Insignia $insigniaName activada.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error al activar la insignia $insigniaName", e)
                            Toast.makeText(this, "Error al activar la insignia $insigniaName.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Log.d("lesson5Activity", "No se encontró la insignia con el nombre $insigniaName.")
                    Toast.makeText(this, "No se encontró la insignia con el nombre $insigniaName.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error al buscar la insignia $insigniaName", e)
                Toast.makeText(this, "Error al buscar la insignia $insigniaName.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun launchExercise1(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise1Activity::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putStringArrayListExtra("videos", ArrayList(exercise["videos"] as List<String>))
        intent.putExtra("lessonName", lessonName) // Usa la constante@ lessonName definida en esta actividad
        startActivityForResult(intent, 1)
    }
    private fun launchMatchingVideosExercise(exercise: Map<String, Any>) {
        Log.d("lesson5Activity", "Intentando llamar al launch con: $exercise") // Verifica los datos

        val intent = Intent(this, ActivityExercise2::class.java)
        intent.putExtra("statement", exercise["statement"] as? String)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)
        intent.putExtra("lessonName", lessonName)
        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", java.util.ArrayList(correctPairs))

        startActivityForResult(intent, 6)
    }

    private fun launchOrderingExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExercise5::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("maxLetters", (exercise["maxLetter"] as Long).toInt())
        intent.putStringArrayListExtra("correctAnswer", ArrayList(exercise["correctAnswer"] as List<String>))
        intent.putExtra("lessonName", lessonName)
        intent.putStringArrayListExtra("videos", ArrayList(exercise["videos"] as List<String>))
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        startActivityForResult(intent, 2)
    }

    private fun launchMatchingExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExcercise4::class.java)
        intent.putExtra("statement", exercise["statement"] as? String)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)
        intent.putExtra("lessonName", lessonName)
        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", ArrayList(correctPairs))

        startActivityForResult(intent, 3)
    }




    private fun launchSelectWordExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        Log.d("lesson5Activity", "Iniciando Exercise3Activity con datos: $exercise") // Verifica los datos

        // Manejo seguro de los datos obtenidos@
        val statement = exercise["statement"] as? String ?: "Pregunta no disponible"
        val correctAnswer = exercise["correctAnswer"] as? String ?: "Respuesta no disponible"
        val points = (exercise["points"] as? Long)?.toInt() ?: 0
        val videoUrl = exercise["video_url"] as? String ?: ""
        val words = exercise["words"] as? List<String> ?: emptyList()

        // Agregar los valores al intent@
        intent.putExtra("statement", statement)
        intent.putExtra("correctAnswer", correctAnswer)
        intent.putExtra("points", points)
        intent.putExtra("video_url", videoUrl)
        intent.putStringArrayListExtra("words", ArrayList(words))
        intent.putExtra("lessonName", lessonName)
        startActivityForResult(intent, 4)
    }
    private fun launchSelectWordExercise2(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        Log.d("lesson5Activity", "Iniciando Exercise3Activity con datos: $exercise") // @Verifica los datos

        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("video_url", exercise["video_url"] as String)
        intent.putStringArrayListExtra("words", ArrayList(exercise["words"] as List<String>))
        intent.putExtra("lessonName", lessonName)
        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("lesson5Activity", "onActivityResult llamado con requestCode=$requestCode, resultCode=$resultCode")

        if (resultCode == RESULT_OK) {
            val pointsEarned = data?.getIntExtra("pointsEarned", 0) ?: 0
            totalPoints += pointsEarned
            Log.d("VERIFICAR PUNTOS", "Puntos acumulados: $totalPoints")
        } else {
            // Incrementar contador de errores si la respuesta es incorrecta
            errorCount++
            Log.d("lesson5Activity", "Respuesta incorrecta. Contador de errores: $errorCount")
        }

        currentExerciseIndex++
        loadNextExercise()
    }



}