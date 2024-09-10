package com.puj.proyectoensenarte.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityDictionaryFragmentBinding

class DictionaryFragmentActivity : Fragment() {

    private var binding: ActivityDictionaryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityDictionaryFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lista de categorías con íconos y nombres
        val categoryList = listOf(
            Category(R.drawable.inteligencia, "Intelgencia"),
            Category(R.drawable.salud, "Salud"),
            Category(R.drawable.ser_humano, "Ser Humano"),
            Category(R.drawable.vestuario, "Vestuario"),
            Category(R.drawable.inteligencia, "Intelgencia"),
            Category(R.drawable.salud, "Salud"),
            Category(R.drawable.ser_humano, "Ser Humano"),
            Category(R.drawable.vestuario, "Vestuario"),
            Category(R.drawable.inteligencia, "Intelgencia"),
            Category(R.drawable.salud, "Salud"),
            Category(R.drawable.ser_humano, "Ser Humano"),
            Category(R.drawable.vestuario, "Vestuario")
            // Añadir más categorías según sea necesario
        )

        // Configuración del RecyclerView con GridLayoutManager
        val adapter = CategoryAdapter(requireContext(), categoryList) { category ->
            // Lógica cuando se selecciona una categoría
        }

        // Usamos binding para acceder al RecyclerView y configurarlo
        binding?.rvCategories?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.rvCategories?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
