package com.puj.proyectoensenarte.learning

import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExcercise1Binding

class Exercise1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityExcercise1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Inicializar ViewBinding
        binding = ActivityExcercise1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los videos y botones play/pause
        setUpVideoViews()

        // Botón Enviar
        binding.btnSubmit.setOnClickListener {
            // Aquí puedes agregar lógica para enviar la respuesta seleccionada
            Toast.makeText(this, "Respuesta enviada", Toast.LENGTH_SHORT).show()
            val dialog = IncorrectResultBottomSheet()
            dialog.show(supportFragmentManager, "ResultDialog")
        }


    }

    private fun setUpVideoViews() {
        // Configurar cada video con su respectivo botón play/pause@
        configureVideo(
            binding.videoView1,
            binding.btnPlayPause1,
            R.raw.video1,  // Reemplaza con tu archivo de video
            binding.radioButton1
        )
        configureVideo(
            binding.videoView2,
            binding.btnPlayPause2,
            R.raw.video2,  // Reemplaza con tu archivo de video
            binding.radioButton2
        )
        configureVideo(
            binding.videoView3,
            binding.btnPlayPause3,
            R.raw.video3,  // Reemplaza con tu archivo de video
            binding.radioButton3
        )
        configureVideo(
            binding.videoView4,
            binding.btnPlayPause4,
            R.raw.video4,  // Reemplaza con tu archivo de video
            binding.radioButton4
        )
    }

    // Método para configurar cada VideoView y su botón de play/pause
    private fun configureVideo(
        videoView: VideoView,
        playPauseButton: ImageButton,
        videoResId: Int,
        radioButton: RadioButton
    ) {
        // Obtener el URI del video
        val videoUri = Uri.parse("android.resource://${packageName}/$videoResId")
        videoView.setVideoURI(videoUri)

        // Configurar el comportamiento del VideoView cuando el video se complete
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

        // Configurar el comportamiento del RadioButton
        radioButton.setOnClickListener {
            // Aquí puedes agregar la lógica de selección del video correspondiente
            Toast.makeText(this, "Seleccionaste un video", Toast.LENGTH_SHORT).show()

            // Si quieres hacer que solo se pueda seleccionar un RadioButton a la vez,
            // puedes hacer que los otros RadioButtons se desactiven cuando se selecciona uno@.
            clearOtherRadioSelections(radioButton)
        }
    }

    // Método para limpiar la selección de otros RadioButtons
    private fun clearOtherRadioSelections(selectedRadioButton: RadioButton) {
        val radioButtons = listOf(binding.radioButton1, binding.radioButton2, binding.radioButton3, binding.radioButton4)
        for (radioButton in radioButtons) {
            if (radioButton != selectedRadioButton) {
                radioButton.isChecked = false
            }
        }
    }
}