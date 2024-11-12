package com.puj.proyectoensenarte.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityVideoPacoJoelBinding

class VideoPacoJoelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPacoJoelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityVideoPacoJoelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar VideoView
        val videoView = binding.videoView

        // Establecer la URL o URI del primer video
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.ensenarte_video_historia)
        videoView.setVideoURI(videoUri)

        // Agregar controlador de medios para controles de reproducción
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Iniciar el video automáticamente
        videoView.start()

        // Configura el botón "Skip" para redirigir a BottomNavigationActivity
        binding.btnSkip.setOnClickListener {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("selected_fragment", R.id.item_1)
            startActivity(intent)
            finish() // Cierra AndinaVideoActivity
        }

        // Listener para detectar cuando el video termina
        videoView.setOnCompletionListener {
            // Iniciar la siguiente actividad que reproduce el segundo video
            val intent = Intent(this, AndinaVideoActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual si no quieres regresar a ella
        }
    }
}