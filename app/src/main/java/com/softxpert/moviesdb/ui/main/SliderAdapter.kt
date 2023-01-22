package com.softxpert.moviesdb.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.softxpert.moviesdb.databinding.SliderItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val dataSet: ArrayList<String>) :
    SliderViewAdapter<SliderAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }



    class ViewHolder(val binding: SliderItemBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.url = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SliderItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder.from(parent)
    }
}