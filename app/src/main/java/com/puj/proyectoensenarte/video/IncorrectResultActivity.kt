package com.puj.proyectoensenarte.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityVideoIncorrectoBinding

class IncorrectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoIncorrectoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoIncorrectoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnContinuar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("pointsEarned", 0)
            resultIntent.putExtra("correctAnswer", false) // Indicar que la respuesta fue correcta
            setResult(RESULT_CANCELED)

            finish()
        }
    }
}