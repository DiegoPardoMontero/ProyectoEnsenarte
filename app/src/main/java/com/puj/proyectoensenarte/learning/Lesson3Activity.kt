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

class Lesson3Activity : AppCompatActivity() {

    private var currentExerciseIndex = 1
    private var totalPoints = 0
    private var previousXpPoints = 0 // Variable para guardar los puntos previos@
    private lateinit var exercises: Map<String, Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar los puntos actuales del usuario antes de iniciar la lección
        loadUserXpPoints { xpPoints ->
            previousXpPoints = xpPoints
            // Después de cargar los puntos previos, cargar la lección
            loadLessonFromFirebase()
        }
    }

    private fun loadUserXpPoints(onSuccess: (Int) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val xpPoints = document.getLong("xpPoints")?.toInt() ?: 0
                        onSuccess(xpPoints)
                    } else {
                        onSuccess(0) // Si no existe el campo, iniciar en 0
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Lesson2Activity", "Error al cargar los puntos de experiencia del usuario", e)
                    onSuccess(0) // En caso de error, iniciar en 0
                }
        } else {
            onSuccess(0) // Si no hay usuario, iniciar en 0
        }
    }

    private fun loadLessonFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("lessons").document("lesson3")

        lessonRef.get().addOnSuccessListener { document ->
            if (document != null) {
                exercises = document.get("exercises") as Map<String, Map<String, Any>>
                loadNextExercise()
            } else {
                Log.e("Lesson2Activity", "Documento no encontrado en Firestore")
            }
        }.addOnFailureListener { exception ->
            Log.e("LessonActivity", "Error fetching lesson", exception)
        }
    }

    private fun loadNextExercise() {
        val currentExerciseKey = "exercise$currentExerciseIndex"
        Log.d("Lesson2Activity", "Intentando cargar ejercicio: $currentExerciseKey")

        if (exercises.containsKey(currentExerciseKey)) {
            val exercise = exercises[currentExerciseKey]!!
            val exerciseType = exercise["exerciseType"] as String
            Log.d("Lesson2Activity", "Tipo de ejercicio: $exerciseType")

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

            val newXpPoints = previousXpPoints + totalPoints
            updateUserXpPoints(newXpPoints)

            Toast.makeText(this, "Lección completada con $totalPoints puntos", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LeccionTerminadaActivity::class.java)
            intent.putExtra("totalPoints", totalPoints) // Pasar los puntos totales
            startActivity(intent)
            finish() // Cierra la actividad ac@tual para evitar volver a ella@
        }
    }
    private fun updateUserXpPoints(newXpPoints: Int) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid)
                .update("xpPoints", newXpPoints)
                .addOnSuccessListener {
                    Log.d("Lesson2Activity", "Puntos actualizados exitosamente: $newXpPoints")
                }
                .addOnFailureListener { e ->
                    Log.e("Lesson2Activity", "Error al actualizar los puntos de experiencia", e)
                }
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
    private fun launchMatchingVideosExercise(exercise: Map<String, Any>) {
        Log.d("Lesson2Activity", "Intentando llamar al launch con: $exercise") // Verifica los datos

        val intent = Intent(this, ActivityExercise2::class.java)
        intent.putExtra("statement", exercise["statement"] as? String)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)
        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", java.util.ArrayList(correctPairs))

        startActivityForResult(intent, 6)
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
        Log.d("Lesson2Activity", "Iniciando Exercise3Activity con datos: $exercise") // Verifica los datos

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
        Log.d("Lesson2Activity", "Iniciando Exercise3Activity con datos: $exercise") // @Verifica los datos

        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("video_url", exercise["video_url"] as String)
        intent.putStringArrayListExtra("words", ArrayList(exercise["words"] as List<String>))

        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Lesson2Activity", "onActivityResult llamado con requestCode=$requestCode, resultCode=$resultCode")

        if (resultCode == RESULT_OK) {
            val pointsEarned = data?.getIntExtra("pointsEarned", 0) ?: 0
            totalPoints += pointsEarned
            Log.d("VERIFICAR PUNTOS", "Puntos acumulados: $totalPoints")
        } else {
            Log.d("Lesson2Activity", "Respuesta incorrecta, no se suman puntos.")
        }

        currentExerciseIndex++
        loadNextExercise()
    }



}