package com.softxpert.moviesdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.softxpert.moviesdb.domain.model.GenresModel
import com.softxpert.moviesdb.domain.model.category.Category
import com.softxpert.moviesdb.domain.model.category.PopularCategory
import com.softxpert.moviesdb.domain.usecases.GetGenres
import com.softxpert.moviesdb.domain.usecases.GetMovies
import com.softxpert.moviesdb.ui.model.FilterOptions
import com.softxpert.moviesdb.ui.model.MovieUI
import com.softxpert.moviesdb.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val getGenres: GetGenres,
) : ViewModel() {

    private val _popularState = MutableStateFlow<PagingData<MovieUI>?>(null)
    val popular: StateFlow<PagingData<MovieUI>?> =
        _popularState.asStateFlow()

    private val _genres = MutableStateFlow<List<GenresModel>?>(null)
    val genres: StateFlow<List<GenresModel>?> =
        _genres.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeToMoviesUpdates(PopularCategory(), null, null)
            subscribeToGenresUpdates()
        }
    }

    private fun subscribeToGenresUpdates() {
        viewModelScope.launch {
            getGenres()
                .collect {
                    _genres.emit(it)
                }
        }
    }

    fun subscribeToMoviesUpdates(
        category: Category,
        genre: String?,
        filterOptions: FilterOptions?
    ) {
        viewModelScope.launch {

            val filtered = filterOptions ?: FilterOptions.GENRE
            getMovies(category = category, genre, filtered).cachedIn(viewModelScope)
                .map { movies ->
                    movies.map {
                        MovieUI.fromDomain(it)
                    }
                }
                .collect {
                    onNewMoviesList(category, it)
                }
        }
    }

    private suspend fun onNewMoviesList(category: Category, newMoviesList: PagingData<MovieUI>) {
        Logger.d("Got more Movies $category")
        when (category) {
            is PopularCategory -> {
                _popularState.emit(newMoviesList)
            }
        }
    }
}
