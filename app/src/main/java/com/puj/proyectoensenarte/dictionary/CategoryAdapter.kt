package com.puj.proyectoensenarte.dictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.R

data class Category(val imageUrl: String, val name: String)

class CategoryAdapter(
    private val context: Context,
    private val categoryList: List<Category>,
    private val onItemClicked: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgCategoryIcon)
        val name: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        // Usar Glide para cargar la imagen desde la URL
        Glide.with(context)
            .load(category.imageUrl)  // Cargar imagen desde la URL
            .placeholder(R.drawable.vestuario)  // Imagen de carga por defecto
            .into(holder.icon)

        holder.name.text = category.name  // Setear el nombre

        // Manejar clic en cada ítem
        holder.itemView.setOnClickListener {
            onItemClicked(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}
