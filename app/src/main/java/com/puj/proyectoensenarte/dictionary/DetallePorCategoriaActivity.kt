package com.puj.proyectoensenarte.dictionary

import Palabra
import PalabraAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorCategoriaBinding
import java.text.Normalizer

class DetallePorCategoriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePorCategoriaBinding
    private lateinit var palabraAdapter: PalabraAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoria = intent.getStringExtra("CATEGORIA") ?: "Desconocida"
        val categoriaImageUrl = intent.getStringExtra("CATEGORIA_IMAGE_URL")

        setupUI(categoria, categoriaImageUrl)
        setupRecyclerView()
        loadPalabras(categoria)
    }

    private fun setupUI(categoria: String, categoriaImageUrl: String?) {
        binding.tvCategoriaTitulo.text = categoria

        categoriaImageUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivCategoriaIcon)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            navigateToDetallePalabra(palabra)
        }
        binding.rvPalabrasCategoria.apply {
            layoutManager = LinearLayoutManager(this@DetallePorCategoriaActivity)
            adapter = palabraAdapter
        }
    }

    private fun navigateToDetallePalabra(palabra: String) {
        val intent = Intent(this, DetallePalabraActivity::class.java).apply {
            putExtra("PALABRA", palabra)
        }
        startActivity(intent)
    }

    private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    private fun CharSequence.unaccent(): String {
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return REGEX_UNACCENT.replace(temp, "")
    }

    private fun loadPalabras(categoria: String) {
        var categoriaNormalizada = categoria.lowercase()
        categoriaNormalizada = categoriaNormalizada.unaccent()

        Log.i("INFO", "LA CATEGORIA TRANSFORMADA ES: $categoriaNormalizada")

        db.collection("dict").document(categoriaNormalizada)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data?.map { (key, value) ->
                        Palabra(key, value.toString())
                    }?.sortedBy {
                        it.texto.unaccent().lowercase() // Ordenar ignorando acentos y mayúsculas
                    } ?: emptyList()

                    palabraAdapter.submitList(palabras)
                } else {
                    // Manejar el caso en que no se encuentren palabras
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }
}

