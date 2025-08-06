package com.nakibul.ifarmermovieapp.presentation.viewmodel

import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

// This code defines a ViewModel for managing movie-related data in an Android application.
data class MoviesState(
    val isLoading: Boolean = false,
    val moviesResponse: MovieResponse? = null,
    val errorMessage: String = "",
    val movieList: List<Movie> = emptyList(),
    val genreList: List<String> = emptyList(),
    val genreList2: List<Genre> = emptyList()
)
