package com.puj.proyectoensenarte.learning

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDictionary {

    // Función para buscar una palabra y devolver si está y la seniaURL
    fun searchSignByName(signName: String, onResult: (Boolean, String?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val dictRef = db.collection("dict").document("palabras")

        dictRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Aquí obtenemos el mapa de las palabras
                    val wordsMap = document.data

                    // Verificar si la palabra está en el documento
                    if (wordsMap != null && wordsMap.containsKey(signName)) {
                        // Obtener los datos de la palabra
                        val signData = wordsMap[signName] as? Map<String, Any>
                        val seniaURL = signData?.get("seniaURL") as? String

                        Log.d("FirestoreDictionary", "Palabra '$signName' encontrada con URL: $seniaURL")
                        onResult(true, seniaURL)  // Indicar que la palabra fue encontrada y devolver la URL
                    } else {
                        Log.d("FirestoreDictionary", "Palabra '$signName' no encontrada.")
                        onResult(false, null)  // Indicar que la palabra no fue encontrada
                    }
                } else {
                    Log.d("FirestoreDictionary", "Documento 'palabras' no encontrado.")
                    onResult(false, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreDictionary", "Error al buscar la palabra: ", exception)
                onResult(false, null)
            }
    }
}