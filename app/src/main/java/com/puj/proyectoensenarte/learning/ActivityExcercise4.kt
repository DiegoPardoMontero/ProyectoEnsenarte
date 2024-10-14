package com.puj.proyectoensenarte.learning

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExcercise4Binding

class ActivityExcercise4 : AppCompatActivity() {

    private lateinit var binding: ActivityExcercise4Binding
    private var selectedImage: ImageView? = null
    private var selectedWord: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityExcercise4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los listeners para las imágenes
        setUpImageListeners()

        // Configurar los listeners para las palabras
        setUpWordListeners()

        // Configurar el botón Enviar
        binding.btnSubmit.setOnClickListener {
            checkAnswer()
        }
    }

    private fun setUpImageListeners() {
        binding.imageView1.setOnClickListener {
            selectImage(binding.imageView1)
        }

        binding.imageView2.setOnClickListener {
            selectImage(binding.imageView2)
        }
    }

    private fun setUpWordListeners() {
        binding.wordOption1.setOnClickListener {
            selectWord(binding.wordOption1)
        }

        binding.wordOption2.setOnClickListener {
            selectWord(binding.wordOption2)
        }
    }

    private fun selectImage(imageView: ImageView) {
        // Desmarcar la imagen previamente seleccionada@
        selectedImage?.setBackgroundColor(Color.TRANSPARENT)

        // Marcar la imagen seleccionada
        selectedImage = imageView
        selectedImage?.setBackgroundColor(Color.LTGRAY) // Cambia el color para mostrar que está seleccionada
    }

    private fun selectWord(textView: TextView) {
        // Desmarcar el texto previamente seleccionado
        selectedWord?.setBackgroundResource(R.drawable.border) // Restablecer el borde original

        // Marcar el texto seleccionado
        selectedWord = textView
        selectedWord?.setBackgroundResource(R.drawable.selected_border) // Cambia el borde para mostrar que está seleccionada
    }

    private fun checkAnswer() {
        if (selectedImage != null && selectedWord != null) {
            if (selectedImage == binding.imageView1 && selectedWord == binding.wordOption1) {
                Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrecto. Intenta de nuevo.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Selecciona una imagen y una palabra", Toast.LENGTH_SHORT).show()
        }
    }
}