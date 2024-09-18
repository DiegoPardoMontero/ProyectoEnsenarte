package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.ActivityScrollableMapBinding

class ScrollableMapActivity : Fragment() {

    private var _binding: ActivityScrollableMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        _binding = ActivityScrollableMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar los clics de los niveles para el mapa de la Región Andina
        binding.level1.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
            // Aquí puedes abrir la lección correspondiente
        }

        binding.level2.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
        }

        binding.level3.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
        }

        binding.lockAndina.setOnClickListener {
            Toast.makeText(context, "Nivel bloqueado", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar lógica para desbloquear el siguiente nivel
        }

        // Configurar los clics de los niveles para el mapa de la Región Caribe
        binding.level1Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.level2Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.level3Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.lockCaribbean.setOnClickListener {
            Toast.makeText(context, "Nivel bloqueado en Caribe", Toast.LENGTH_SHORT).show()
        }

        // Configurar los clics de los niveles para el mapa de la Región Amazónica
        binding.level1Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }

        binding.level2Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }

        binding.level3Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}