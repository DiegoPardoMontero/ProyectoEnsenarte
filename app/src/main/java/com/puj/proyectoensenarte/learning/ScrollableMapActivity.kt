package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.ActivityScrollableMapBinding

class ScrollableMapActivity : Fragment() {

    private var _binding: ActivityScrollableMapBinding? = null
    private val binding get() = _binding!!
    private var isCaribbeanBannerShown = false

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

        // Listener de scroll para detectar la posición
        binding.scrollView.setOnScrollChangeListener { v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            // Verifica si se alcanzó el nivel caribe
            if (scrollY >= binding.mapImageCaribbean.top && !isCaribbeanBannerShown) {
                // Mostrar el banner de nivel Caribe si no ha sido mostrado antes
                showCaribbeanBanner()
            }
        }

        // Aquí puedes configurar los clics para los niveles como antes
        setUpClickLevel()
    }


    // Función para mostrar el banner con animación (opcional)
    private fun showCaribbeanBanner() {
        binding.bannerCaribbean.apply {
            visibility = View.VISIBLE // Hacer visible el banner
            // Agregar animación si deseas (por ejemplo, fade in o slide up)
            alpha = 0f
            animate().alpha(1f).setDuration(900).start() // Animación de desvanecimiento
        }
        isCaribbeanBannerShown = true // Actualizar el estado del banner
    }

    private fun setUpClickLevel(){
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