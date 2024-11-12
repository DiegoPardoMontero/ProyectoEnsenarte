package com.puj.proyectoensenarte.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityCaribeanVideoBinding

class CaribeanVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaribeanVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityCaribeanVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar VideoView
        val videoView = binding.videoView
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.video_caribe)
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