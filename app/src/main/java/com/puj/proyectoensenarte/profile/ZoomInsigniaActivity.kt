package com.puj.proyectoensenarte.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityZoomInsigniaBinding

class ZoomInsigniaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomInsigniaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomInsigniaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el nombre de la insignia desde el intent
        val insigniaName = intent.getStringExtra("insignia_name")

        // Mostrar el nombre de la insignia en el título
        binding.insigniaTitle.text = insigniaName

        // Cargar la descripción y la imagen de la insignia desde Firestore
        loadInsigniaDescriptionFromFirestore(insigniaName)

        // Asignar el comportamiento de cerrar la actividad
        binding.closeButton.setOnClickListener {
            finish() // Cerrar la actividad al hacer clic en el botón de cerrar
        }

        // Configurar el botón de recompensa para navegar a RewardProfileActivity
        binding.botonEditar.setOnClickListener {
            val intent = Intent(this, RewardProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadInsigniaDescriptionFromFirestore(insigniaName: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null && insigniaName != null) {
            val db = FirebaseFirestore.getInstance()
            val insigniaRef = db.collection("users").document(uid).collection("insignias")

            insigniaRef.whereEqualTo("name", insigniaName)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        val description = document.getString("description") ?: "Descripción no disponible"
                        val imageUrl = document.getString("url") ?: "" // Obtener la URL de la insignia activada

                        binding.insigniaDescription.text = description

                        // Cargar la imagen de la insignia con Glide
                        Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.img_placeholder) // Imagen de carga por defecto@
                            .error(R.drawable.img_error)            // Imagen de error
                            .into(binding.insigniaImageZoom)
                    } else {
                        binding.insigniaDescription.text = "Descripción no disponible"
                        Glide.with(this)
                            .load(R.drawable.img_error) // Imagen de error si no se encuentra la insignia
                            .into(binding.insigniaImageZoom)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error al cargar la descripción: ${exception.message}", Toast.LENGTH_SHORT).show()
                    binding.insigniaDescription.text = "Descripción no disponible"
                    Glide.with(this)
                        .load(R.drawable.img_error)
                        .into(binding.insigniaImageZoom)
                }
        } else {
            binding.insigniaDescription.text = "Usuario no autenticado o nombre de insignia no válido"
            Glide.with(this)
                .load(R.drawable.img_error)
                .into(binding.insigniaImageZoom)
        }
    }
}