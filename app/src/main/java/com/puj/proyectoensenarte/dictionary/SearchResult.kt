package com.puj.proyectoensenarte.dictionary

sealed class SearchResult {
    data class Category(val name: String, val imageUrl: String) : SearchResult()
    data class Word(val word: String, val meaning: String) : SearchResult()
    object NotFound : SearchResult()
}