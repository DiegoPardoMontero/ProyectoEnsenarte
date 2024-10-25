package com.puj.proyectoensenarte.learning

import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExercise3Binding

class Exercise3Activity : AppCompatActivity() {

    // Inicializa el objeto de binding@
    private lateinit var binding: ActivityExercise3Binding
    private var selectedWord: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el layout usando ViewBinding
        binding = ActivityExercise3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la lógica de los botones de opción
        setUpWordListeners()

        setUpVideoViews()

        // Configura el botón "Enviar@"
        binding.btnSubmit.setOnClickListener {
            Toast.makeText(this, "Respuesta enviada", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setUpWordListeners() {
        binding.wordOption1.setOnClickListener {
            selectWord(binding.wordOption1)
        }

        binding.wordOption2.setOnClickListener {
            selectWord(binding.wordOption2)
        }

        binding.wordOption3.setOnClickListener {
            selectWord(binding.wordOption3)
        }

        binding.wordOption4.setOnClickListener {
            selectWord(binding.wordOption4)
        }
    }

    private fun selectWord(textView: TextView) {
        // Desmarcar el texto previamente seleccionado
        selectedWord?.setBackgroundResource(R.drawable.border) // Restablecer el borde original

        // Marcar el texto seleccionado
        selectedWord = textView
        selectedWord?.setBackgroundResource(R.drawable.selected_border) // Cambia el borde para mostrar que está seleccionada
    }
    private fun checkAnswer() {
    }

    private fun setUpVideoViews() {
        // Configurar cada video con su respectivo botón play/pause@
        configureVideo(
            binding.videoView1,
            binding.btnPlayPause1,
            R.raw.video1
        )
    }

    private fun configureVideo(
        videoView: VideoView,
        playPauseButton: ImageButton,
        videoResId: Int
    ) {
        // Obtener el URI del video
        val videoUri = Uri.parse("android.resource://${packageName}/$videoResId")
        videoView.setVideoURI(videoUri)

        // Configurar el comportamiento del VideoView cuando el video se com@plete
        videoView.setOnCompletionListener {
            // Reiniciar el botón a estado de reproducción cuando el video finalice
            //playPauseButton.setImageResource(android.R.drawable.ic_media_play)
        }

        // Configurar el botón play/pause
        playPauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                //playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            } else {
                videoView.start()
                //playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

    }
}