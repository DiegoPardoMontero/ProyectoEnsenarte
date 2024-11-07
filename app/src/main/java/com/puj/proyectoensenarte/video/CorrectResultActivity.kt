package com.puj.proyectoensenarte.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityVideoCorrectoBinding

class CorrectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoCorrectoBinding
    private lateinit var correctAnswer: String
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoCorrectoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        correctAnswer = intent.getStringExtra("correctAnswer") ?: ""
        points = intent.getIntExtra("points", 20)

        binding.btnContinuar.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("pointsEarned", points)
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }
}