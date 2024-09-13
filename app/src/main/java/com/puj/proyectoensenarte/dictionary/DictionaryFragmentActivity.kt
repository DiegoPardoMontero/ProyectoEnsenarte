package com.puj.proyectoensenarte.dictionary

import AlphabetAdapter
import Error404
import android.content.Context
import androidx.navigation.fragment.findNavController
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityDictionaryFragmentBinding

class DictionaryFragmentActivity : Fragment() {

    private var binding: ActivityDictionaryFragmentBinding? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var alphabetAdapter: AlphabetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityDictionaryFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupCategoriesRecyclerView()
        setupAlphabetRecyclerView()
        loadCategories()
    }

    private fun setupSearchBar() {
        binding?.etSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding?.ivSearch?.setOnClickListener {
            performSearch()
        }
    }

    private fun setupAlphabetRecyclerView() {
        val letters = ('A'..'Z').map { it.toString() }
        alphabetAdapter = AlphabetAdapter(letters)
        binding?.rvAlphabet?.apply {
            adapter = alphabetAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun setupCategoriesRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            // Manejar el clic en la categoría
        }
        binding?.rvCategories?.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun loadCategories() {
        categoryAdapter.loadCategories(
            onSuccess = {
                // Categorías cargadas exitosamente
            },
            onFailure = { exception ->
                // Manejar el error
            }
        )
    }

    private fun performSearch() {
        navigateToError404()
        val searchQuery = binding?.etSearch?.text.toString()
        if (searchQuery.isEmpty() || noResultsFound(searchQuery)) {
            navigateToError404()
        } else {
            // Realizar la búsqueda normal
            // Aquí iría tu lógica de búsqueda existente
        }
        hideKeyboard()
    }


    private fun noResultsFound(query: String): Boolean {
        // Implementa tu lógica para determinar si no se encontraron resultados
        // Este es solo un ejemplo, debes adaptarlo a tu lógica real
        return false
    }

    private fun navigateToError404() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, Error404())
            .addToBackStack(null)
            .commit()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.etSearch?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}
