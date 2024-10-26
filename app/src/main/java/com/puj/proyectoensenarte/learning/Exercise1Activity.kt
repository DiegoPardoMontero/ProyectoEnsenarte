package com.puj.proyectoensenarte.learning

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityExcercise1Binding

class Exercise1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityExcercise1Binding
    private lateinit var correctAnswer: String
    private var points: Int = 0
    private var videoUrls = listOf<String?>() // Lista para almacenar las URLs de los videos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityExcercise1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir los datos del ejercicio desde Lesson1Activity
        val statement = intent.getStringExtra("statement")
        correctAnswer = intent.getStringExtra("correctAnswer") ?: ""
        points = intent.getIntExtra("points", 0)
        val videos = intent.getStringArrayListExtra("videos") ?: arrayListOf()

        // Configurar el texto de la pregunta
        binding.tvQuestion.text = statement

        // Cargar los videos y almacenar sus URLs@
        setUpVideoViews(videos)

        // Botón Enviar
        binding.btnSubmit.setOnClickListener {
            validateAnswer()
        }
    }

    private fun setUpVideoViews(videos: ArrayList<String>) {
        videoUrls = videos // Almacenar las URLs de los videos
        Log.d("url videos", videoUrls.toString())
        configureVideo(binding.videoView1, binding.btnPlayPause1, videoUrls.getOrNull(0), binding.radioButton1)
        configureVideo(binding.videoView2, binding.btnPlayPause2, videoUrls.getOrNull(1), binding.radioButton2)
        configureVideo(binding.videoView3, binding.btnPlayPause3, videoUrls.getOrNull(2), binding.radioButton3)
        configureVideo(binding.videoView4, binding.btnPlayPause4, videoUrls.getOrNull(3), binding.radioButton4)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureVideo(
        videoView: VideoView,
        playPauseButton: ImageButton,
        videoUri: String?,
        radioButton: RadioButton
    ) {
        videoUri?.let {
            videoView.setVideoURI(Uri.parse(it))
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

        radioButton.setOnClickListener {
            clearOtherRadioSelections(radioButton)
        }
    }

    private fun clearOtherRadioSelections(selectedRadioButton: RadioButton) {
        val radioButtons = listOf(binding.radioButton1, binding.radioButton2, binding.radioButton3, binding.radioButton4)
        for (radioButton in radioButtons) {
            if (radioButton != selectedRadioButton) {
                radioButton.isChecked = false
            }
        }
    }

    private fun validateAnswer() {
        var selectedAnswer: String? = null

        // Verificar cuál RadioButton está seleccionado y asignar el video correspondiente a `selectedAnswer`
        selectedAnswer = when {
            binding.radioButton1.isChecked -> videoUrls.getOrNull(0)
            binding.radioButton2.isChecked -> videoUrls.getOrNull(1)
            binding.radioButton3.isChecked -> videoUrls.getOrNull(2)
            binding.radioButton4.isChecked -> videoUrls.getOrNull(3)
            else -> null
        }

        // Si no se seleccionó ningún RadioButton, mostrar un mensaje
        if (selectedAnswer.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor selecciona una opción", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("URL DEL VIDEO SELECCIONADO",selectedAnswer)


        // Comparar la respuesta seleccionada con la respuesta correcta@
        if (selectedAnswer == correctAnswer) {
            showCorrectResultDialog()
        } else {
            showIncorrectResultDialog()
        }
    }

    // Mostrar diálogo de respuesta correcta
    private fun showCorrectResultDialog() {
        val dialog = CorrectResultBottomSheet { continueToNextExercise() }
        dialog.show(supportFragmentManager, "CorrectResultDialog")
    }

    // Mostrar diálogo de respuesta incorrecta
    private fun showIncorrectResultDialog() {
        val dialog = IncorrectResultBottomSheet { continueToNextExercise() }
        dialog.show(supportFragmentManager, "IncorrectResultDialog")
    }

    // Esta función será llamada desde el diálogo para continuar
    private fun continueToNextExercise() {
        val resultIntent = Intent()
        resultIntent.putExtra("pointsEarned", points)
        setResult(RESULT_OK, resultIntent)
        finish() // Volver a Lesson1Activity
    }
}