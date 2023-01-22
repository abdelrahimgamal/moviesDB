package com.softxpert.moviesdb.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softxpert.moviesdb.data.cache.daos.MoviesDao
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.domain.model.GenresModel

@Database(
    entities = [
        CachedMovie::class,
        GenresModel::class
    ],
    version = 4
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
