package com.puj.proyectoensenarte.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.ActivityVideoExampleBinding

class VideoExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoExampleBinding
    private var videoUrl: Uri? = null

    // Variables para pasar a la siguiente actividad
    private var points: Int = 0
    private var lessonNumber: Int = 0
    private var lessonName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar datos pasados a esta actividad
        points = intent.getIntExtra("points", 0)
        lessonNumber = intent.getIntExtra("lessonNumber", 0)
        lessonName = intent.getStringExtra("lessonName") ?: ""

        // Configurar el número de lección
        binding.tvLeccionNumero.text = "Lección $lessonNumber"

        // Configurar el MediaController para el VideoView
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        // Cargar el video desde Firebase Storage
        loadVideoFromFirebase()

        // Configurar el botón de continuar
        binding.btnContinuar.setOnClickListener {
            startCameraRecording()
        }

        // Configurar los listeners del VideoView
        setupVideoListeners()
    }

    private fun loadVideoFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE

        // Obtener la palabra objetivo del intent
        val targetWord = intent.getStringExtra("targetWord") ?: "joven"

        // Referencia al video en Firebase Storage usando la palabra objetivo
        val storage = FirebaseStorage.getInstance()
        val videoRef = storage.reference.child("seniasModelo/${targetWord.capitalize()}.mp4")

        videoRef.downloadUrl.addOnSuccessListener { uri ->
            videoUrl = uri
            binding.videoView.setVideoURI(uri)
            binding.videoView.start()
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Error al cargar el video", exception)
            Toast.makeText(
                this,
                "Error al cargar el video. Por favor, intenta nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupVideoListeners() {
        binding.videoView.setOnPreparedListener { mediaPlayer ->
            // Ocultar el loading cuando el video está listo
            binding.progressBar.visibility = View.GONE

            // Configurar loop del video
            mediaPlayer.setOnCompletionListener {
                binding.videoView.start()
            }
        }

        binding.videoView.setOnErrorListener { _, what, extra ->
            Log.e(TAG, "Error en VideoView: what=$what extra=$extra")
            Toast.makeText(
                this,
                "Error al reproducir el video. Por favor, intenta nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            binding.progressBar.visibility = View.GONE
            true
        }
    }

    private fun startCameraRecording() {
        val targetWord = intent.getStringExtra("targetWord") ?: "joven"
        val intent = Intent(this, CameraRecordingActivity::class.java).apply {
            putExtra("points", points)
            putExtra("lessonNumber", lessonNumber)
            putExtra("lessonName", lessonName)
            putExtra("targetWord", targetWord)
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            // Propagar el resultado hacia atrás (a la actividad que llamó a VideoExampleActivity)
            setResult(resultCode, data)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // Pausar el video cuando la actividad no está visible
        binding.videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos del video
        binding.videoView.stopPlayback()
    }

    companion object {
        private const val TAG = "VideoExampleActivity"
        private const val CAMERA_REQUEST_CODE = 100
    }
}