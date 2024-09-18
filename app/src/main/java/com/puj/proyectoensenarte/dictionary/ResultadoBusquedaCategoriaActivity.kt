import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding
import com.puj.proyectoensenarte.dictionary.Category
import com.puj.proyectoensenarte.dictionary.CategoryAdapter

class ResultadoBusquedaCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaCategoriaBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBusquedaCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
        setupRecyclerView()
        performSearch(intent.getStringExtra("SEARCH_QUERY") ?: "")
    }

    private fun setupSearchBar() {
        binding.etSearch.setText(intent.getStringExtra("SEARCH_QUERY"))
        binding.ivSearch.setOnClickListener {
            performSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            // Navegar a la pantalla de detalle de categoría
        }
        binding.rvSearchResults.adapter = categoryAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
    }

    private fun performSearch(query: String) {
        val searchResults = listOf(
            Category("url_imagen_1", "Categoría 1"),
            Category("url_imagen_2", "Categoría 2")
        )
        categoryAdapter.submitList(searchResults)
    }
}