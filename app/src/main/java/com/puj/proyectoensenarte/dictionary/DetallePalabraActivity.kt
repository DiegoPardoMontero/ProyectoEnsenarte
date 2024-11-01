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
        return texto.replaceFirstChar { it.uppercase() }
    }

    private fun loadPalabraDetails(palabra: String) {
        var palabra = primeraLetraMinuscula(palabra)
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

            // Limpiar el contenedor
            videoViewContainer.removeAllViews()

            // Crear un nuevo VideoView
            val videoView = VideoView(this)

            // Configurar el VideoView
            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            videoView.layoutParams = layoutParams

            // Añadir el VideoView al FrameLayout
            videoViewContainer.addView(videoView)

            val uri = Uri.parse(url)
            videoView.setVideoURI(uri)

            // Crear un MediaController personalizado
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

            // Iniciar la carga del video
            videoView.requestFocus()
        } else {
            Log.e("DetallePalabra", "URL del video es nula")
            // Limpiar el contenedor si no hay URL
            videoViewContainer.removeAllViews()
        }
    }

    private fun setupTexts(palabraData: Map<String, Any>) {
        binding.tvDefinicion.text = palabraData["Definicion"] as? String
        binding.tvEjemplo.text = palabraData["Ejemplo"] as? String
    }
}