import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.databinding.ItemPalabraBinding
class PalabraAdapter : ListAdapter<Pair<String, String>, PalabraAdapter.PalabraViewHolder>(PalabraDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalabraViewHolder {
        val binding = ItemPalabraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PalabraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PalabraViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PalabraViewHolder(private val binding: ItemPalabraBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(palabra: Pair<String, String>) {
            binding.tvPalabra.text = palabra.second
        }
    }

    class PalabraDiffCallback : DiffUtil.ItemCallback<Pair<String, String>>() {
        override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem == newItem
        }
    }
}