package com.puj.proyectoensenarte.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.R


class PalabraAdapter(private val onItemClicked: (Palabra) -> Unit) :
    ListAdapter<Palabra, PalabraAdapter.PalabraViewHolder>(PalabraDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalabraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_palabra, parent, false)
        return PalabraViewHolder(view)
    }

    override fun onBindViewHolder(holder: PalabraViewHolder, position: Int) {
        val palabra = getItem(position)
        holder.bind(palabra)
        holder.itemView.setOnClickListener { onItemClicked(palabra) }
    }

    class PalabraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPalabra: TextView = itemView.findViewById(R.id.tvPalabra)

        fun bind(palabra: Palabra) {
            tvPalabra.text = palabra.texto
        }
    }

    private class PalabraDiffCallback : DiffUtil.ItemCallback<Palabra>() {
        override fun areItemsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem == newItem
        }
    }
}