package com.puj.proyectoensenarte.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityStatisticsProfileBinding

class StatisticsProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsProfileBinding
    private lateinit var adapter: InsigniaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar la imagen del perfil del usuario
        loadProfileImageFromFirestore()

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configura el RecyclerView con un GridLayoutManager
        binding.recyclerViewInsignias.layoutManager = GridLayoutManager(this, 3)
        adapter = InsigniaAdapter(this, listOf()) { insignia ->
            //Toast.makeText(this, "Seleccionaste: ${insignia.name}", Toast.LENGTH_SHORT).show()@
        }
        binding.recyclerViewInsignias.adapter = adapter

        // Cargar las insignias desde Firestore@
        loadInsigniasFromFirestore()
        loadStreakDaysPxPoints()

    }

    // Método para manejar la acción de retroceso del botón en la Toolb@ar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Crear un intent para volver a BottomNavigationActivity y cargar el fragmento de perfil
                val intent = Intent(this, BottomNavigationActivity::class.java)
                intent.putExtra("navigate_to_item", R.id.item_4)  // Indicar que queremos abrir el item 4 (perfil)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun loadStreakDaysPxPoints() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()

        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {

                        val streakDays = document.getLong("streakDays")?.toString() ?: "--"
                        val xpPoints = document.getLong("xpPoints")?.toString() ?: "--"

                        binding.streakDaysText.text = streakDays
                        binding.XPonints.text = xpPoints
                    } else {
                        Log.d("Firestore", "No existe documento para el usuario.")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar racha y puntos de XP", Toast.LENGTH_SHORT).show()
                    Log.e("Firestore", "Error al obtener documento", e)
                }
        }
    }

    private fun loadInsigniasFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            db.collection("users").document(uid).collection("insignias").get()
                .addOnSuccessListener { result ->
                    val insigniaList = mutableListOf<Insignia>()
                    for (document in result) {
                        val nombre = document.getString("name") ?: "Nombre desconocido"
                        val status = document.getString("status") ?: "inactivo"
                        val fotoUrl = if (status == "activated") {
                            document.getString("url") ?: ""
                        } else {
                            document.getString("url_deactivated") ?: ""
                        }

                        if (fotoUrl.isNotEmpty()) {
                            insigniaList.add(Insignia(fotoUrl, nombre, status))
                        } else {
                            Log.e("Firestore", "URL de la foto no disponible para $nombre")
                        }
                    }
                    // Actualizar el adaptador con la lista de insignias@
                    adapter.updateData(insigniaList)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error al cargar las insignias: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfileImageFromFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val db = FirebaseFirestore.getInstance()

        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val photoUrl = document.getString("photo_url")
                        Glide.with(this)
                            .load(photoUrl) // URL de la imagen
                            .placeholder(R.drawable.img_placeholder) // Imagen de placeholder mientras se carga
                            .error(R.drawable.img_placeholder) // Imagen a mostrar en caso de error
                            .into(binding.avatarImage)
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar la imagen de perfil", Toast.LENGTH_SHORT).show()
                }
        }
    }
}