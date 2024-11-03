package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExercise2Binding

class ActivityExercise2 : AppCompatActivity() {

    private lateinit var binding: ActivityExercise2Binding
    private val selectedPairs = mutableMapOf<VideoView, TextView>()
    private lateinit var correctPairs: List<Map<String, String>>
    private var points: Int = 0
    private var selectedVideoView: VideoView? = null
    private val matchedVideos = mutableSetOf<VideoView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercise2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val statement = intent.getStringExtra("statement") ?: ""
        correctPairs = intent.getSerializableExtra("correctPairs") as? List<Map<String, String>> ?: emptyList()
        points = intent.getIntExtra("points", 0)
        val lessonName = intent.getStringExtra("lessonName") ?: "Lección desconocida"

        binding.tvTitle.text = lessonName
        binding.tvQuestion.text = statement
        loadVideos()
        setUpWordOptions()
        configureCloseButton()

        binding.btnSubmit.setOnClickListener {
            validateAnswer()
        }
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
                    finish() // Cierra Exercise1Activity@
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

    private fun loadVideos() {
        val videoViews = listOf(
            binding.videoView1,
            binding.VideoView2,
            binding.VideoView3,
            binding.VideoView4
        )

        correctPairs.forEachIndexed { index, pair ->
            val videoView = videoViews.getOrNull(index)
            videoView?.let {
                val videoUri = pair["url"]
                if (videoUri != null) {
                    videoView.setVideoURI(Uri.parse(videoUri))

                    // Configurar el video en loop y empezar automáticamente
                    videoView.setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = true
                        videoView.start() // Inicia la reproducción automáticamente
                    }

                    // Asignar la URL como tag para referencia en la validación
                    videoView.tag = videoUri
                }

                // Listener para seleccionar el video
                videoView.setOnClickListener {
                    selectVideo(videoView)
                }
            }
        }
    }


    private fun setUpWordOptions() {
        val wordViews = listOf(binding.wordOption1, binding.wordOption2, binding.wordOption3, binding.wordOption4)

        correctPairs.forEachIndexed { index, pair ->
            wordViews.getOrNull(index)?.apply {
                text = pair["word"]
                setOnClickListener {
                    selectWord(this)
                }
            }
        }
    }

    private fun selectVideo(videoView: VideoView) {
        // Pausar el video previamente seleccionado (si no está ya emparejado) y eliminar el borde temporal@
        if (selectedVideoView != null && !matchedVideos.contains(selectedVideoView)) {
            selectedVideoView?.pause()
            selectedVideoView?.setBackgroundResource(0) // Quitar el borde temporal
        }

        // Establecer el nuevo video como seleccionado y agregar el fondo de selección temporal
        selectedVideoView = videoView
        selectedVideoView?.setBackgroundResource(R.drawable.selected_background) // Fondo temporal de selección
        selectedVideoView?.start()
    }


    private fun selectWord(textView: TextView) {
        if (selectedVideoView != null) {
            // Emparejar el video seleccionado con la palabra y agregarlo al conjunto de videos emparejados
            selectedPairs[selectedVideoView!!] = textView
            matchedVideos.add(selectedVideoView!!) // Marcar el video como emparejado

            // Cambiar el fondo del video emparejado permanentemente
            selectedVideoView?.setBackgroundResource(R.drawable.paired_background) // Fondo permanente para videos emparejados

            // Marcar la palabra como seleccionada
            textView.isEnabled = false
            textView.setBackgroundResource(R.drawable.selected_border)
        } else {
            Toast.makeText(this, "Por favor selecciona primero un video", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAnswer() {
        if (selectedPairs.size < correctPairs.size) {
            Toast.makeText(this, "Debes seleccionar una palabra para cada video", Toast.LENGTH_SHORT).show()
            return
        }

        var correctCount = 0

        selectedPairs.forEach { (videoView, wordView) ->
            val selectedUrl = videoView.tag as? String
            val selectedWord = wordView.text.toString()

            // Verificar si el par seleccionado (URL y palabra) está en los pares correctos
            val isCorrectPair = correctPairs.any { it["url"] == selectedUrl && it["word"] == selectedWord }

            if (isCorrectPair) {
                correctCount++
            }
        }

        if (correctCount == correctPairs.size) {
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
        finish() // Volver a Lesson1Activity
    }
}