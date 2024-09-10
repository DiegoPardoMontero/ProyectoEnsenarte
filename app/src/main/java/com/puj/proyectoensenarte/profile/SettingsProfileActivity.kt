package com.puj.proyectoensenarte.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.databinding.ActivitySettingsProfileBinding

class SettingsProfileActivity : AppCompatActivity() {
    // Inicializa correctamente el binding
    private lateinit var binding: ActivitySettingsProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el layout con el binding
        binding = ActivitySettingsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el listener del botón editar
        binding.botonEditar.setOnClickListener {
            saveUserData()
        }

        binding.backButton.setOnClickListener{
            val intent = Intent(this, ProfileFragmentActivity::class.java)
            startActivity(intent)
        }

        // Carga los datos del usuario cuando se crea la actividad
        loadUserData()
    }

    private fun loadUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name")
                        val email = document.getString("email")
                        val nickname = document.getString("nickname")

                        // Muestra los datos en los campos de texto
                        binding.inputName.setText(name)
                        binding.inputEmail.setText(email)
                        binding.inputUser.setText(nickname)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error cargando los datos del usuario", e)
                    Toast.makeText(this, "Error cargando los datos del usuario.", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val name = binding.inputName.text.toString()
        val email = binding.inputEmail.text.toString()
        val nickname = binding.inputUser.text.toString()

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            val userMap = hashMapOf(
                "name" to name,
                "email" to email,
                "nickname" to nickname
            )


            db.collection("users").document(uid)
                .update(userMap as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Perfil actualizado exitosamente.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating user data", e)
                    Toast.makeText(this, "Error al actualizar el perfil.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}