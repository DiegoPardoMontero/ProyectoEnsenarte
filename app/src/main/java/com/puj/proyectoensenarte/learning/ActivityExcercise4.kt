package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityExcercise4Binding

class ActivityExcercise4 : AppCompatActivity() {

    private lateinit var binding: ActivityExcercise4Binding
    private val selectedPairs = mutableMapOf<ImageView, TextView>()
    private lateinit var correctPairs: List<Map<String, String>>
    private var points: Int = 0
    private var selectedImageView: ImageView? = null
    private val matchedImages = mutableSetOf<ImageView>()
    private var countBackground = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcercise4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val statement = intent.getStringExtra("statement") ?: ""
        correctPairs = intent.getSerializableExtra("correctPairs") as? List<Map<String, String>> ?: emptyList()
        points = intent.getIntExtra("points", 0)
        val lessonName = intent.getStringExtra("lessonName") ?: "Lección desconocida"

        binding.tvTitle.text = lessonName
        binding.tvQuestion.text = statement
        loadImages()
        setUpWordOptions()
        configureCloseButton()

        binding.btnSubmit.setOnClickListener {
            validateAnswer()
        }

        binding.btnBorrar.setOnClickListener{
            countBackground = 1
            selectedPairs.clear()
            matchedImages.clear()
            binding.imageView1.setBackgroundResource(0)
            binding.imageView2.setBackgroundResource(0)
            binding.imageView3.setBackgroundResource(0)
            binding.imageView4.setBackgroundResource(0)
            binding.wordOption1.setBackgroundResource(R.drawable.border)
            binding.wordOption2.setBackgroundResource(R.drawable.border)
            binding.wordOption3.setBackgroundResource(R.drawable.border)
            binding.wordOption4.setBackgroundResource(R.drawable.border)
            binding.wordOption1.isEnabled = true
            binding.wordOption2.isEnabled = true
            binding.wordOption3.isEnabled = true
            binding.wordOption4.isEnabled = true

            selectedImageView = null

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.putExtra("selected_fragment", R.id.item_1) // Seleccionar el fragmento deseado
        startActivity(intent)
        finishAffinity() // Cierra todas las actividades anteriores en la pila@
    }

    private fun configureCloseButton() {
        binding.closeButton.setOnClickListener {
            showExitConfirmationDialog()
        }
    }


    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Salir de la lección")
            .setMessage("¿Estás seguro de que deseas salir? No ganarás puntos por esta lección.")
            .setPositiveButton("Sí") { _, _ ->
                try {
                    // Redirigir a ScrollableMapActivity@
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)
                    Log.d("Exercise1Activity", "Navigating to BottomNavigationActivity")
                    finish() // Cierra Exercise1Activity
                } catch (e: Exception) {
                    Log.e("Exercise1Activity", "Error al navegar a BottomNavigationActivity", e)
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
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
        val wordViews = listOf(
            binding.wordOption1,
            binding.wordOption2,
            binding.wordOption3,
            binding.wordOption4
        )

        // Extraer solo las palabras de los pares correctos
        val words = correctPairs.map { it["word"] ?: "" }

        // Mezclar aleatoriamente las palabras
        val shuffledWords = words.shuffled()

        // Asignar las palabras mezcladas a los TextViews
        wordViews.forEachIndexed { index, textView ->
            textView.text = shuffledWords.getOrNull(index) ?: ""
            textView.setOnClickListener {
                selectWord(textView)
            }
        }
    }

    private fun selectImage(imageView: ImageView) {
        if (selectedImageView != null && !matchedImages.contains(selectedImageView)) {
            selectedImageView?.setBackgroundResource(0)
            selectedImageView = null
        }

        selectedImageView = imageView
        if (countBackground == 1){
            selectedImageView?.setBackgroundResource(R.drawable.selected_background) // Fondo temporal de selección
        }else if (countBackground == 2){
            selectedImageView?.setBackgroundResource(R.drawable.selected_background_1) // Fondo temporal de selección
        }else if (countBackground == 3){
            selectedImageView?.setBackgroundResource(R.drawable.selected_background_2) // Fondo temporal de selección
        }else if (countBackground == 4){
            selectedImageView?.setBackgroundResource(R.drawable.selected_background_3) // Fondo temporal de selección
        }
        //Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
    }

    private fun selectWord(textView: TextView) {
        if (selectedImageView != null) {
            selectedPairs[selectedImageView!!] = textView
            matchedImages.add(selectedImageView!!)

            // Marcar la palabra como seleccionada
            textView.isEnabled = false
            selectedImageView= null
            if (countBackground == 1){
                textView.setBackgroundResource(R.drawable.selected_border) // Fondo temporal de selección
            }else if (countBackground == 2){
                textView.setBackgroundResource(R.drawable.selected_border_1) // Fondo temporal de selección
            }else if (countBackground == 3){
                textView.setBackgroundResource(R.drawable.selected_border_2) // Fondo temporal de selección
            }else if (countBackground == 4){
                textView.setBackgroundResource(R.drawable.selected_border_3) // Fondo temporal de selección
            }
            countBackground++

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
            resultIntent.putExtra("correctAnswer", true) // Indicar que la respuesta fue correcta
            setResult(RESULT_OK, resultIntent)
            finish() // Volver a Lesson1Activity
        }
        dialog.show(supportFragmentManager, "CorrectResultDialog")
    }

    private fun showIncorrectResultDialog() {
        val dialog = IncorrectResultBottomSheet {
            val resultIntent = Intent()
            resultIntent.putExtra("correctAnswer", false) // Indicar que la respuesta fue incorrecta
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