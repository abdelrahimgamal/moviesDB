package com.softxpert.moviesdb.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
    const val POPULAR_ENDPOINT = "discover/movie"
    const val SEARCH_ENDPOINT = "search/movie"
    const val GENRES_ENDPOINT = "genre/movie/list"
    const val IMAGES_BASE = "https://www.themoviedb.org/t/p/w220_and_h330_face"
    const val COVERS_BASE = "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces"
}

object ApiParameters {
    const val API_KEY = "api_key"
    const val WITH_GENRES = "with_genres"
    const val SEARCH_QUERY = "query"
    // MOVIE
    const val PAGE = "page"
    const val PAGE_SIZE = 20
}
