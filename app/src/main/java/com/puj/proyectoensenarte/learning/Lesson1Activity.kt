package com.puj.proyectoensenarte.learning

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.profile.ZoomInsigniaActivity
import java.text.SimpleDateFormat
import java.util.*

class Lesson1Activity : AppCompatActivity() {

    private var currentExerciseIndex = 1
    private var totalPoints = 0
    private var previousXpPoints = 0
    private var lessonStartTime: Long = 0 // Tiempo de inicio de la lección@
    private var streakDays = 0
    private var errorCount = 0
    private lateinit var exercises: Map<String, Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registrar la hora de inicio de la lección
        lessonStartTime = System.currentTimeMillis()

        // Cargar los puntos actuales del usuario antes de iniciar la lección
        loadUserInfo { xpPoints, streak ->
            previousXpPoints = xpPoints
            streakDays = streak
            loadLessonFromFirebase()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Si deseas que no haga nada al presionar el botón de retroceso,
        // deja el método vacío.
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
                    Log.e("Lesson1Activity", "Error al cargar la información del usuario", e)
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
                                Log.d("Lesson1Activity", "Racha iniciada en 1 para el día de hoy.")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Lesson1Activity", "Error al actualizar la racha para el día de hoy", e)
                            }
                    } else {
                        Log.d("Lesson1Activity", "El usuario ya completó un ejercicio hoy. Racha actual: $previousStreakDays días.")
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
                        Log.d("Lesson1Activity", "Racha actualizada exitosamente: $streakDays días.")
                    }.addOnFailureListener { e ->
                        Log.e("Lesson1Activity", "Error al actualizar la racha", e)
                    }
                }
            } else {
                Log.e("Lesson1Activity", "No se encontraron datos del usuario en Firestore.")
            }
        }.addOnFailureListener { e ->
            Log.e("Lesson1Activity", "Error al obtener los datos del usuario", e)
        }
    }

    private fun updateUserPointsForLesson(lessonName: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        // Verifica si la lección ya fue completada sin errores
        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val completedWithoutErrors = document.getBoolean("${lessonName}_completedWithoutErrors") ?: false

                // Si la lección ya fue completada sin errores, no sumamos puntos
                if (completedWithoutErrors) {
                    Log.d("Lesson1Activity", "Lección ya completada sin errores, no se sumarán puntos nuevamente.")
                    return@addOnSuccessListener
                }

                // Sumar puntos solo si la lección no ha sido completada sin errores antes
                if (errorCount == 0) {
                    val previousXpPoints = document.getLong("xpPoints")?.toInt() ?: 0
                    val updatedPoints = previousXpPoints + totalPoints

                    // Actualizar los puntos y marcar la lección como completada sin errores
                    userRef.update(
                        mapOf(
                            "xpPoints" to updatedPoints,
                            "${lessonName}_completedWithoutErrors" to true // Marcar lección como completada sin errores
                        )
                    ).addOnSuccessListener {
                        Log.d("Lesson1Activity", "Puntos actualizados exitosamente: $updatedPoints y lección marcada como completada sin errores.")
                    }.addOnFailureListener { e ->
                        Log.e("Lesson1Activity", "Error al actualizar los puntos y la marca de lección", e)
                    }
                }
            }
        }.addOnFailureListener { e ->
            Log.e("Lesson1Activity", "Error al obtener los datos del usuario", e)
        }
    }
    private fun updateUserPoints() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        // Calcular los puntos actualizados
        val updatedPoints = previousXpPoints + totalPoints

        userRef.update("xpPoints", updatedPoints)
            .addOnSuccessListener {
                Log.d("Lesson1Activity", "Puntos actualizados exitosamente: $updatedPoints")
            }
            .addOnFailureListener { e ->
                Log.e("Lesson1Activity", "Error al actualizar los puntos", e)
            }
    }

    private fun loadLessonFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("lessons").document("lesson1")

        lessonRef.get().addOnSuccessListener { document ->
            if (document != null) {
                exercises = document.get("exercises") as Map<String, Map<String, Any>>
                loadNextExercise()
            } else {
                Log.e("Lesson1Activity", "Documento no encontrado en Firestore")
            }
        }.addOnFailureListener { exception ->
            Log.e("LessonActivity", "Error fetching lesson", exception)
        }
    }

    private fun loadNextExercise() {
        val currentExerciseKey = "exercise$currentExerciseIndex"
        Log.d("Lesson1Activity", "Intentando cargar ejercicio: $currentExerciseKey")

        if (exercises.containsKey(currentExerciseKey)) {
            val exercise = exercises[currentExerciseKey]!!
            val exerciseType = exercise["exerciseType"] as String
            Log.d("Lesson1Activity", "Tipo de ejercicio: $exerciseType")

            when (exerciseType) {
                "video_selection" -> launchExercise1(exercise)
                "ordering" -> launchOrderingExercise(exercise)
                "matching" -> launchMatchingExercise(exercise)
                "selection" -> launchSelectWordExercise(exercise)
                "selection2" -> launchSelectWordExercise2(exercise)
                else -> Toast.makeText(this, "Tipo de ejercicio no soportado: $exerciseType", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Lección completada
            Toast.makeText(this, "Lección completada con $totalPoints puntos", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LeccionTerminadaActivity::class.java)
            intent.putExtra("totalPoints", totalPoints)
            startActivity(intent)

            // Llama a updateUserStreakAndPoints() para actualizar la racha y los puntos en la base de datos@

            updateUserPointsForLesson("lesson1")


            // Llama a checkAchievements() para verificar y desbloquear logros, si corresponde
            if (errorCount == 0) {
                updateUserStreak()
                checkAchievements()
            }
            checkExperiencia()

            finish()
        }
    }


    private fun checkAchievements() {
        checkAprendizRapido()
        //checkConstancia()
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

        Log.d("Lesson1Activity", "Tiempo transcurrido en la lección: $elapsedTime segundos")

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

                // Verificar si el último ejercicio fue ayer o hoy@
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -1)
                val yesterdayDate = dateFormat.format(calendar.time)

                if (lastExerciseDate == yesterdayDate || lastExerciseDate == todayDate) {
                    // El usuario ha mantenido su racha; no hacemos nada aquí
                } else {
                    // Reiniciar la racha si no ha sido continuo
                    streakDays = 1
                    userRef.update("streakDays", streakDays)
                }

                // Verificar si se ha alcanzado la racha de 5 días para la Insignia de Constancia
                if (streakDays >= 5) {
                    unlockInsignia("Insignia de Constancia")
                }
            }
        }.addOnFailureListener { e ->
            Log.e("ZoomInsigniaActivity", "Error al obtener los datos del usuario", e)
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
                        Log.d("Lesson1Activity", "Insignia $insigniaName ya está activada. No se requiere desbloquear nuevamente.")
                        return@addOnSuccessListener
                    }

                    // Si no está activada, procede a activarla
                    val insigniaImageUrl = insigniaDoc.getString("url") ?: ""
                    val insigniaDescription = insigniaDoc.getString("description") ?: ""

                    insigniaDoc.reference.update("status", "activated")
                        .addOnSuccessListener {
                            Log.d("Lesson1Activity", "Insignia $insigniaName activada correctamente.")

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
                    Log.d("Lesson1Activity", "No se encontró la insignia con el nombre $insigniaName.")
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
        startActivityForResult(intent, 1)
    }

    private fun launchOrderingExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExercise5::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("maxLetters", (exercise["maxLetter"] as Long).toInt())
        intent.putStringArrayListExtra("correctAnswer", ArrayList(exercise["correctAnswer"] as List<String>))
        intent.putStringArrayListExtra("videos", ArrayList(exercise["videos"] as List<String>))
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        startActivityForResult(intent, 2)
    }

    private fun launchMatchingExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExcercise4::class.java)
        intent.putExtra("statement", exercise["statement"] as? String)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)

        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", ArrayList(correctPairs))

        startActivityForResult(intent, 3)
    }

    private fun launchSelectWordExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        Log.d("Lesson1Activity", "Iniciando Exercise3Activity con datos: $exercise") // Verifica los datos

        // Manejo seguro de los datos obtenidos
        val statement = exercise["statement"] as? String ?: "Pregunta no disponible"
        val correctAnswer = exercise["correctAnswer"] as? String ?: "Respuesta no disponible"
        val points = (exercise["points"] as? Long)?.toInt() ?: 0
        val videoUrl = exercise["video_url"] as? String ?: ""
        val words = exercise["words"] as? List<String> ?: emptyList()

        // Agregar los valores al intent
        intent.putExtra("statement", statement)
        intent.putExtra("correctAnswer", correctAnswer)
        intent.putExtra("points", points)
        intent.putExtra("video_url", videoUrl)
        intent.putStringArrayListExtra("words", ArrayList(words))

        startActivityForResult(intent, 4)
    }
    private fun launchSelectWordExercise2(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        Log.d("Lesson1Activity", "Iniciando Exercise3Activity con datos: $exercise") // @Verifica los datos

        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("video_url", exercise["video_url"] as String)
        intent.putStringArrayListExtra("words", ArrayList(exercise["words"] as List<String>))

        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Lesson1Activity", "onActivityResult llamado con requestCode=$requestCode, resultCode=$resultCode")

        if (resultCode == RESULT_OK) {
            val pointsEarned = data?.getIntExtra("pointsEarned", 0) ?: 0
            totalPoints += pointsEarned
            Log.d("VERIFICAR PUNTOS", "Puntos acumulados: $totalPoints")
        } else {
            // Incrementar contador de errores si la respuesta es incorrecta
            errorCount++
            Log.d("Lesson1Activity", "Respuesta incorrecta. Contador de errores: $errorCount")
        }

        currentExerciseIndex++
        loadNextExercise()
    }



}