package com.puj.proyectoensenarte.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityZoomInsigniaBinding

class ZoomInsigniaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomInsigniaBinding
    private var insigniaName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomInsigniaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el nombre de la insignia desde el intent
        insigniaName = intent.getStringExtra("insignia_name")

        // Mostrar el nombre de la insignia en el título
        binding.insigniaTitle.text = insigniaName

        // Cargar la descripción y la imagen de la insignia desde Firestore
        loadInsigniaDescriptionFromFirestore(insigniaName)

        // Asignar el comportamiento de cerrar la actividad
        binding.closeButton.setOnClickListener {
            finish() // Cerrar la actividad al hacer clic en el botón de cerrar
        }

        // Configurar el botón de recompensa
        binding.botonEditar.setOnClickListener {
            claimReward()
        }
    }

    private fun loadInsigniaDescriptionFromFirestore(insigniaName: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null && insigniaName != null) {
            val db = FirebaseFirestore.getInstance()
            val insigniaRef = db.collection("users").document(uid).collection("insignias")

            // Buscar el documento de la insignia por el campo "name"
            insigniaRef.whereEqualTo("name", insigniaName)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        val description = document.getString("description") ?: "Descripción no disponible"
                        val imageUrl = document.getString("url") ?: ""
                        binding.insigniaDescription.text = description
                        Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.img_placeholder)
                            .error(R.drawable.img_error)
                            .into(binding.insigniaImageZoom)
                    } else {
                        showError("Descripción no disponible")
                    }
                }
                .addOnFailureListener { exception ->
                    showError("Error al cargar la descripción: ${exception.message}")
                }
        } else {
            showError("Usuario no autenticado o nombre de insignia no válido")
        }
    }

    private fun claimReward() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        if (uid != null && insigniaName != null) {
            val db = FirebaseFirestore.getInstance()
            val insigniaRef = db.collection("users").document(uid).collection("insignias")

            // Buscar el documento de la insignia por el campo "name"@
            insigniaRef.whereEqualTo("name", insigniaName)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.documents[0]
                        val claimReward = document.getString("claimReward") ?: "false"
                        if (claimReward == "false") {
                            // Actualizar el campo claimReward a "true" y agregar puntos de experiencia
                            document.reference.update("claimReward", "true")
                                .addOnSuccessListener {
                                    // Agregar puntos de experiencia al usuario
                                    addXpPoints(uid, 10)
                                    val intent = Intent(this, RewardProfileActivity::class.java)
                                    startActivity(intent)
                                    //Toast.makeText(this, "Recompensa reclamada con éxito", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    showError("Error al reclamar recompensa: ${e.message}")
                                }
                        } else {
                            Toast.makeText(this, "Ya has reclamado esta recompensa", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        showError("No se encontró la insignia")
                    }
                }
                .addOnFailureListener { e ->
                    showError("Error al verificar recompensa: ${e.message}")
                }
        } else {
            showError("Usuario no autenticado o nombre de insignia no válido")
        }
    }

    private fun addXpPoints(uid: String, points: Int) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)

        userRef.update("xpPoints", com.google.firebase.firestore.FieldValue.increment(points.toLong()))
            .addOnSuccessListener {
                Toast.makeText(this, "$points puntos de XP agregados", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                showError("Error al agregar puntos de XP: ${e.message}")
            }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}