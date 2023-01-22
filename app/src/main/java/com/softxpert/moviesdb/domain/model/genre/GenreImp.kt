package com.softxpert.moviesdb.domain.model.genre


import androidx.core.view.isVisible
import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.ApiGenres
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.databinding.FragmentMoviesBinding
import com.softxpert.moviesdb.domain.model.GenresModel

class GenreImp : Genre {


    override suspend fun apiCall(api: MoviesApi): ApiGenres {
        return api.getGenres()
    }

    override suspend fun getCache(cache: Cache): List<GenresModel> {
        return cache.getGenreSource()
    }

    override suspend fun getItemsCount(cache: Cache): Int {
        return cache.getGenresCount()
    }

    override fun setupProgress(binding: FragmentMoviesBinding, b: Boolean) {
        binding.progressBar1.isVisible = b
    }
}
