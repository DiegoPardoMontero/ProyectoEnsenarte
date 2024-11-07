package com.puj.proyectoensenarte.buttons

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.puj.proyectoensenarte.databinding.ActivityButtonsBinding
import android.os.Handler
import android.os.Looper

class ButtonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityButtonsBinding
    private lateinit var database: DatabaseReference
    private var buttonsEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityButtonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance("https://proyectoensenarte-d4dd2-default-rtdb.firebaseio.com").reference

        binding.btnCorrecto.setOnClickListener {
            if (buttonsEnabled) {
                actualizarResultado("correcto")
                startCooldown()
            }
        }

        binding.btnIncorrecto.setOnClickListener {
            if (buttonsEnabled) {
                actualizarResultado("incorrecto")
                startCooldown()
            }
        }
    }

    private fun startCooldown() {
        buttonsEnabled = false

        binding.btnCorrecto.alpha = 0.5f
        binding.btnIncorrecto.alpha = 0.5f

        binding.btnCorrecto.foreground = createCooldownDrawable()
        binding.btnIncorrecto.foreground = createCooldownDrawable()

        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.duration = 3000
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            binding.btnCorrecto.foreground?.alpha = (value * 255).toInt()
            binding.btnIncorrecto.foreground?.alpha = (value * 255).toInt()
        }

        animator.doOnEnd {
            binding.btnCorrecto.foreground = null
            binding.btnIncorrecto.foreground = null
            binding.btnCorrecto.alpha = 1f
            binding.btnIncorrecto.alpha = 1f
        }
        animator.start()

        Handler(Looper.getMainLooper()).postDelayed({
            buttonsEnabled = true
        }, 3000)
    }

    private fun createCooldownDrawable() = android.graphics.drawable.ColorDrawable().apply {
        setColor(android.graphics.Color.parseColor("#80000000")) // 50% transparente negro
    }

    private fun actualizarResultado(valor: String) {
        database.child("resultado").setValue(valor)
            .addOnSuccessListener {
                Toast.makeText(this, "Resultado actualizado a: $valor", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al actualizar el resultado: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }
}