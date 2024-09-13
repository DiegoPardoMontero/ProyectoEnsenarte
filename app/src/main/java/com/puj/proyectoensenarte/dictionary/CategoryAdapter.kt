package com.puj.proyectoensenarte.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.R

data class Category(val imageUrl: String, val name: String)

class CategoryAdapter(
    private val onItemClicked: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = listOf()

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgCategoryIcon)
        val name: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        Glide.with(holder.itemView.context)
            .load(category.imageUrl)
            .placeholder(R.drawable.vestuario)
            .into(holder.icon)

        holder.name.text = category.name

        holder.itemView.setOnClickListener {
            onItemClicked(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    fun loadCategories(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val listRef = FirebaseStorage.getInstance().reference.child("imagenesCategorias")
        val tempCategories = mutableListOf<Category>()

        listRef.listAll().addOnSuccessListener { listResult ->
            val totalItems = listResult.items.size
            var loadedItems = 0

            listResult.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    var fileName = item.name.substringBeforeLast(".png")
                    fileName = fileName.replace(Regex("(?<=.)([A-Z])"), " $1")
                    val downloadUrl = uri.toString()
                    tempCategories.add(Category(downloadUrl, fileName))

                    loadedItems++
                    if (loadedItems == totalItems) {
                        categories = tempCategories.sortedBy { it.name }
                        notifyDataSetChanged()
                        onSuccess()
                    }
                }.addOnFailureListener { e ->
                    onFailure(e)
                }
            }
        }.addOnFailureListener { e ->
            onFailure(e)
        }
    }
}