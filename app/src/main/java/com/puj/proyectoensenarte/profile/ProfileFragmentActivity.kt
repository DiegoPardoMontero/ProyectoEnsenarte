package com.puj.proyectoensenarte.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.puj.proyectoensenarte.databinding.ActivityProfileFragmentBinding

class ProfileFragmentActivity : Fragment() {
    private var binding: ActivityProfileFragmentBinding? = null
    private var adapter: MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Data to populate the RecyclerView with
        val data = ArrayList<String>()
        data.add("Configuraciones de la cuenta")
        data.add("Estadísticas de aprendizaje")
        data.add("Cerrar Sesión")

        // Set up the RecyclerView
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        adapter = MyAdapter(context, data)
        binding?.recyclerView?.adapter = adapter

        // Add divider
        val dividerItemDecoration = DividerItemDecoration(binding?.recyclerView?.context, layoutManager.orientation)
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
