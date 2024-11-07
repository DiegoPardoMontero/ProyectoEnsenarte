package com.puj.proyectoensenarte.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.puj.proyectoensenarte.databinding.ActivityVideoProcesamientoBinding
import com.puj.proyectoensenarte.utils.SignLanguageVideoProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.database.FirebaseDatabase

class ProcessingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoProcesamientoBinding
    private lateinit var videoProcessor: SignLanguageVideoProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcesamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoProcessor = SignLanguageVideoProcessor(this)

        val videoUri = intent.getStringExtra("video_uri")?.let { Uri.parse(it) }
        val lessonNumber = intent.getStringExtra("lesson_number") ?: "1"
        val expectedSign = intent.getStringExtra("expected_sign")

        binding.tvEjercicioNumero.text = "Lección $lessonNumber"

        lifecycleScope.launch {
            try {
                if (videoUri != null && expectedSign != null) {
                    when (val result = videoProcessor.processVideo(videoUri, expectedSign)) {
                        is SignLanguageVideoProcessor.ProcessingResult.Success -> {
                            val predictionResult = result.result

                            Log.d("ProcessingActivity", """
                                Resultado del procesamiento:
                                Seña predicha: ${predictionResult.predictedSign}
                                Seña esperada: ${predictionResult.expectedSign}
                                Es correcta: ${predictionResult.isCorrect}
                                Confianzas: ${predictionResult.confidenceScores.map { "%.2f".format(it * 100) + "%" }}
                            """.trimIndent())

                            delay(2000) // Dar tiempo para ver la animación
                            navigateToResult(predictionResult.isCorrect)
                        }
                        is SignLanguageVideoProcessor.ProcessingResult.Error -> {
                            Log.e("ProcessingActivity", "Error en el procesamiento", result.exception)
                            Toast.makeText(
                                this@ProcessingActivity,
                                "Error al procesar el video",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToResult(false)
                        }
                    }
                } else {
                    Log.e("ProcessingActivity", "URI del video o seña esperada es null")
                    Toast.makeText(
                        this@ProcessingActivity,
                        "Error: Datos incompletos",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                Log.e("ProcessingActivity", "Error inesperado", e)
                Toast.makeText(
                    this@ProcessingActivity,
                    "Error inesperado",
                    Toast.LENGTH_SHORT
                ).show()
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
        val database = FirebaseDatabase.getInstance("https://proyectoensenarte-d4dd2-default-rtdb.firebaseio.com").reference

        database.child("resultado").get().addOnSuccessListener { snapshot ->
            val firebaseResult = snapshot.getValue(String::class.java)

            val intent = when (firebaseResult) {
                "correcto" -> Intent(this, CorrectResultActivity::class.java)
                "incorrecto" -> Intent(this, IncorrectResultActivity::class.java)
                else -> {
                    if (isCorrect) {
                        Intent(this, CorrectResultActivity::class.java)
                    } else {
                        Intent(this, IncorrectResultActivity::class.java)
                    }
                }
            }

            // Usar startActivityForResult en lugar de startActivity
            startActivityForResult(intent, RESULT_REQUEST_CODE)

        }.addOnFailureListener { e ->
            Log.e("ProcessingActivity", "Error al leer Firebase", e)
            val intent = if (isCorrect) {
                Intent(this, CorrectResultActivity::class.java)
            } else {
                Intent(this, IncorrectResultActivity::class.java)
            }
            startActivityForResult(intent, RESULT_REQUEST_CODE)
        }
    }

    // Agregar el onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_REQUEST_CODE) {
            // Propagar el resultado hacia atrás
            setResult(resultCode, data)
            finish()
        }
    }

    companion object {
        private const val RESULT_REQUEST_CODE = 100
    }

}