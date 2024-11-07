package com.puj.proyectoensenarte.dictionary

sealed class SearchResult {
    data class Category(val name: String, val imageUrl: String) : SearchResult()
    data class Word(val id: String, val texto: String) : SearchResult() // Modificado para coincidir con Palabra
    object NotFound : SearchResult()
}