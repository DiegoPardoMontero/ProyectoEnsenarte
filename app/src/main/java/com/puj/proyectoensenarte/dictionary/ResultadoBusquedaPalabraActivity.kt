package com.puj.proyectoensenarte.dictionary

import PalabraAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaPalabraBinding

class ResultadoBusquedaPalabraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaPalabraBinding
    private lateinit var palabraAdapter: PalabraAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBusquedaPalabraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
        setupRecyclerView()
        performSearch(intent.getStringExtra("SEARCH_QUERY") ?: "")
    }

    private fun setupSearchBar() {
        binding.etSearch.setText(intent.getStringExtra("SEARCH_QUERY"))
        binding.ivSearch.setOnClickListener {
            performSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            // Navegar a la pantalla de detalle de palabra
        }
        binding.rvSearchResults.adapter = palabraAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
    }

    private fun performSearch(query: String) {
        db.collection("dictionary").document("palabras")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data?.filter { (key, value) ->
                        key.contains(query, ignoreCase = true) ||
                                (value as? String)?.contains(query, ignoreCase = true) == true
                    }?.map { (key, value) ->
                        Palabra(key, value.toString())
                    } ?: listOf()
                    palabraAdapter.submitList(palabras)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }
}