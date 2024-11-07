package com.puj.proyectoensenarte.dictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityError404FragmentBinding


class Error404 : BaseSearchFragment() {
    private var _binding: ActivityError404FragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityError404FragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
    }

    private fun setupSearchBar() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.ivSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val searchQuery = binding.etSearch.text.toString().trim()
        if (searchQuery.isEmpty()) {
            Toast.makeText(context, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }
        hideKeyboard()
        handleSearch(searchQuery)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    override fun navigateToCategory(category: SearchResult.Category) {
        val fragment = ResultadoBusquedaCategoriaFragment.newInstance(category.name)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToWord(word: SearchResult.Word) {
        val fragment = ResultadoBusquedaPalabraFragment.newInstance(word.texto)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToError404() {
        // Ya estamos en Error404, no necesitamos navegar
        Toast.makeText(context, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}