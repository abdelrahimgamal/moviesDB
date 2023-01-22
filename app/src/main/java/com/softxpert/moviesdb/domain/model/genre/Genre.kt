package com.softxpert.moviesdb.domain.model.genre

import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.ApiGenres
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.databinding.FragmentMoviesBinding
import com.softxpert.moviesdb.domain.model.GenresModel
import retrofit2.Response

interface Genre {
    suspend fun apiCall(api: MoviesApi): ApiGenres
    suspend fun getCache(cache: Cache): List<GenresModel>
    suspend fun getItemsCount(cache: Cache): Int
    fun setupProgress(binding: FragmentMoviesBinding, b: Boolean)
}
