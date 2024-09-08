package com.puj.proyectoensenarte

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textoCrearCuenta.setOnClickListener{
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        // Aquí puedes configurar el comportamiento de la actividad
    }
}
