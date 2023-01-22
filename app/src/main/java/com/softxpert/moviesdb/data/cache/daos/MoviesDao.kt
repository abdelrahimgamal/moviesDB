package com.softxpert.moviesdb.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.domain.model.GenresModel

@Dao
abstract class MoviesDao {

    @Query("SELECT COUNT(movieId) FROM movies where isPopular = 1")
    abstract suspend fun getPopularMoviesCount(): Int

    @Query("SELECT * FROM movies")
    abstract fun popularPagingSource(): PagingSource<Int, CachedMovie>

    @Query("SELECT * FROM genres")
    abstract suspend fun getGenres(): List<GenresModel>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    abstract suspend fun getMovieById(id: Long): CachedMovie?

    @Query("SELECT * FROM genres WHERE id = :id")
    abstract suspend fun getGenreById(id: Long): GenresModel?

    @Query("SELECT createdAt FROM movies order by id ASC LIMIT 1")
    abstract suspend fun getCreatedAt(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(vararg movie: CachedMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGenre(vararg genre: GenresModel)

    @Query("DELETE FROM movies ")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM genres ")
    abstract suspend fun deleteAllGenres()
}
