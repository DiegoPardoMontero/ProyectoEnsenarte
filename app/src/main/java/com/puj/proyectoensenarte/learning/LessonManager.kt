package com.puj.proyectoensenarte.learning

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LessonManager(private val context: Context) {

    fun updateXpPointsForUser(userId: String, pointsEarned: Int) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val currentPoints = document.getLong("xpPoints") ?: 0
                    val updatedPoints = currentPoints + pointsEarned

                    userRef.update("xpPoints", updatedPoints)
                        .addOnSuccessListener {
                            Log.d("com.puj.proyectoensenarte.learning.LessonManager", "Puntos actualizados correctamente a $updatedPoints")
                        }
                        .addOnFailureListener { e ->
                            Log.w("com.puj.proyectoensenarte.learning.LessonManager", "Error actualizando los puntos", e)
                        }
                } else {
                    Log.w("com.puj.proyectoensenarte.learning.LessonManager", "Documento de usuario no encontrado.")
                }
            }
            .addOnFailureListener { e ->
                Log.w("com.puj.proyectoensenarte.learning.LessonManager", "Error cargando los datos del usuario", e)
            }
    }
}

// Código para obtener el usuario autenticado y actualizar los puntos de experiencia
fun updateUserXpPoints(context: Context, pointsEarned: Int) {
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    if (uid != null) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val lessonManager = LessonManager(context)
                    lessonManager.updateXpPointsForUser(uid, pointsEarned)
                }
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error cargando los datos del usuario", e)
                Toast.makeText(context, "Error cargando los datos del usuario.", Toast.LENGTH_LONG).show()
            }
    } else {
        Log.w(ContentValues.TAG, "No se encontró un usuario autenticado.")
        Toast.makeText(context, "Usuario no autenticado. Inicia sesión.", Toast.LENGTH_SHORT).show()
    }
}