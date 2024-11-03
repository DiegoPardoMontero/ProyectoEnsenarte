package com.puj.proyectoensenarte.dictionary

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorPalabraBinding
import java.text.Normalizer

class DetallePalabraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePorPalabraBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorPalabraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var palabra = intent.getStringExtra("PALABRA") ?: return

        setupUI(palabra)
        loadPalabraDetails(palabra)
    }

    private fun setupUI(palabra: String) {
        binding.tvPalabraTitulo.text = palabra
        binding.backButton.setOnClickListener { finish() }
    }

    private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    private fun CharSequence.unaccent(): String {
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return REGEX_UNACCENT.replace(temp, "")
    }

    fun primeraLetraMayuscula(texto: String): String {
        return texto.replaceFirstChar { it.uppercase() }
    }

    private fun loadPalabraDetails(palabra: String) {
        var palabra = palabra.unaccent().lowercase().replace(" ", "")
        palabra = primeraLetraMayuscula(palabra)

        db.collection("dict").document("palabras")
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
        binding.videoContainerSena.setupVideo(palabraData["seniaURL"] as? String)
        binding.videoContainerDefinicion.setupVideo(palabraData["DefinicionURL"] as? String)
        binding.videoContainerEjemplo.setupVideo(palabraData["EjemploURL"] as? String)
    }

    private fun setupVideo(url: String?, videoViewContainer: FrameLayout) {
        if (url != null) {
            Log.d("DetallePalabra", "Configurando video con URL: $url")

            videoViewContainer.removeAllViews()

            val videoView = VideoView(this)

            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            videoView.layoutParams = layoutParams

            videoViewContainer.addView(videoView)

            val uri = Uri.parse(url)
            videoView.setVideoURI(uri)

            val mediaController = object : MediaController(this) {
                override fun show(timeout: Int) {
                    super.show(0) // Mantener los controles siempre visibles
                }
            }
            mediaController.setAnchorView(videoViewContainer)

            videoView.setMediaController(mediaController)

            videoView.setOnPreparedListener { mp ->
                Log.d("DetallePalabra", "Video preparado y listo para reproducir")
                mp.isLooping = true
                videoView.pause()
                videoView.seekTo(1)
                mediaController.show(0)
            }

            videoView.setOnErrorListener { mp, what, extra ->
                Log.e("DetallePalabra", "Error al cargar el video: what=$what, extra=$extra")
                false
            }

            videoView.requestFocus()
        } else {
            Log.e("DetallePalabra", "URL del video es nula")
            videoViewContainer.removeAllViews()
        }
    }

    private fun setupTexts(palabraData: Map<String, Any>) {
        binding.tvDefinicion.text = palabraData["Definicion"] as? String
        binding.tvEjemplo.text = palabraData["Ejemplo"] as? String
    }
}