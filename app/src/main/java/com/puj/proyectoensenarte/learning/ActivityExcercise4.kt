package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExcercise4Binding

class ActivityExcercise4 : AppCompatActivity() {

    private lateinit var binding: ActivityExcercise4Binding
    private val selectedPairs = mutableMapOf<ImageView, TextView>()
    private lateinit var correctPairs: List<Map<String, String>>
    private var points: Int = 0
    private var selectedImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcercise4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val statement = intent.getStringExtra("statement") ?: ""
        correctPairs = intent.getSerializableExtra("correctPairs") as? List<Map<String, String>> ?: emptyList()
        points = intent.getIntExtra("points", 0)

        binding.tvQuestion.text = statement
        loadImages()
        setUpWordOptions()

        binding.btnSubmit.setOnClickListener {
            validateAnswer()
        }
    }

    private fun loadImages() {
        val imageViews = listOf(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4
        )

        correctPairs.forEachIndexed { index, pair ->
            val imageView = imageViews.getOrNull(index)
            imageView?.let {
                Glide.with(this)
                    .load(pair["url"])
                    .placeholder(R.drawable.placeholder_edit)
                    .error(R.drawable.img_error)
                    .into(it)

                // Guardar la URL como etiqueta para referencia en validación
                it.tag = pair["url"]

                // Listener para seleccionar la imagen
                it.setOnClickListener {
                    selectImage(imageView)
                }
            }
        }
    }

    private fun setUpWordOptions() {
        val wordViews = listOf(binding.wordOption1, binding.wordOption2, binding.wordOption3, binding.wordOption4)

        correctPairs.forEachIndexed { index, pair ->
            wordViews.getOrNull(index)?.apply {
                text = pair["word"]
                setOnClickListener {
                    selectWord(this)
                }
            }
        }
    }

    private fun selectImage(imageView: ImageView) {
        selectedImageView?.setBackgroundResource(R.drawable.border)
        selectedImageView = imageView
        selectedImageView?.setBackgroundResource(R.drawable.selected_border)
        //Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
    }

    private fun selectWord(textView: TextView) {
        if (selectedImageView != null) {
            selectedPairs[selectedImageView!!] = textView

            // Marcar la palabra como seleccionada
            textView.isEnabled = false
            textView.setBackgroundResource(R.drawable.selected_border)

            //Toast.makeText(this, "Asignaste '${textView.text}' a la imagen seleccionada.", Toast.LENGTH_SHORT).show()
        } else {
           Toast.makeText(this, "Por favor selecciona primero una imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAnswer() {
        if (selectedPairs.size < correctPairs.size) {
            Toast.makeText(this, "Debes seleccionar una palabra para cada imagen", Toast.LENGTH_SHORT).show()
            return
        }

        var correctCount = 0

        selectedPairs.forEach { (imageView, wordView) ->
            val selectedUrl = imageView.tag as? String
            val selectedWord = wordView.text.toString()

            // Verificar si el par seleccionado (URL y palabra) está en los pares correctos
            val isCorrectPair = correctPairs.any { it["url"] == selectedUrl && it["word"] == selectedWord }

            if (isCorrectPair) {
                correctCount++
            }
        }

        if (correctCount == correctPairs.size) {
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
        finish() // Volver a Lesson1Activity@
    }
}