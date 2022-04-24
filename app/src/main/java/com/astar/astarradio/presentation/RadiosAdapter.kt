package com.astar.astarradio.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.databinding.RadioItemBinding
import com.astar.astarradio.domain.RadioStation
import com.bumptech.glide.Glide

class RadiosAdapter(private val callback: Callback) : RecyclerView.Adapter<RadiosAdapter.RadioViewHolder>() {

    var radios: List<RadioStation> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RadioItemBinding.inflate(inflater, parent, false)
        return RadioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        val radioStation = radios[position]
        val context = holder.itemView.context

        holder.binding.name.text = radioStation.name
        holder.binding.name.isSelected = true

        Glide.with(context).load(radioStation.preview).into(holder.binding.icon)

        holder.itemView.setOnClickListener {
            callback.onClickItem(radioStation)
        }
        holder.binding.favorite.setOnClickListener {
            callback.onClickFavorite(radioStation)
        }
    }

    override fun getItemCount(): Int = radios.size

    inner class RadioViewHolder(
        val binding: RadioItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onClickItem(station: RadioStation)
        fun onClickFavorite(station: RadioStation)
    }
}