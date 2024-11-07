package com.puj.proyectoensenarte.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityLeccionRepasadaBinding

class LeccionRepasadaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeccionRepasadaBinding
    private var totalPoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding@
        binding = ActivityLeccionRepasadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los puntos totales de la lección@
        totalPoints = intent.getIntExtra("totalPoints", 0)

        // Mostrar los puntos obtenidos en el TextView correspondiente
        binding.puntosObtenidos.text = totalPoints.toString()

        // Configurar el botón "Continuar" para regresar a la pantalla principal@
        binding.buttonContinue.setOnClickListener {
            finish() // Cierra la actividad y vuelve a la pantalla anterior o principal
        }
    }
}