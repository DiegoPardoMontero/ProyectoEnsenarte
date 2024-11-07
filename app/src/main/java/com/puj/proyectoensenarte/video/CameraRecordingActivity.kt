package com.puj.proyectoensenarte.video

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.puj.proyectoensenarte.databinding.ActivityGrabarVideoBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CameraRecordingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGrabarVideoBinding
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrabarVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Agregar PreviewView programáticamente detrás de todo
        val previewView = PreviewView(this).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            visibility = View.GONE
        }
        binding.root.addView(previewView, 0) // Añadir al inicio para que esté detrás

        // Solicitar permisos cuando se presione el botón
        binding.btnEmpezar.setOnClickListener {
            if (!isRecording) {
                if (allPermissionsGranted()) {
                    startRecording(previewView)
                } else {
                    ActivityCompat.requestPermissions(
                        this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                    )
                }
            } else {
                stopRecording()
            }
        }
    }

    private fun startRecording(previewView: PreviewView) {
        // Mostrar la vista de cámara y ocultar los elementos iniciales
        previewView.visibility = View.VISIBLE
        binding.apply {
            tvLeccionNumero.visibility = View.GONE
            tvTitulo.visibility = View.GONE
            ivIlustracion.visibility = View.GONE
            tvDescripcion.visibility = View.GONE
            btnEmpezar.text = "Detener"
        }

        isRecording = true

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this as LifecycleOwner,
                    cameraSelector,
                    preview,
                    videoCapture
                )

                // Iniciar la grabación inmediatamente
                startVideoRecording()
            } catch (exc: Exception) {
                Log.e(TAG, "Error al vincular la cámara", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private var videoUri: Uri? = null

    private fun startVideoRecording() {
        val videoCapture = this.videoCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Signs")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            // Guardar la URI del video grabado
                            videoUri = recordEvent.outputResults.outputUri
                            // Navegar a la pantalla de procesamiento
                            navigateToProcessing()
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Error en la grabación: ${recordEvent.error}")
                            resetUIState()
                        }
                    }
                }
            }
    }

    private fun navigateToProcessing() {
        videoUri?.let { uri ->
            val intent = Intent(this, ProcessingActivity::class.java).apply {
                putExtra("video_uri", uri.toString())
                putExtra("lesson_number", "1")
                putExtra("expected_sign", "hombre")
            }
            startActivityForResult(intent, PROCESSING_REQUEST_CODE) // Cambiar a startActivityForResult
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROCESSING_REQUEST_CODE) {
            // Propagar el resultado hacia atrás
            setResult(resultCode, data)
            finish()
        }
    }

    private fun stopRecording() {
        recording?.stop()
        recording = null
        isRecording = false
    }

    private fun resetUIState() {
        binding.apply {
            root.getChildAt(0).visibility = View.GONE // Ocultar PreviewView
            tvLeccionNumero.visibility = View.VISIBLE
            tvTitulo.visibility = View.VISIBLE
            ivIlustracion.visibility = View.VISIBLE
            tvDescripcion.visibility = View.VISIBLE
            btnEmpezar.text = "Empezar"
        }
        isRecording = false
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startRecording(binding.root.getChildAt(0) as PreviewView)
            } else {
                Toast.makeText(this,
                    "Permisos no otorgados por el usuario.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "Grabar Video Activity"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val PROCESSING_REQUEST_CODE = 200 // Agregar esta constante
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}