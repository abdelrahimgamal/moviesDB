package com.softxpert.moviesdb.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.softxpert.moviesdb.data.api.ApiParameters.PAGE_SIZE
import com.softxpert.moviesdb.data.api.MoviesApi
import com.softxpert.moviesdb.data.api.model.mapToDomain
import com.softxpert.moviesdb.data.cache.Cache
import com.softxpert.moviesdb.data.cache.model.CachedMovie
import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.ui.model.FilterOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val category: Category,
    private val cache: Cache,
    private val moviesApi: MoviesApi,
    private val genre: String?,
    private val filterOptions: FilterOptions
) : RemoteMediator<Int, CachedMovie>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedMovie>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    withContext(Dispatchers.IO) {
                        val count = category.getItemsCount(cache)
                        return@withContext (count / PAGE_SIZE) + 1
                    }
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val response = category.apiCall(moviesApi, loadKey.toLong(),genre,filterOptions)
            if (loadType == LoadType.REFRESH) {
                cache.deleteAll()
            }

            // Insert new movies into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            response.movies.forEach {
                val itemFromDB = cache.getMovieById(it.id!!)
                if (itemFromDB == null) {
                    val updated =
                        category.setCacheCategoryValue(CachedMovie.fromDomain(it.mapToDomain()))
                    cache.storeMovies(updated)
                } else {
                    val updated =
                        category.setCacheCategoryValue(itemFromDB)
                    updated.modifiedAt = System.currentTimeMillis()
                    cache.storeMovies(updated)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = response.currentPage == response.totalPages
            )
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}
