package com.puj.proyectoensenarte.dictionary

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class SearchManager(private val db: FirebaseFirestore, private val storage: FirebaseStorage) {
    suspend fun search(query: String): SearchResult {
        return try {
            val normalizedQuery = query.trim().lowercase()

            // Primero buscar en categorías
            val categoryResult = searchInCategories(normalizedQuery)
            if (categoryResult != null) {
                return categoryResult
            }

            // Si no es categoría, buscar en palabras
            val wordResult = searchInWords(normalizedQuery)
            if (wordResult != null) {
                return wordResult
            }

            SearchResult.NotFound
        } catch (e: Exception) {
            Log.e("SearchManager", "Error durante la búsqueda", e)
            SearchResult.NotFound
        }
    }

    private suspend fun searchInCategories(query: String): SearchResult.Category? {
        return suspendCancellableCoroutine { continuation ->
            // Formatear el query para que coincida con el formato de almacenamiento
            val formattedQuery = query.split(" ")
                .filter { it.isNotEmpty() }
                .joinToString("") { word ->
                    word.replaceFirstChar { it.uppercase() }
                }

            db.collection("dict").document(query.lowercase()).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Usar el query formateado para buscar la imagen
                        storage.reference.child("imagenesCategorias/$formattedQuery.png")
                            .downloadUrl
                            .addOnSuccessListener { uri ->
                                continuation.resume(SearchResult.Category(
                                    name = query.split(" ")
                                        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                                    imageUrl = uri.toString()
                                ))
                            }
                            .addOnFailureListener {
                                continuation.resume(SearchResult.Category(
                                    name = query.split(" ")
                                        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                                    imageUrl = "url_default"
                                ))
                            }
                    } else {
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }
    private suspend fun searchInWords(query: String): SearchResult.Word? {
        return suspendCancellableCoroutine { continuation ->
            db.collection("dict").document("dict").get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val palabras = document.data
                        val capitalizedQuery = query.capitalize()
                        if (palabras?.containsValue(capitalizedQuery) == true) {
                            continuation.resume(SearchResult.Word(
                                id = capitalizedQuery,    // Usando la palabra como ID
                                texto = capitalizedQuery  // Usando la palabra como texto
                            ))
                        } else {
                            continuation.resume(null)
                        }
                    } else {
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }
}