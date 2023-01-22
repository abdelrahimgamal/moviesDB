package com.softxpert.moviesdb.domain.usecases

import com.softxpert.moviesdb.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieById @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movieId: Long) = moviesRepository.getMovieById(movieId)
}
