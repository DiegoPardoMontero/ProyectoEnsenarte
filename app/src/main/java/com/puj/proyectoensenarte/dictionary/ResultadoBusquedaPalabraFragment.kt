package com.puj.proyectoensenarte.dictionary

import Palabra
import PalabraAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaPalabraBinding

class ResultadoBusquedaPalabraFragment : BaseSearchFragment() {
    private var _binding: ActivityResultadoBusquedaPalabraBinding? = null
    private val binding get() = _binding!!
    private lateinit var palabraAdapter: PalabraAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityResultadoBusquedaPalabraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchBar()
        setupRecyclerView()
        arguments?.getString("SEARCH_QUERY")?.let { performInitialSearch(it) }
    }

    private fun setupSearchBar() {
        binding.etSearch.setText(arguments?.getString("SEARCH_QUERY"))
        binding.ivSearch.setOnClickListener {
            handleSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            navigateToDetallePalabra(palabra)
        }
        binding.rvSearchResults.apply {
            adapter = palabraAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun performInitialSearch(query: String) {
        handleSearch(query)
    }

    override fun navigateToCategory(category: SearchResult.Category) {
        // Navegar al fragmento de categoría
        val fragment = ResultadoBusquedaCategoriaFragment.newInstance(category.name)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToWord(word: SearchResult.Word) {
        palabraAdapter.submitList(listOf(Palabra(
            id = word.id,
            texto = word.texto
        )))
    }

    override fun navigateToError404() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, Error404())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToDetallePalabra(palabra: String) {
        val intent = Intent(requireContext(), DetallePalabraActivity::class.java).apply {
            putExtra("PALABRA", palabra)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(searchQuery: String): ResultadoBusquedaPalabraFragment {
            return ResultadoBusquedaPalabraFragment().apply {
                arguments = Bundle().apply {
                    putString("SEARCH_QUERY", searchQuery)
                }
            }
        }
    }
}