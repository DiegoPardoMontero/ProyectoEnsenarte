package com.puj.proyectoensenarte.learning

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.puj.proyectoensenarte.databinding.BottomSheetResultIncorrectBinding

class IncorrectResultBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetResultIncorrectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        // Inflar la vista con ViewBinding
        _binding = BottomSheetResultIncorrectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón continuar usando el bindi@ng
        binding.btnContinue.setOnClickListener {
            dismiss()  // Cierra el bottom sheet
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpia el binding para evitar fugas de memoria
    }
}