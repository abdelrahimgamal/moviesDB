package com.softxpert.moviesdb.domain.usecases

import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.domain.repository.MoviesRepository
import com.softxpert.moviesdb.ui.model.FilterOptions
import javax.inject.Inject

class GetMovies @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(category: Category,genre:String?,filterOptions: FilterOptions) =
        moviesRepository.getMovies(category,genre,filterOptions)
}
