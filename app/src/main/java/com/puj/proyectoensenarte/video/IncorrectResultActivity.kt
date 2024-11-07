package com.puj.proyectoensenarte.video

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
            setResult(RESULT_CANCELED)

            finish()
        }
    }
}