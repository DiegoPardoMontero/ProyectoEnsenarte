package com.puj.proyectoensenarte.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.puj.proyectoensenarte.databinding.ActivityVideoProcesamientoBinding
import com.puj.proyectoensenarte.utils.SignLanguageVideoProcessor // Ajusta según donde tengas el procesador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProcessingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoProcesamientoBinding
    private lateinit var videoProcessor: SignLanguageVideoProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcesamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el procesador
        videoProcessor = SignLanguageVideoProcessor(this)

        // Obtener datos del intent usando el nuevo método para Strings
        val videoUriString = intent.getStringExtra("video_uri")
        val lessonNumber = intent.getStringExtra("lesson_number") ?: "1"
        val expectedSign = intent.getStringExtra("expected_sign")

        // Actualizar texto de la lección
        binding.tvEjercicioNumero.text = "Lección $lessonNumber"

        // Configurar el indicador de progreso
        setupProgressIndicator()

        // Procesar el video
        lifecycleScope.launch {
            try {
                val videoUri = videoUriString?.let { Uri.parse(it) }
                videoUri?.let {
                    when (val result = videoProcessor.processVideo(it)) {
                        is SignLanguageVideoProcessor.ProcessingResult.Success -> {
                            // Comparar con la seña esperada
                            val isCorrect = expectedSign?.let { expected ->
                                result.predictedLabel == expected
                            } ?: false

                            delay(2000)
                            navigateToResult(isCorrect)
                        }
                        is SignLanguageVideoProcessor.ProcessingResult.Error -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@ProcessingActivity,
                                    "Error al procesar el video",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            delay(1000)
                            navigateToResult(false)
                        }
                    }
                } ?: run {
                    Toast.makeText(
                        this@ProcessingActivity,
                        "Error: No se encontró el video",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                Log.e("ProcessingActivity", "Error processing video", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProcessingActivity,
                        "Error inesperado al procesar el video",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                navigateToResult(false)
            }
        }
    }

    private fun setupProgressIndicator() {
        binding.progressIndicator.apply {
            isIndeterminate = true
            // El color ya está definido en el XML con @array/progress_colors
            // Pero podríamos añadir configuración adicional aquí si es necesario
            show()
        }
    }

    private fun navigateToResult(isCorrect: Boolean) {
        // Crear el intent según el resultado
        val intent = if (isCorrect) {
            Intent(this, CorrectResultActivity::class.java)
        } else {
            Intent(this, IncorrectResultActivity::class.java)
        }
        // Iniciar la actividad y finalizar esta
        startActivity(intent)
        finish()
    }

}