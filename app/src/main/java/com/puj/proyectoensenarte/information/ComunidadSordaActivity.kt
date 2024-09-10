package com.puj.proyectoensenarte.information

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, InformationFragmentActivity()) // Aquí el ID del contenedor donde se muestran los fragmentos
                .addToBackStack(null)  // Esto permite volver al fragmento anterior
                .commit()
        }

        val contentContainer : LinearLayout = binding.contentContainer
        val titulo1 = createTitle("Comunidad Sorda")
        val subTitulo1 = createSubTitle("¿Quienes son?")
        val parrafo1 = createParagraph("Son una comunidad que se autodetermina como una minoría lingüística " +
                "con una cultura y una lengua propia, que es la Lengua de Señas. Esta lengua cumple con todas las " +
                "leyes lingüísticas y se aprende dentro de una comunidad de usuarios, facilitando la resolución de " +
                "todas las necesidades comunicativas y no comunicativas propias del ser humano, tanto a nivel social " +
                "como cultural.\n" +
                "\n" +
                "La comunidad sorda, destaca que no se considera discapacitados o incompetentes, ni se autodefinen de esa manera. Aspiran a que los oyentes tampoco los definan como personas con discapacidad, sino como individuos diferentes. Muchos incluso, se consideran biculturales, mostrando una clara conciencia de su naturaleza lingüística, sin que ello represente grandes barreras de exclusión, como las experimentadas por otros grupos.")
        val image1 = createImageView(R.drawable.crearcuenta)

        contentContainer.addView(titulo1)
        contentContainer.addView(subTitulo1)
        contentContainer.addView(parrafo1)
        contentContainer.addView(image1)

    }

    fun createTitle(text: String): TextView {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regularbold)
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
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regular)
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
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regular)
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

    fun createImageView(imageResId: Int): ImageView {
        val roundedDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.rounded_image_background,
            null
        )

        val imageView = ImageView(requireContext()).apply {
            setImageResource(imageResId)  // Establecer la imagen desde un recurso
            scaleType = ImageView.ScaleType.CENTER_CROP  // Ajustar el tipo de escala
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200.dpToPx()  // Limitar la altura a 300dp
            ).apply {
                setMargins(0, 16, 0, 8)  // Márgenes
            }
            adjustViewBounds = true  // Asegurar que la imagen se ajuste a los límites
            clipToOutline = true  // Permitir que la imagen se recorte según el contorno
            background = roundedDrawable
        }
        return imageView
    }


    fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()


}