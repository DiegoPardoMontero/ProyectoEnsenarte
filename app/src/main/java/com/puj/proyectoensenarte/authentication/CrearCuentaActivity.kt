package com.puj.proyectoensenarte

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityCrearCuentaBinding

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.textoIniciarSesion.setOnClickListener{
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
        binding.botonCrearCuenta.setOnClickListener{
            var name : String = binding.textFieldNombre.editText?.text.toString()
            var email : String = binding.textFieldEmail.editText?.text.toString()
            var password : String = binding.textFieldPassword.editText?.text.toString()
            var nickname : String = binding.textFieldUsuario.editText?.text.toString()
            // Validación de los campos antes de crear la cuenta
            if (validateInput(name, email, password, nickname)) {
                createAccount(name, email, password, nickname)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, nickname: String): Boolean {
        if (name.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isBlank() || password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nickname.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun createAccount(name : String, email: String, password: String, nickname : String) {
        var photoUrl = "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/fotosPerfil%2FexampleProfilePhot.png?alt=media&token=4e5af62e-506c-4b47-bcdd-3ef8b9d2659d"
        var streakDays : Number = 0
        var xpPoints : Number = 0
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, get the user's information
                    val user = auth.currentUser
                    user?.let {
                        val uid = user.uid
                        val email = user.email

                        // Create a new user with the UID
                        val userMap = hashMapOf(
                            "email" to email,
                            "uid" to uid,
                            "name" to name,
                            "nickname" to nickname,
                            "photo_url" to photoUrl,
                            "streakDays" to streakDays,
                            "xpPoints" to xpPoints
                        )

                        // Store user data in Firestore
                        db.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "User data stored successfully")
                                Toast.makeText(baseContext, "Registrado exitosamente!.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error storing user data", e)
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "No se pudo registrar!.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
