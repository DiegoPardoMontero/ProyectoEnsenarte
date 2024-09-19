package com.puj.proyectoensenarte.dictionary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding

class ResultadoBusquedaCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaCategoriaBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBusquedaCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
        setupRecyclerView()
        performSearch(intent.getStringExtra("SEARCH_QUERY") ?: "")
    }

    private fun setupSearchBar() {
        binding.ivSearch.setOnClickListener {
            performSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            // Navegar a la pantalla de detalle de categoría
            // Implementar la navegación aquí
        }
        binding.rvSearchResults.adapter = categoryAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
    }

    private fun performSearch(query: String) {
        var query = capitalizeFirstLetter(query)
        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child("imagenesCategorias/$query.png")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val downloadUrl = uri.toString()
            val formattedQuery = query.replace(Regex("(?<=.)([A-Z])"), " $1")
            val category = Category(downloadUrl, formattedQuery)
            categoryAdapter.submitList(listOf(category))
        }.addOnFailureListener { exception ->
            Log.e("ResultadoBusqueda", "Error al cargar la imagen de la categoría: ${exception.message}")
            val category = Category("url_imagen_por_defecto", query)
            categoryAdapter.submitList(listOf(category))
        }
    }

    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }


}