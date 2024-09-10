package com.puj.proyectoensenarte.information

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.FragmentComunidadSordaActivityBinding


class ComunidadSordaActivity : Fragment() {

    private lateinit var binding: FragmentComunidadSordaActivityBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComunidadSordaActivityBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher
        }

        val contentContainer : LinearLayout = binding.contentContainer
        val titulo1 = createTitle("Comunidad Sorda")
        val subTitulo1 = createTitle("¿Quienes son?")
        val parrafo1 = createTitle("Son una comunidad que se autodetermina como una " +
                "minoría lingüística con una cultura y una lengua propia...")


        contentContainer.addView(titulo1)
        contentContainer.addView(subTitulo1)
        contentContainer.addView(parrafo1)
    }

    fun createTitle(text: String): TextView {
        val typeface = Typeface.createFromFile("${context?.filesDir}/res/raw/regularbold.ttf")
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 24f
            setTypeface(typeface)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 0)  // Margen superior e inferior
            }
        }
    }

    fun createSubTitle(text: String): TextView {
        val typeface = Typeface.createFromFile("${context?.filesDir}/res/raw/regular.ttf")
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 18f
            setTypeface(typeface, Typeface.ITALIC)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 12, 0, 0)  // Margen superior e inferior
            }
        }
    }

    fun createParagraph(text: String): TextView {
        val typeface = Typeface.createFromFile("${context?.filesDir}/res/raw/regular.ttf")
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 16f
            setTypeface(typeface)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 0)  // Margen superior e inferior
            }
        }
    }




}