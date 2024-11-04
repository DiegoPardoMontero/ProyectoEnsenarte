import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityError404FragmentBinding
import com.puj.proyectoensenarte.dictionary.ResultadoBusquedaCategoriaFragment
import com.puj.proyectoensenarte.dictionary.ResultadoBusquedaPalabraFragment

class Error404 : Fragment() {

    private var _binding: ActivityError404FragmentBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityError404FragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
    }

    private fun setupSearchBar() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.ivSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        var searchQuery = binding?.etSearch?.text.toString().trim()
        searchQuery = searchQuery.lowercase()
        if (searchQuery.isEmpty()) {
            Toast.makeText(context, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard()

        db.collection("dict").document(searchQuery).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    navigateToResultadoBusquedaCategoria(searchQuery)
                } else {
                    searchQuery = capitalizeFirstLetter(searchQuery)
                    searchInPalabras(searchQuery)
                }
            }
            .addOnFailureListener { e ->
                Log.e("DictionaryFragment", "Error buscando categoría: ", e)
                navigateToError404()
            }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun searchInPalabras(searchQuery: String) {
        db.collection("dict").document("dict").get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data
                    if (palabras?.containsValue(searchQuery) == true) {
                        navigateToResultadoBusquedaPalabra(searchQuery)
                    } else {
                        navigateToError404()
                    }
                } else {
                    navigateToError404()
                }
            }
            .addOnFailureListener { e ->
                navigateToError404()
            }
    }

    private fun navigateToResultadoBusquedaCategoria(categoria: String) {
        var categoria = capitalizeFirstLetter(categoria)
        val fragment = ResultadoBusquedaCategoriaFragment.newInstance(categoria)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToResultadoBusquedaPalabra(palabra: String) {
        val fragment = ResultadoBusquedaPalabraFragment.newInstance(palabra)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToError404() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, Error404())
            .addToBackStack(null)
            .commit()
    }

    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }
}