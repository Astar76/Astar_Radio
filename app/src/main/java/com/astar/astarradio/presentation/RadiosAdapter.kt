package com.astar.astarradio.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.astarradio.databinding.RadioItemBinding
import com.astar.astarradio.domain.RadioStation

class RadiosAdapter : RecyclerView.Adapter<RadiosAdapter.RadioViewHolder>() {

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
        holder.binding.name.text = radioStation.name
    }

    override fun getItemCount(): Int = radios.size

    inner class RadioViewHolder(
        val binding: RadioItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}