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
            db.collection("dict").document(query).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Obtener URL de imagen
                        storage.reference.child("imagenesCategorias/${query.capitalize()}.png")
                            .downloadUrl
                            .addOnSuccessListener { uri ->
                                continuation.resume(SearchResult.Category(
                                    name = query.capitalize(),
                                    imageUrl = uri.toString()
                                ))
                            }
                            .addOnFailureListener {
                                continuation.resume(SearchResult.Category(
                                    name = query.capitalize(),
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
                        if (palabras?.containsValue(query.capitalize()) == true) {
                            continuation.resume(SearchResult.Word(
                                word = query.capitalize(),
                                meaning = palabras[query.capitalize()].toString()
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