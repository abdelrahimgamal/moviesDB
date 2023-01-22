package com.softxpert.moviesdb.data

import androidx.paging.*
import com.softxpert.moviesdb.data.api.ApiParameters.PAGE_SIZE
import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.mapToDomain
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.data.cache.model.toDomain
import com.softxpert.moviesdb.data.paging.MoviesRemoteMediator
import com.softxpert.moviesdb.domain.model.GenresModel
import com.softxpert.moviesdb.domain.model.Movie
import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.domain.model.genre.GenreImp
import com.softxpert.moviesdb.domain.repository.MoviesRepository
import com.softxpert.moviesdb.ui.model.FilterOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val api: MoviesApi
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovies(category: Category,genre:String?,filterOptions: FilterOptions): Flow<PagingData<Movie>> {
        val pager = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MoviesRemoteMediator(
                category, cache, api,genre,filterOptions
            )
        ) {
            category.getCache(cache)
        }
        return pager.flow.map { data -> data.map { it.toDomain() } }
    }

    override suspend fun getGenres(): Flow<List<GenresModel>> {
        loadGenres()
        return flowOf(cache.getGenreSource())
    }

    override suspend fun getMovieById(id: Long): Movie {
        return cache.getMovieById(id)!!.toDomain()
    }

    private suspend fun loadGenres() {
        try {
            val response = GenreImp().apiCall(api)
            response.genres.forEach {
                cache.storeGenres(it.mapToDomain())
            }
        } catch (e: IOException) {
            e.printStackTrace()

        } catch (e: HttpException) {
            e.printStackTrace()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
