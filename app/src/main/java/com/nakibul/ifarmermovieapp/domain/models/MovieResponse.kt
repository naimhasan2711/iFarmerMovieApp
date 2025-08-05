package com.nakibul.ifarmermovieapp.domain.models

data class MovieResponse(
    val genres: List<String>,
    val movies: List<Movie>
)