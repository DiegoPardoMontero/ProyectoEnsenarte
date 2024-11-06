package com.puj.proyectoensenarte.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.CrearCuentaActivity
import com.puj.proyectoensenarte.databinding.ActivityScrollableMapBinding

class ScrollableMapActivity : Fragment() {

    private var _binding: ActivityScrollableMapBinding? = null
    private val binding get() = _binding!!
    private var isCaribbeanBannerShown = false
    private var isAmazonasBannerShown = false
    private var isFadeOutInProgress = false // Flag para controlar la animación del banner Caribe
    private var isFadeOutInProgressAmazonas = false // Flag para controlar la animación del banner Amazonas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityScrollableMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserXpPoints()
        // Listener de scroll para detectar la posición
        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // Verifica si se alcanzó el nivel caribe y no se ha mostrado el banner aún
            if (scrollY >= binding.mapImageCaribbean.top && !isCaribbeanBannerShown) {
                showCaribbeanBanner()
            }

            // Verifica si el scroll ha regresado al nivel Andina y no se está ejecutando fade out
            if (scrollY < binding.level2.top && isCaribbeanBannerShown && !isFadeOutInProgress) {
                fadeOutCaribbeanBanner()
            }

            // Verifica si se alcanzó el nivel Amazonas y no se ha mostrado el banner aún
            if (scrollY >= binding.mapImageAmazonas.top && !isAmazonasBannerShown) {
                showAmazonasBanner()
            }

            // Verifica si el scroll ha regresado a niveles anteriores y no se está ejecutando fade out
            if (scrollY < binding.level2Amazonas.top && isAmazonasBannerShown && !isFadeOutInProgressAmazonas) {
                fadeOutAmazonasBanner()
            }
        }

        // Configura los clics para los niveles
        setUpClickLevel()
    }

    private fun loadUserXpPoints() {
        // Obtén el UID del usuario autenticado@
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            // Accede a la base de datos de Firestore@
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(uid)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Obtiene el valor de "xpPoints" y actualiza el TextView
                        val xpPoints = document.getLong("xpPoints") ?: 0
                        val streakDays = document.getLong("streakDays") ?: 0
                        binding.tvPoints.text = "${xpPoints}xp"
                        binding.tvPointsCaribe.text = "${xpPoints}xp"
                        binding.tvPoints3.text = "${xpPoints}xp"
                        binding.strakDaysCaribe.text = "${streakDays}"
                        binding.tvStreakAndina.text = "${streakDays}"
                        binding.tvStreakAmazonas.text = "${streakDays}"
                    } else {
                        Log.w("FRAGMENTOS DE NIVELES LOG", "Documento de usuario no encontrado.")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("AndinaLevelFragment", "Error al cargar los puntos de experiencia del usuario", e)
                }
        } else {
            Log.w("AndinaLevelFragment", "No se encontró un usuario autenticado.")
        }
    }

    // Función para ocultar el banner del Caribe con animación
    private fun fadeOutCaribbeanBanner() {
        isFadeOutInProgress = true // Marcamos que la animación está en progreso

        binding.bannerCaribbean.apply {
            animate()
                .alpha(0f) // Desvanecer a 0 (invisible)
                .setDuration(600) // Duración de la animación
                .withEndAction {
                    visibility = View.GONE // Ocultar completamente el banner
                    alpha = 1f // Restaurar alpha a 1 para futuras animaciones
                    isFadeOutInProgress = false // La animación ha terminado
                    isCaribbeanBannerShown = false // El banner ya no está mostrado
                }
                .start()
        }
    }

    // Función para ocultar el banner del Amazonas con animación
    private fun fadeOutAmazonasBanner() {
        isFadeOutInProgressAmazonas = true // Marcamos que la animación está en progreso

        binding.bannerAmazonas.apply {
            animate()
                .alpha(0f) // Desvanecer a 0 (invisible)
                .setDuration(800) // Duración de la animación
                .withEndAction {
                    visibility = View.GONE // Ocultar completamente el banner
                    alpha = 1f // Restaurar alpha a 1 para futuras animaciones
                    isFadeOutInProgressAmazonas = false // La animación ha terminado
                    isAmazonasBannerShown = false // El banner ya no está mostrado
                }
                .start()
        }
    }

    // Función para mostrar el banner del Caribe con animación
    private fun showCaribbeanBanner() {
        binding.bannerCaribbean.apply {
            visibility = View.VISIBLE // Mostrar el banner
            alpha = 0f // Empezar invisible
            animate().alpha(1f).setDuration(900).start() // Desvanecer a visible
        }
        isCaribbeanBannerShown = true // Actualizar el estado del banner
    }

    // Función para mostrar el banner del Amazonas con animación
    private fun showAmazonasBanner() {
        binding.bannerAmazonas.apply {
            visibility = View.VISIBLE // Mostrar el banner
            alpha = 0f // Empezar invisible
            animate().alpha(1f).setDuration(2000).start() // Desvanecer a visible
        }
        isAmazonasBannerShown = true // Actualizar el estado del banner@
    }

    // Configura los clicks de los niveles@
    private fun setUpClickLevel() {
        // Niveles Región Andina
        binding.level1.setOnClickListener {
            val intent = Intent(activity, Lesson1Activity::class.java)
            startActivity(intent)
        }

        binding.level2.setOnClickListener {
            val intent = Intent(activity,Lesson2Activity::class.java)
            startActivity(intent)

        }

        binding.level3.setOnClickListener {
            val intent = Intent(activity,Lesson3Activity::class.java)
            startActivity(intent)
        }

        binding.lockAndina.setOnClickListener {
            val intent = Intent(activity, ActivityTestInfo::class.java)
            startActivity(intent)
            //Toast.makeText(context, "Nivel bloqueado en Andina", Toast.LENGTH_SHORT).show()
        }

        // Niveles Región Caribe
        binding.level1Caribbean.setOnClickListener {
            val intent = Intent(activity,Lesson4Activity::class.java)
            startActivity(intent)
        }

        binding.level2Caribbean.setOnClickListener {
            val intent = Intent(activity,Lesson5Activity::class.java)
            startActivity(intent)
        }

        binding.level3Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.lockCaribbean.setOnClickListener {
            Toast.makeText(context, "Nivel bloqueado en Caribe", Toast.LENGTH_SHORT).show()
        }

        // Niveles Región Amazónica
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