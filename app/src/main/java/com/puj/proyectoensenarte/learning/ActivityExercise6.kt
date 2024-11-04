package com.puj.proyectoensenarte.learning

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExercise6Binding

class ActivityExercise6 : AppCompatActivity() {

    private lateinit var binding: ActivityExercise6Binding
    private lateinit var correctAnswers: List<String>
    private var points: Int = 0
    private var videoUrls = listOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercise6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir datos
        val statement = intent.getStringExtra("statement")
        correctAnswers = intent.getStringArrayListExtra("correctAnswer") ?: arrayListOf()
        Log.d("ActivityExercise6AYUDA", "Loaded correctAnswers: $correctAnswers")
        points = intent.getIntExtra("points", 0)
        val videos = intent.getStringArrayListExtra("videos") ?: arrayListOf()
        val lessonName = intent.getStringExtra("lessonName") ?: "Lección desconocida"

        // Depuración: Verificar valores recibidos
        Log.d("DEBUG", "Statement: $statement")
        Log.d("DEBUG", "Correct Answers: $correctAnswers")
        Log.d("DEBUG", "Videos URLs: $videos")

        binding.tvTitle.text = lessonName
        binding.tvQuestion.text = statement

        setUpVideoViews(videos)
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
    private fun setUpVideoViews(videos: ArrayList<String>) {
        videoUrls = videos
        Log.d("DEBUG", "Setting up video views with URLs: $videoUrls")

        configureVideo(binding.videoView1, binding.btnPlayPause1, videoUrls.getOrNull(0), binding.checkBox1)
        configureVideo(binding.videoView2, binding.btnPlayPause2, videoUrls.getOrNull(1), binding.checkBox2)
        configureVideo(binding.videoView3, binding.btnPlayPause3, videoUrls.getOrNull(2), binding.checkBox3)
        configureVideo(binding.videoView4, binding.btnPlayPause4, videoUrls.getOrNull(3), binding.checkBox4)
        configureVideo(binding.videoView5, binding.btnPlayPause5, videoUrls.getOrNull(4), binding.checkBox5)
        configureVideo(binding.videoView6, binding.btnPlayPause6, videoUrls.getOrNull(5), binding.checkBox6)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureVideo(
        videoView: VideoView,
        playPauseButton: ImageButton,
        videoUri: String?,
        checkBox: CheckBox
    ) {
        videoUri?.let {
            videoView.setVideoURI(Uri.parse(it))
            Log.d("DEBUG", "Configuring video for URI: $it")
        }

        playPauseButton.visibility = View.GONE

        videoView.setOnCompletionListener {
            playPauseButton.visibility = View.VISIBLE
            playPauseButton.setImageResource(android.R.drawable.ic_media_play)
        }

        videoView.setOnTouchListener { _, _ ->
            if (!videoView.isPlaying) {
                playPauseButton.visibility = View.VISIBLE
            }
            false
        }

        playPauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                videoView.start()
                playPauseButton.visibility = View.GONE
            }
        }
    }

    private fun validateAnswer() {
        val selectedAnswers = mutableListOf<String>()
        Log.d("DEBUG", "Starting answer validation...")

        if (binding.checkBox1.isChecked) selectedAnswers.add(videoUrls.getOrNull(0) ?: "")
        if (binding.checkBox2.isChecked) selectedAnswers.add(videoUrls.getOrNull(1) ?: "")
        if (binding.checkBox3.isChecked) selectedAnswers.add(videoUrls.getOrNull(2) ?: "")
        if (binding.checkBox4.isChecked) selectedAnswers.add(videoUrls.getOrNull(3) ?: "")
        if (binding.checkBox5.isChecked) selectedAnswers.add(videoUrls.getOrNull(4) ?: "")
        if (binding.checkBox6.isChecked) selectedAnswers.add(videoUrls.getOrNull(5) ?: "")

        // Depuración: Mostrar las respuestas seleccionadas por el usuario
        Log.d("DEBUG", "Selected Answers: $selectedAnswers")

        if (selectedAnswers.size != 2) {
            Toast.makeText(this, "Por favor selecciona dos opciones", Toast.LENGTH_SHORT).show()
            Log.d("DEBUG", "Número incorrecto de selecciones: ${selectedAnswers.size}")
            return
        }
        Log.d("ActivityExercise6", "Selected Answers: $selectedAnswers")
        Log.d("ActivityExercise6", "Correct Answers: $correctAnswers")
        // Comparación de respuestas seleccionadas con respuestas correctas
        if (selectedAnswers.containsAll(correctAnswers) && correctAnswers.containsAll(selectedAnswers)) {
            Log.d("DEBUG", "Respuesta correcta!")
            showCorrectResultDialog()
        } else {
            Log.d("DEBUG", "Respuesta incorrecta. Correct answers: $correctAnswers")
            showIncorrectResultDialog()
        }
    }

    private fun showCorrectResultDialog() {
        val dialog = CorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("pointsEarned", points)
            resultIntent.putExtra("correctAnswer", true)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        dialog.show(supportFragmentManager, "CorrectResultDialog")
    }

    private fun showIncorrectResultDialog() {
        val dialog = IncorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("correctAnswer", false)
            setResult(RESULT_CANCELED)
            finish()
        }
        dialog.show(supportFragmentManager, "IncorrectResultDialog")
    }
}