package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.FragmentAmazonasLevelBinding

class AmazonasLevelFragment : Fragment() {

    private var _binding: FragmentAmazonasLevelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmazonasLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar los clics de los niveles
        setupLevelClicks()
    }

    private fun setupLevelClicks() {
        // Configurar el clic para el nivel 1
        binding.level1.setOnClickListener {
            // Acción al hacer clic en el nivel 1 (reemplaza con la acción deseada)
            // Por ejemplo, cargar la lección correspondiente
        }

        // Configurar el clic para el nivel 2
        binding.level2.setOnClickListener {
            // Acción al hacer clic en el nivel 2 (reemplaza con la acción deseada)
        }

        // Configurar el clic para el nivel 3
        binding.level3.setOnClickListener {
            // Acción al hacer clic en el nivel 3 (reemplaza con la acción deseada)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}