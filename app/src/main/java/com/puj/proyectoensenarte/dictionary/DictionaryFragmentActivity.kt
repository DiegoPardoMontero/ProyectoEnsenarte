package com.puj.proyectoensenarte.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.puj.proyectoensenarte.databinding.ActivityDictionaryFragmentBinding

class DictionaryFragmentActivity : Fragment() {

    private var binding: ActivityDictionaryFragmentBinding? = null
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityDictionaryFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el RecyclerView con un GridLayoutManager
        binding?.rvCategories?.layoutManager = GridLayoutManager(requireContext(), 3)

        // Inicializamos el adaptador vacío para luego actualizarlo dinámicamente
        adapter = CategoryAdapter(requireContext(), emptyList()) { category ->
            // Lógica cuando se selecciona una categoría
        }
        binding?.rvCategories?.adapter = adapter

        // Llamada a la función para obtener los nombres e imágenes de Firebase
        getImageNamesAndUrls(onSuccess = { categories ->
            // Actualizar el adaptador con las categorías obtenidas de Firebase
            adapter = CategoryAdapter(requireContext(), categories) { category ->
                // Lógica cuando se selecciona una categoría
            }
            binding?.rvCategories?.adapter = adapter
        }, onFailure = { error ->
            Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        })
    }

    fun getImageNamesAndUrls(onSuccess: (List<Category>) -> Unit, onFailure: (Exception) -> Unit) {
        // Referencia a la carpeta en Firebase Storage
        val listRef: StorageReference = FirebaseStorage.getInstance().reference.child("imagenesCategorias")

        // Lista para almacenar las categorías
        val categories = mutableListOf<Category>()

        // Listar todos los archivos en esa referencia
        listRef.listAll().addOnSuccessListener { listResult ->
            // Iterar sobre cada item (archivo)
            val tasks = listResult.items.map { item ->
                // Obtener el nombre del archivo y la URL
                item.downloadUrl.addOnSuccessListener { uri ->
                    var fileName: String = item.name
                    fileName = fileName.substringBeforeLast(".png")

                    // Insertar un espacio antes de cada letra mayúscula, excepto la primera letra
                    fileName = fileName.replace(Regex("(?<=.)([A-Z])"), " $1")

                    val downloadUrl: String = uri.toString()

                    // Crear la categoría con el nombre y la URL
                    categories.add(Category(downloadUrl, fileName)) // Category(downloadUrl, fileName)

                    // Verifica si se completaron todas las descargas y llama a onSuccess
                    if (categories.size == listResult.items.size) {
                        onSuccess(categories)
                    }
                }.addOnFailureListener { e ->
                    onFailure(e)
                }
            }

            // Ejecutar todas las tareas de descarga
            tasks.forEach { it }
        }.addOnFailureListener { e ->
            // Manejo de errores si la lista de archivos no se puede obtener
            onFailure(e)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
