package com.puj.proyectoensenarte.onboarding

//import android.content.Intent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.CrearCuentaActivity
import com.puj.proyectoensenarte.IniciarSesionActivity
import com.puj.proyectoensenarte.databinding.ActivityOnboardingThreeBinding

class OnboardingThreeActivity : Fragment() {
    private var _binding: ActivityOnboardingThreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityOnboardingThreeBinding.inflate(inflater, container, false)

        binding.botonCrearCuenta3.setOnClickListener {
            val intent = Intent(activity, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        binding.botonIniciarSesion3.setOnClickListener {
            val intent = Intent(activity, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
