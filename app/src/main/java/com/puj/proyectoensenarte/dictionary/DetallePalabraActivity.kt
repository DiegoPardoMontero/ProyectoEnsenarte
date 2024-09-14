package com.puj.proyectoensenarte.dictionary

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorPalabraBinding

class DetallePalabraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePorPalabraBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorPalabraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val palabra = intent.getStringExtra("PALABRA") ?: return

        setupUI(palabra)
        loadPalabraDetails(palabra)
    }

    private fun setupUI(palabra: String) {
        binding.tvPalabraTitulo.text = palabra
        binding.backButton.setOnClickListener { finish() }
    }

    fun primeraLetraMinuscula(texto: String): String {
        return texto.replaceFirstChar { it.lowercase() }
    }

    private fun loadPalabraDetails(palabra: String) {
        var palabra = primeraLetraMinuscula(palabra)
        db.collection("dictionary").document("palabras")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabraData = document.get(palabra) as? Map<String, Any>
                    palabraData?.let {
                        setupVideos(it)
                        setupTexts(it)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }

    private fun setupVideos(palabraData: Map<String, Any>) {
        setupVideo(palabraData["seniaURL"] as? String, binding.videoViewSena)
        setupVideo(palabraData["definicionURL"] as? String, binding.videoViewDefinicion)
        setupVideo(palabraData["ejemploURL"] as? String, binding.videoViewEjemplo)
    }

    private fun setupVideo(url: String?, videoView: VideoView) {
        url?.let {
            val uri = Uri.parse(it)
            videoView.setVideoURI(uri)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
            }
            videoView.start()
        }
    }

    private fun setupTexts(palabraData: Map<String, Any>) {
        binding.tvDefinicion.text = palabraData["definicion"] as? String
        binding.tvEjemplo.text = palabraData["ejemplo"] as? String
    }
}