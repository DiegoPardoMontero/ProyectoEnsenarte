package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.video.CameraRecordingActivity

class TestAndinaLevelActivity : AppCompatActivity() {

    private var questions: List<Map<String, Any>> = emptyList()
    private var currentQuestionIndex = 0
    private var failCount = 0
    private val maxFails = 3
    private val numQuestions = 12
    private val lessonName = "Test Región Andina"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_andina_level)

        loadExamQuestions()
    }

    private fun loadExamQuestions() {
        val db = FirebaseFirestore.getInstance()
        val lessonIds = listOf("lesson1", "lesson2", "lesson3")
        val exercises = mutableListOf<Map<String, Any>>()

        // Cargar ejercicios de cada lección
        lessonIds.forEach { lessonId ->
            db.collection("lessons").document(lessonId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val lessonExercises = document.get("exercises") as Map<String, Map<String, Any>>

                        // Filtrar los ejercicios para excluir "selection"
                        val filteredExercises = lessonExercises.values.filter { exercise ->
                            val exerciseType = exercise["exerciseType"] as? String
                            exerciseType != "selection"
                        }

                        exercises.addAll(filteredExercises)

                        if (lessonId == "lesson3") {
                            // Cuando todos los ejercicios estén listos, seleccionamos los 12 aleatorios@
                            questions = exercises.shuffled().take(numQuestions)
                            showNextQuestion()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("ExamActivity", "Error al cargar ejercicios de $lessonId", e)
                }
        }
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex >= questions.size) {
            Toast.makeText(this, "¡Examen completado exitosamente!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val currentQuestion = questions[currentQuestionIndex]
        displayQuestion(currentQuestion)
    }

    private fun displayQuestion(question: Map<String, Any>) {
        val exerciseType = question["exerciseType"] as? String

        when (exerciseType) {
            "video_selection" -> launchVideoSelectionExercise(question)
            "ordering" -> launchOrderingExercise(question)
            "matching" -> launchMatchingExercise(question)
            "matching_videos" -> launchMatchingVideosExercise(question)
            "selection" -> launchSelectWordExercise(question)
            "selection2" -> launchSelectWordExercise2(question)
            "model" -> launchModelExercise(question)
            else -> Toast.makeText(this, "Tipo de ejercicio no soportado: $exerciseType", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onUserAnswered(correct: Boolean) {
        if (correct) {
            currentQuestionIndex++
            showNextQuestion()
        } else {
            failCount++
            if (failCount >= maxFails) {
                Toast.makeText(this, "Examen finalizado. Demasiados fallos.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, BottomNavigationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("navigate_to_item", R.id.item_1) // Indicar que queremos abrir el item 4 (perfil)@
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Respuesta incorrecta. Intentos restantes: ${maxFails - failCount}", Toast.LENGTH_SHORT).show()
                currentQuestionIndex++  // Asegura que avance al siguiente ejercicio@
                showNextQuestion()
            }
        }
    }

    private fun launchVideoSelectionExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise1Activity::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("lessonName", lessonName)
        intent.putStringArrayListExtra("videos", ArrayList(exercise["videos"] as List<String>))
        startActivityForResult(intent, 1)
    }

    private fun launchOrderingExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExercise5::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("lessonName", lessonName)
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
        intent.putExtra("lessonName", lessonName)
        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", ArrayList(correctPairs))
        startActivityForResult(intent, 3)
    }

    private fun launchMatchingVideosExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, ActivityExercise2::class.java)
        intent.putExtra("statement", exercise["statement"] as? String)
        intent.putExtra("lessonName", lessonName)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)
        val correctPairs = exercise["correctPairs"] as? List<Map<String, String>> ?: emptyList()
        intent.putExtra("correctPairs", ArrayList(correctPairs))
        startActivityForResult(intent, 6)
    }

    private fun launchSelectWordExercise(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("lessonName", lessonName)
        intent.putStringArrayListExtra("words", ArrayList(exercise["words"] as List<String>))
        startActivityForResult(intent, 4)
    }

    private fun launchModelExercise(exercise: Map<String, Any>) {
        Log.d("lesson3Activity", "Intentando llamar al launch con: $exercise") // Verifica los datos@
        val intent = Intent(this, CameraRecordingActivity::class.java)
        intent.putExtra("lessonName", lessonName)
        intent.putExtra("points", (exercise["points"] as? Long)?.toInt() ?: 0)
        intent.putExtra("lessonName", lessonName)

        startActivityForResult(intent, 7)
    }
    private fun launchSelectWordExercise2(exercise: Map<String, Any>) {
        val intent = Intent(this, Exercise3Activity::class.java)
        Log.d("lesson3Activity", "Iniciando Exercise3Activity con datos: $exercise")
        intent.putExtra("lessonName", lessonName)
        intent.putExtra("statement", exercise["statement"] as String)
        intent.putExtra("correctAnswer", exercise["correctAnswer"] as String)
        intent.putExtra("points", (exercise["points"] as Long).toInt())
        intent.putExtra("video_url", exercise["video_url"] as String)
        intent.putStringArrayListExtra("words", ArrayList(exercise["words"] as List<String>))

        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            // Recibe el valor de correctAnswer desde la actividad de ejercicio
            val correctAnswer = data.getBooleanExtra("correctAnswer", false)

            Log.d("TestAndinaLevelActivity", "Respuesta recibida: $correctAnswer") // Verificar el valor recibido
            onUserAnswered(correctAnswer)  // Llama a la función para manejar la respuesta del usuario
        } else {
            // Caso en que no se recibe un resultado válido
            failCount++
            Toast.makeText(this, "Respuesta incorrecta. Intentos restantes: ${maxFails - failCount}", Toast.LENGTH_SHORT).show()
            Log.d("TestAndinaLevelActivity", "No se recibió respuesta válida o respuesta incorrecta por defecto")

            if (failCount >= maxFails) {
                Toast.makeText(this, "Examen finalizado. Demasiados fallos.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, BottomNavigationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("navigate_to_item", R.id.item_1) // Indicar que queremos abrir el item 1@
                startActivity(intent)
                finish()
            } else {
                currentQuestionIndex++
                showNextQuestion()
            }
        }
    }
}