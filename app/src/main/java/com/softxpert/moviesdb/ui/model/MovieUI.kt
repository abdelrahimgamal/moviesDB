package com.softxpert.moviesdb.ui.model

import com.softxpert.moviesdb.domain.model.Movie

data class MovieUI(
    val id: Long,
    val title: String,
    val image: String,
    val releaseDate: String,
    val averageRate: String
) {
    companion object {
        fun fromDomain(movie: Movie): MovieUI {
            return MovieUI(
                movie.id,
                movie.title,
                movie.posterPath,
                movie.releaseDate,
                movie.voteAverage.toString()
            )
        }
    }
}
