package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityLeccionTerminadaBinding

class LeccionTerminadaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeccionTerminadaBinding
    private var totalPoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding@
        binding = ActivityLeccionTerminadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los puntos totales de la lección@
        totalPoints = intent.getIntExtra("totalPoints", 0)

        // Mostrar los puntos obtenidos en el TextView correspondiente
        binding.puntosObtenidos.text = totalPoints.toString()

        // Configurar el botón "Continuar" para regresar a la pantalla principal@
        binding.buttonContinue.setOnClickListener {
            val intent = Intent(this@LeccionTerminadaActivity, BottomNavigationActivity::class.java)
            intent.putExtra("selected_fragment", R.id.item_1) // Seleccionar el fragmento deseado
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finishAffinity() // Cierra todas las actividades anteriores en la pila@
        }
    }
}