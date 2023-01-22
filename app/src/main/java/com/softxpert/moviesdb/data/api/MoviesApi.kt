package com.softxpert.moviesdb.data.api

import com.softxpert.moviesdb.BuildConfig
import com.softxpert.moviesdb.data.api.model.ApiGenres
import com.softxpert.moviesdb.data.api.model.ApiPaginatedMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET(ApiConstants.POPULAR_ENDPOINT)
    suspend fun getPopularMovies(
        @Query(ApiParameters.PAGE) pageToLoad: Long,
        @Query(ApiParameters.WITH_GENRES) genre: String?,
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiPaginatedMovies

    @GET(ApiConstants.SEARCH_ENDPOINT)
    suspend fun getSearchMovies(
        @Query(ApiParameters.PAGE) pageToLoad: Long,
        @Query(ApiParameters.SEARCH_QUERY) search: String?,
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiPaginatedMovies

    @GET(ApiConstants.GENRES_ENDPOINT)
    suspend fun getGenres(
        @Query(ApiParameters.API_KEY) apiKey: String = BuildConfig.API_KEY
    ): ApiGenres


}
