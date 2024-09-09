package com.puj.proyectoensenarte.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityInformationFragmentBinding

class InformationFragmentActivity : Fragment() {
    private lateinit var binding: ActivityInformationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityInformationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //LINKS BUTTON
        binding.linksButton.setOnClickListener{
            Toast.makeText(requireContext(), "Links seleccionado", Toast.LENGTH_SHORT).show()
        }
        //GRAMMAR BUTTON
        binding.grammarButton.setOnClickListener{
            Toast.makeText(requireContext(), "Grammar seleccionado", Toast.LENGTH_SHORT).show()
        }
        //COMMUNITY BUTTON
        binding.communityButton.setOnClickListener{
            Toast.makeText(requireContext(), "Community seleccionado", Toast.LENGTH_SHORT).show()
        }
        //HISTORY BUTTON
        binding.historyButton.setOnClickListener{
            Toast.makeText(requireContext(), "History seleccionado", Toast.LENGTH_SHORT).show()
        }


    }


}
