package com.puj.proyectoensenarte.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityAndinaVideoBinding

class AndinaVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAndinaVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityAndinaVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar VideoView
        val videoView = binding.videoView

        val videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/videosHistoria%2FVideo%20regio%CC%81n%20andina%20comprimido.mp4?alt=media&token=d033ca62-8f9c-47c1-a669-3243f93e651c")
        videoView.setVideoURI(videoUri)

        // Agregar controlador de medios para controles de reproducción
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Iniciar el segundo video automáticamente
        videoView.start()

        // Configura el botón "Skip" para redirigir a BottomNavigationActivity
        binding.btnSkip.setOnClickListener {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("selected_fragment", R.id.item_1)
            startActivity(intent)
            finish() // Cierra AndinaVideoActivity
        }

        // Listener para redirigir al final del video@
        videoView.setOnCompletionListener {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("selected_fragment", R.id.item_1)
            startActivity(intent)
            finish()
        }
    }
}