package com.puj.proyectoensenarte.dictionary

import PalabraAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorLetraBinding

class DetallePorLetraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePorLetraBinding
    private lateinit var palabraAdapter: PalabraAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorLetraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letra = intent.getStringExtra("LETRA") ?: "P"

        setupRecyclerView()
        loadPalabras(letra)
        setupUI(letra)
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter()
        binding.rvPalabrasA.apply {
            layoutManager = LinearLayoutManager(this@DetallePorLetraActivity)
            adapter = palabraAdapter
        }
    }

    private fun loadPalabras(letra: String) {
        db.collection("dictionary").document("palabras")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val todasLasPalabras = document.data?.mapValues { it.value.toString() } ?: emptyMap()
                    val palabrasFiltradas = todasLasPalabras.filter { it.value.startsWith(letra, ignoreCase = true) }
                    palabraAdapter.submitList(palabrasFiltradas.toList())
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }

    private fun setupUI(letra: String) {
        binding.tvLetraA.text = "Letra $letra"
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}