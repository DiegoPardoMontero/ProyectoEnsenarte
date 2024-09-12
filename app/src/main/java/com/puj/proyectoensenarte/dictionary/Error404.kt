import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.ActivityError404FragmentBinding

class Error404 : Fragment() {

    private var _binding: ActivityError404FragmentBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun performSearch() {
        val searchQuery = binding.etSearch.text.toString()
        // Aquí iría la lógica para realizar la búsqueda
        // Por ahora, solo limpiaremos el campo de búsqueda
        binding.etSearch.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}