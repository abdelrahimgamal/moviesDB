package com.softxpert.moviesdb.domain.model.category

import androidx.paging.PagingSource
import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.ApiPaginatedMovies
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.databinding.FragmentMoviesBinding
import com.softxpert.moviesdb.ui.model.FilterOptions

interface Category {
    fun setCacheCategoryValue(movie: CachedMovie): CachedMovie
    suspend fun apiCall(
        api: MoviesApi,
        page: Long,
        genre: String?,
        filterOptions: FilterOptions
    ): ApiPaginatedMovies

    fun getCache(cache: Cache): PagingSource<Int, CachedMovie>
    suspend fun getItemsCount(cache: Cache): Int
    fun setupProgress(binding: FragmentMoviesBinding, b: Boolean)
}
