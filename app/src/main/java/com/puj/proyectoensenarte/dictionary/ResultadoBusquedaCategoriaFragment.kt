package com.puj.proyectoensenarte.dictionary

import android.content.Intent
import android.os.Bundle
import com.puj.proyectoensenarte.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding

class ResultadoBusquedaCategoriaFragment : BaseSearchFragment() {
    private var _binding: ActivityResultadoBusquedaCategoriaBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityResultadoBusquedaCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
        setupRecyclerView()
        arguments?.getString("SEARCH_QUERY")?.let { performInitialSearch(it) }
    }

    private fun setupSearchBar() {
        binding.ivSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            handleSearch(query)
        }
    }

    private fun performInitialSearch(query: String) {
        handleSearch(query)
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToDetallePorCategoria(category.name, category.imageUrl)
        }
        binding.rvSearchResults.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun navigateToCategory(category: SearchResult.Category) {
        categoryAdapter.submitList(listOf(Category(category.imageUrl, category.name)))
    }

    override fun navigateToWord(word: SearchResult.Word) {
        // Navegar al fragmento de palabra
        val fragment = ResultadoBusquedaPalabraFragment.newInstance(word.texto)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToError404() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, Error404())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToDetallePorCategoria(categoria: String, imageUrl : String) {
        val intent = Intent(requireContext(), DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(searchQuery: String): ResultadoBusquedaCategoriaFragment {
            return ResultadoBusquedaCategoriaFragment().apply {
                arguments = Bundle().apply {
                    putString("SEARCH_QUERY", searchQuery)
                }
            }
        }
    }
}