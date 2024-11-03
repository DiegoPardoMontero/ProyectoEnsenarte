package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityTestInfoBinding

class ActivityTestInfo : AppCompatActivity() {

    private lateinit var binding: ActivityTestInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando ViewBinding
        binding = ActivityTestInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar botón "Iniciar Prueba"
        binding.btnStartTest.setOnClickListener {
            // Iniciar TestAndinaLevelActivity
            val intent = Intent(this, TestAndinaLevelActivity::class.java)
            startActivity(intent)
            finish() // Cerrar ActivityTestInfo
        }

        // Configurar botón "Cancelar"
        binding.btnCancelTest.setOnClickListener {
            // Finalizar la actividad para volver al mapa o actividad anterior@
            finish()
        }
    }
}