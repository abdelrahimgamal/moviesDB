package com.softxpert.moviesdb.data.cache

import androidx.paging.PagingSource
import com.softxpert.moviesdb.data.cache.daos.MoviesDao
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.domain.model.GenresModel
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val moviesDao: MoviesDao,
) : Cache {
    override fun getPopularPagingSource(): PagingSource<Int, CachedMovie> {
        return moviesDao.popularPagingSource()
    }

    override suspend fun getGenreSource(): List<GenresModel> {
        return moviesDao.getGenres()

    }


    override suspend fun getPopularMoviesCount(): Int {
        return moviesDao.getPopularMoviesCount()
    }

    override suspend fun getGenresCount(): Int {

        return 0
    }


    override suspend fun getMovieById(id: Long): CachedMovie? {
        return moviesDao.getMovieById(id)
    }

    override suspend fun getGenreByID(id: Long): GenresModel? {
        return moviesDao.getGenreById(id)
    }

    override suspend fun getCreatedAt(): Long? {
        return moviesDao.getCreatedAt()
    }

    override suspend fun storeMovies(vararg movie: CachedMovie) {
        moviesDao.insertMovie(*movie)
    }

    override suspend fun storeGenres(vararg genresModel: GenresModel) {
        moviesDao.insertGenre(*genresModel)
    }

    override suspend fun deleteAll() {
        moviesDao.deleteAll()
    }

    override suspend fun deleteAllGenres() {
        moviesDao.deleteAllGenres()
    }
}
