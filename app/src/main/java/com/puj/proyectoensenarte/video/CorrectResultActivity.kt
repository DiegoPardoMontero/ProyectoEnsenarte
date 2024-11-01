package com.puj.proyectoensenarte.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityVideoCorrectoBinding

class CorrectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoCorrectoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoCorrectoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}