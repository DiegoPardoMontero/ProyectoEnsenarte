package com.puj.proyectoensenarte.learning

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.puj.proyectoensenarte.databinding.BottomSheetResultIncorrectBinding

class IncorrectResultBottomSheet(private val onContinue: () -> Unit) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetResultIncorrectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        // Inflar la vista con ViewBinding@
        _binding = BottomSheetResultIncorrectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón continuar
        binding.btnContinue.setOnClickListener {
            dismiss()  // Cerrar el BottomSheetDialog@
            onContinue() // Llamar la función callback para continuar
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpia el binding para evitar fugas de memoria
    }
}