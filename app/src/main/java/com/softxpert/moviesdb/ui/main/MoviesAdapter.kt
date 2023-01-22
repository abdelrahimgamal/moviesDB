package com.softxpert.moviesdb.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softxpert.moviesdb.databinding.ItemMovieBinding
import com.softxpert.moviesdb.ui.model.MovieUI

class MoviesAdapter(val onMovieClickListener: MovieClickListener) :
    PagingDataAdapter<MovieUI, MoviesAdapter.MovieViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieUI) {
            binding.movie = item
            binding.onClickListener = onMovieClickListener
            binding.executePendingBindings()
        }
    }

    class MovieClickListener(val clickListener: (ui: MovieUI) -> Unit) {
        fun onClick(uiMovieUI: MovieUI) {
            clickListener(uiMovieUI)
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem == newItem
    }
}
