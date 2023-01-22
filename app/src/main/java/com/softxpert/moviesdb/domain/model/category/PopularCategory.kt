package com.softxpert.moviesdb.domain.model.category

import androidx.core.view.isVisible
import androidx.paging.PagingSource
import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.ApiPaginatedMovies
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.databinding.FragmentMoviesBinding
import com.softxpert.moviesdb.ui.model.FilterOptions

class PopularCategory : Category {
    override fun setCacheCategoryValue(movie: CachedMovie): CachedMovie {
        return movie.copy(isPopular = true)
    }

    override suspend fun apiCall(
        api: MoviesApi,
        page: Long,
        genre: String?,
        filterOptions: FilterOptions
    ): ApiPaginatedMovies {
        return when(filterOptions)
        {
         FilterOptions.GENRE->   api.getPopularMovies(page, genre)
         FilterOptions.SEARCH->   api.getSearchMovies(page, genre)
        }
    }

    override fun getCache(cache: Cache): PagingSource<Int, CachedMovie> {
        return cache.getPopularPagingSource()
    }

    override suspend fun getItemsCount(cache: Cache): Int {
        return cache.getPopularMoviesCount()
    }

    override fun setupProgress(binding: FragmentMoviesBinding, b: Boolean) {
        binding.progressBar2.isVisible = b
    }
}
