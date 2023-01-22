package com.softxpert.moviesdb.domain.repository

import androidx.paging.PagingData
import com.softxpert.moviesdb.domain.model.GenresModel
import com.softxpert.moviesdb.domain.model.Movie
import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.ui.model.FilterOptions
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(
        category: Category,
        genre: String?,
        filterOptions: FilterOptions
    ): Flow<PagingData<Movie>>

    suspend fun getGenres(): Flow<List<GenresModel>>

    suspend fun getMovieById(id: Long): Movie
}
