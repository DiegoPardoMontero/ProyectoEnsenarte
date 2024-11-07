package com.puj.proyectoensenarte.dictionary

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

// BaseSearchFragment.kt
abstract class BaseSearchFragment : Fragment() {
    protected val searchManager: SearchManager by lazy {
        SearchManager(FirebaseFirestore.getInstance(), FirebaseStorage.getInstance())
    }

    protected fun handleSearch(query: String) {
        lifecycleScope.launch {
            val result = searchManager.search(query)
            when (result) {
                is SearchResult.Category -> navigateToCategory(result)
                is SearchResult.Word -> navigateToWord(result)
                is SearchResult.NotFound -> navigateToError404()
            }
        }
    }

    abstract fun navigateToCategory(category: SearchResult.Category)
    abstract fun navigateToWord(word: SearchResult.Word)
    abstract fun navigateToError404()
}