package com.puj.proyectoensenarte.profile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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


        loadUserData()
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


    private fun loadUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name")
                        val nickname = document.getString("nickname")

                        // Muestra los datos en los campos de texto
                        binding?.nameProfile?.setText(name)
                        binding?.usernameProfile?.setText(nickname)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error cargando los datos del usuario", e)
                    // Toast.makeText(this, "Error cargando los datos del usuario.", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
