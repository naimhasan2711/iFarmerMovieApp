package com.nakibul.ifarmermovieapp.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakibul.ifarmermovieapp.domain.models.MovieResponse
import com.nakibul.ifarmermovieapp.domain.use_case.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val response = moviesUseCase.fetchMovieList()
                _state.value = _state.value.copy(
                    moviesResponse = response,
                    isLoading = false,
                    errorMessage = ""
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
}

data class MoviesState(
    val isLoading: Boolean = false,
    val moviesResponse: MovieResponse? = null,
    val errorMessage: String = ""
)