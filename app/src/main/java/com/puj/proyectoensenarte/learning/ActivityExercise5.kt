package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityExercise5Binding

class ActivityExercise5 : AppCompatActivity() {

    private lateinit var binding: ActivityExercise5Binding
    private var selectionCounter = 1
    private val selectedImages = mutableMapOf<Int, Int>()
    private var maxLetters: Int = 0
    private lateinit var correctAnswer: List<String>
    private lateinit var videoUrls: ArrayList<String>
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityExercise5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del ejercicio
        val statement = intent.getStringExtra("statement") ?: ""
        correctAnswer = intent.getStringArrayListExtra("correctAnswer") ?: listOf()
        maxLetters = intent.getIntExtra("maxLetters", 5)
        videoUrls = intent.getStringArrayListExtra("videos") ?: arrayListOf()
        points = intent.getIntExtra("points", 0)

        binding.tvQuestion.text = statement


        Log.d ("VIDEOOOOOOOSSSS", videoUrls.toString())
        // Cargar las imágenes o videos según las URLs obtenidas@
        loadVideos(videoUrls)



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



        // Botón de enviar
        binding.btnSubmit.setOnClickListener {
            if (selectedImages.size == maxLetters) {
                validateAnswer() // Validar si la selección es correcta
            } else {
                Toast.makeText(this, "Selecciona exactamente $maxLetters letras", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para cargar las imágenes o videos en los ImageViews correspondientes@
    private fun loadVideos(videoUrls: ArrayList<String>) {
        val imageViews = listOf(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9
        )

        // Iterar a través de videoUrls y asignarlos a los ImageView disponibles usando Glide
        for (i in videoUrls.indices) {
            imageViews.getOrNull(i)?.let { imageView ->
                Glide.with(this)
                    .load(videoUrls[i])
                    .into(imageView)
            }
        }
    }

    // Método para configurar la selección de imágenes
    private fun setupImageSelection(imageView: ImageView, selectionText: TextView, imageId: Int) {
        imageView.setOnClickListener {
            if (selectedImages.containsKey(imageId)) {
                // Si la imagen ya está seleccionada, se quita la selección
                selectionText.visibility = View.GONE
                selectedImages.remove(imageId)
                reassignNumbers()  // Reasigna los números tras eliminar una selección
            } else {
                // Si no se ha alcanzado el límite, se permite seleccionar
                if (selectedImages.size < maxLetters) {
                    // Asignar el siguiente número de selección
                    selectionText.text = selectionCounter.toString()
                    selectionText.visibility = View.VISIBLE
                    selectedImages[imageId] = selectionCounter
                    selectionCounter++
                } else {
                    Toast.makeText(this, "Solo puedes seleccionar $maxLetters letras", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Método para reasignar los números de selección en las imágenes
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



    // Método para validar la respuesta
    private fun validateAnswer() {
        // Obtener los IDs de selección ordenados
        val selectedOrder = selectedImages.keys.sortedBy { selectedImages[it] }.mapNotNull { videoUrls.getOrNull(it - 1) }

        // Comparar la secuencia seleccionada con la respuesta correcta@
        if (selectedOrder == correctAnswer) {
            showCorrectResultDialog()
        } else {
            showIncorrectResultDialog()
        }
    }


    private fun showCorrectResultDialog() {
        val dialog = CorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("pointsEarned", points)
            setResult(RESULT_OK, resultIntent)
            finish() // Volver a Lesson1Activity
        }
        dialog.show(supportFragmentManager, "CorrectResultDialog")
    }

    private fun showIncorrectResultDialog() {
        val dialog = IncorrectResultBottomSheet {
            setResult(RESULT_CANCELED) // Enviar RESULT_CANCELED para respuestas incorrectas
            finish() // Volver a Lesson1Activity
        }
        dialog.show(supportFragmentManager, "IncorrectResultDialog")
    }


    private fun continueToNextExercise() {
        val resultIntent = Intent()
        resultIntent.putExtra("pointsEarned", points)
        setResult(RESULT_OK, resultIntent)
        finish() // Volver a Lesson1Activity
    }
}