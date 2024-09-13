package com.puj.proyectoensenarte.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityStatisticsProfileBinding

class StatisticsProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsProfileBinding
    private lateinit var adapter: InsigniaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar la imagen del perfil del usuario
        loadProfileImageFromFirestore()

        // Configura el RecyclerView con un GridLayoutManager
        binding.recyclerViewInsignias.layoutManager = GridLayoutManager(this, 3)
        adapter = InsigniaAdapter(this, listOf()) { insignia ->
            Toast.makeText(this, "Seleccionaste: ${insignia.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewInsignias.adapter = adapter

        // Cargar las insignias desde Firestore
        loadInsigniasFromFirestore()


    }

    private fun loadInsigniasFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("insignia").get()
            .addOnSuccessListener { result ->
                val insigniaList = mutableListOf<Insignia>()
                for (document in result) {
                    val nombre = document.getString("name") ?: "Nombre desconocido"
                    val fotoUrl = document.getString("url") ?: ""

                    if (fotoUrl.isNotEmpty()) {
                        insigniaList.add(Insignia(fotoUrl, nombre))
                    } else {
                        Log.e("Firestore", "URL de la foto no disponible para $nombre")
                    }
                }
                adapter.updateData(insigniaList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar las insignias: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadProfileImageFromFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()

        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val photoUrl = document.getString("photo_url")
                        Glide.with(this)
                            .load(photoUrl) // URL de la imagen
                            .placeholder(R.drawable.img_placeholder) // Imagen de placeholder mientras se carga
                            .error(R.drawable.img_placeholder) // Imagen a mostrar en caso de error
                            .into(binding.avatarImage)
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar la imagen de perfil", Toast.LENGTH_SHORT).show()
                }
        }
    }
}