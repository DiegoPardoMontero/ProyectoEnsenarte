package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityTestCaribbeanInfoBinding

class ActivityTestCaribbeanInfo : AppCompatActivity() {

    private lateinit var binding: ActivityTestCaribbeanInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityTestCaribbeanInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStartTest.setOnClickListener {
            val intent = Intent(this, TestCaribbeanLevelActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar botón "Cancelar"
        binding.btnCancelTest.setOnClickListener {
            finish()
        }
    }
}