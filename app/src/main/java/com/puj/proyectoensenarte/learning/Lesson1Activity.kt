package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R

class Lesson1Activity : AppCompatActivity() {

    private var currentExerciseIndex = 1
    private var totalPoints = 0
    private lateinit var exercises: Map<String, Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar la lección desde Firebase
        loadLessonFromFirebase()
    }

    private fun loadLessonFromFirebase() {
        val db = FirebaseFirestore.getInstance()
        val lessonRef = db.collection("lessons").document("lesson1")

        lessonRef.get().addOnSuccessListener { document ->
            if (document != null) {
                exercises = document.get("exercises") as Map<String, Map<String, Any>>
                loadNextExercise()
            }
        }.addOnFailureListener { exception ->
            Log.e("LessonActivity", "Error fetching lesson", exception)
        }
    }

    private fun loadNextExercise() {
        val currentExerciseKey = "exercise$currentExerciseIndex"

        if (exercises.containsKey(currentExerciseKey)) {
            val exercise = exercises[currentExerciseKey]!!
            val exerciseType = exercise["exerciseType"] as String

            when (exerciseType) {
                "video_selection" -> launchExercise1(exercise)
                "word_selection" -> launchWordSelectionExercise(exercise)
                else -> Toast.makeText(this, "Tipo de ejercicio no soportado", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Todos los ejercicios completados
            Toast.makeText(this, "Lección completada con $totalPoints puntos", Toast.LENGTH_LONG).show()
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

    private fun launchWordSelectionExercise(exercise: Map<String, Any>) {
        // Implementa otro tipo de ejercicio si es necesario.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recibir puntos acumulados desde Exercise1Activity
            val pointsEarned = data?.getIntExtra("pointsEarned", 0) ?: 0
            totalPoints += pointsEarned

            currentExerciseIndex++
            loadNextExercise()
        }
    }
}