package com.softxpert.moviesdb.data.cache

import androidx.paging.PagingSource
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.domain.model.GenresModel

interface Cache {

    fun getPopularPagingSource(): PagingSource<Int, CachedMovie>
    suspend fun getGenreSource(): List<GenresModel>

    suspend fun getPopularMoviesCount(): Int
    suspend fun getGenresCount(): Int

    suspend fun getMovieById(id: Long): CachedMovie?
    suspend fun getGenreByID(id: Long): GenresModel?

    suspend fun getCreatedAt(): Long?

    suspend fun storeMovies(vararg movie: CachedMovie)
    suspend fun storeGenres(vararg genresModel: GenresModel)
    suspend fun deleteAll()
    suspend fun deleteAllGenres()
}
