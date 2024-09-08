package com.puj.proyectoensenarte

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityCrearCuentaBinding

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textoIniciarSesion.setOnClickListener{
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
        binding.botonCrearCuenta.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
