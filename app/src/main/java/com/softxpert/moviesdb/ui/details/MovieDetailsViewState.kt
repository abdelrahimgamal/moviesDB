package com.softxpert.moviesdb.ui.details

import com.softxpert.moviesdb.ui.model.MovieDetailsUI

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movie: MovieDetailsUI? = null,
)
