package com.astar.astarradio.presentation.radiolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.R
import com.astar.astarradio.databinding.RadioItemBinding
import com.astar.astarradio.presentation.RadioUi
import com.bumptech.glide.Glide

interface RadioActionCallback {
    fun onClickItem(station: RadioUi)
    fun onClickFavorite(station: RadioUi)
}

class RadioAdapter(private val callback: RadioActionCallback) : RecyclerView.Adapter<RadioAdapter.RadioViewHolder>() {

    var radios: List<RadioUi> = emptyList()
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

        holder.binding.favorite.setImageResource(
            if (radioStation.isFavorite)
                R.drawable.ic_baseline_favorite
            else
                R.drawable.ic_baseline_favorite_border
        )

        Glide.with(context).load(radioStation.previewUrl).into(holder.binding.icon)

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
}