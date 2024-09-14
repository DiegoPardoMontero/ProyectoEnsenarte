package com.puj.proyectoensenarte.dictionary


import PalabraAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorCategoriaBinding

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
        palabraAdapter = PalabraAdapter()
        binding.rvPalabrasCategoria.apply {
            layoutManager = LinearLayoutManager(this@DetallePorCategoriaActivity)
            adapter = palabraAdapter
        }
    }

    private fun loadPalabras(categoria: String) {
        var categoria = primeraLetraMinuscula(categoria)
        db.collection("dictionary").document(categoria)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data?.map { (key, value) ->
                        Palabra(key, value.toString())
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

    fun primeraLetraMinuscula(texto: String): String {
        return texto.replaceFirstChar { it.lowercase() }
    }
}

data class Palabra(val id: String, val texto: String)
