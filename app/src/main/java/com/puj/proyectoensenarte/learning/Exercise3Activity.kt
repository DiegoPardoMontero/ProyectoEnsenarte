package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExercise3Binding

class Exercise3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityExercise3Binding
    private var selectedWord: TextView? = null
    private lateinit var correctAnswer: String
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercise3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtener los datos pasados de Lesson1Activity@
        val statement = intent.getStringExtra("statement") ?: ""
        correctAnswer = intent.getStringExtra("correctAnswer") ?: ""
        points = intent.getIntExtra("points", 0)
        val videoUrl = intent.getStringExtra("video_url") ?: ""
        val words = intent.getStringArrayListExtra("words") ?: arrayListOf()
        val lessonName = intent.getStringExtra("lessonName") ?: "Lección desconocida"

        binding.tvTitle.text = lessonName
        // Configurar el enunciado
        binding.tvQuestion3.text = statement

        // Cargar el video en el VideoView@
        setUpVideoView(videoUrl)

        // Configurar las palabras
        setUpWordOptions(words)

        configureCloseButton()

        // Configurar el botón Enviar
        binding.btnSubmit.setOnClickListener {
            validateAnswer()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.putExtra("selected_fragment", R.id.item_1) // Seleccionar el fragmento deseado
        startActivity(intent)
        finishAffinity() // Cierra todas las actividades anteriores en la pila
    }

    private fun configureCloseButton() {
        binding.closeButton.setOnClickListener {
            showExitConfirmationDialog()
        }
    }


    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Salir de la lección")
            .setMessage("¿Estás seguro de que deseas salir? No ganarás puntos por esta lección.")
            .setPositiveButton("Sí") { _, _ ->
                try {
                    // Redirigir a ScrollableMapActivity@
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)
                    Log.d("Exercise1Activity", "Navigating to BottomNavigationActivity")
                    finish() // Cierra Exercise1Activity
                } catch (e: Exception) {
                    Log.e("Exercise1Activity", "Error al navegar a BottomNavigationActivity", e)
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    private fun setUpVideoView(videoUrl: String) {
        binding.videoView1.setVideoURI(Uri.parse(videoUrl))
        binding.videoView1.setOnPreparedListener { mp -> mp.isLooping = true }
        binding.videoView1.start()

        // Configurar el botón de reproducción/pausa@
        binding.btnPlayPause1.setOnClickListener {
            if (binding.videoView1.isPlaying) {
                binding.videoView1.pause()
            } else {
                binding.videoView1.start()
            }
        }
    }

    private fun setUpWordOptions(words: ArrayList<String>) {
        val wordViews = listOf(binding.wordOption1, binding.wordOption2, binding.wordOption3, binding.wordOption4)

        words.forEachIndexed { index, word ->
            wordViews.getOrNull(index)?.apply {
                text = word
                setOnClickListener {
                    selectWord(this)
                }
            }
        }
    }

    private fun selectWord(textView: TextView) {
        // Restablecer el borde de la palabra previamente seleccionada
        selectedWord?.setBackgroundResource(R.drawable.border)

        // Marcar la palabra seleccionada@
        selectedWord = textView
        selectedWord?.setBackgroundResource(R.drawable.selected_border)
    }

    private fun validateAnswer() {
        if (selectedWord == null) {
            Toast.makeText(this, "Por favor selecciona una palabra", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si la palabra seleccionada es correcta@
        if (selectedWord?.text == correctAnswer) {
            showCorrectResultDialog()
        } else {
           showIncorrectResultDialog()
        }
    }

    private fun showCorrectResultDialog() {
        val dialog = CorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("pointsEarned", points)
            resultIntent.putExtra("correctAnswer", true) // Indicar que la respuesta fue correcta
            setResult(RESULT_OK, resultIntent)
            finish() // Volver a Lesson1Activity
        }
        dialog.show(supportFragmentManager, "CorrectResultDialog")
    }

    private fun showIncorrectResultDialog() {
        val dialog = IncorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("correctAnswer", false) // Indicar que la respuesta fue incorrecta
            setResult(RESULT_CANCELED) // Enviar RESULT_CANCELED para respuestas incorrectas
            finish() // Volver a Lesson1Activity
        }
        dialog.show(supportFragmentManager, "IncorrectResultDialog")
    }

    private fun continueToNextExercise() {
        val resultIntent = Intent()
        resultIntent.putExtra("pointsEarned", points)
        setResult(RESULT_OK, resultIntent)
        finish() // Volver a Lesson1Activity@
    }
}