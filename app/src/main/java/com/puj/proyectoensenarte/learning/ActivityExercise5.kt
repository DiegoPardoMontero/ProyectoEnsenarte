package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityExercise5Binding

class ActivityExercise5 : AppCompatActivity() {

    private lateinit var binding: ActivityExercise5Binding
    private var selectionCounter = 1
    private val selectedImages = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el binding correctamente
        binding = ActivityExercise5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar selección de imágenes
        setupImageSelection(binding.imageView1, binding.selectionNumber1, 1)
        setupImageSelection(binding.imageView2, binding.selectionNumber2, 2)
        setupImageSelection(binding.imageView3, binding.selectionNumber3, 3)
        setupImageSelection(binding.imageView4, binding.selectionNumber4, 4)
        setupImageSelection(binding.imageView5, binding.selectionNumber5, 5)
        setupImageSelection(binding.imageView6, binding.selectionNumber6, 6)
        setupImageSelection(binding.imageView7, binding.selectionNumber7, 7)
        setupImageSelection(binding.imageView8, binding.selectionNumber8, 8)
        setupImageSelection(binding.imageView9, binding.selectionNumber9, 9)
    }

    // Método para configurar la selección de imágenes
    private fun setupImageSelection(imageView: ImageView, selectionText: TextView, imageId: Int) {
        imageView.setOnClickListener {
            if (selectedImages.containsKey(imageId)) {
                // Si la imagen ya está seleccionada, se quita la selección
                selectionText.visibility = View.GONE
                selectedImages.remove(imageId)
                reassignNumbers()  // Reasigna los números tras eliminar una selección@
            } else {
                // Si la imagen no está seleccionada, se asigna el siguiente número
                selectionText.text = selectionCounter.toString()
                selectionText.visibility = View.VISIBLE
                selectedImages[imageId] = selectionCounter
                selectionCounter++
            }
        }
    }

    // Método para reasignar los números de selección en las imágenes@
    private fun reassignNumbers() {
        selectionCounter = 1
        selectedImages.forEach { (imageId, _) ->
            val textView = when (imageId) {
                1 -> binding.selectionNumber1
                2 -> binding.selectionNumber2
                3 -> binding.selectionNumber3
                4 -> binding.selectionNumber4
                5 -> binding.selectionNumber5
                6 -> binding.selectionNumber6
                7 -> binding.selectionNumber7
                8 -> binding.selectionNumber8
                9 -> binding.selectionNumber9
                else -> null
            }
            textView?.let {
                it.text = selectionCounter.toString()
                selectionCounter++
            }
        }
    }
}