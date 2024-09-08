package com.puj.proyectoensenarte.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ItemLayoutBinding


class MyAdapter(context: Context?, private val mData: List<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater


    init {
        mInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemLayoutBinding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]

        // Establecer el texto en el TextView existente
        holder.binding.textView.text = item

        // Establecer la imagen del nuevo ImageView
        holder.binding.goIcon.setImageResource(R.drawable.flecha)
    }


    override fun getItemCount(): Int {
        return mData.size
    }


    inner class ViewHolder internal constructor(binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItemLayoutBinding

        init {
            this.binding = binding
        }
    }
}

