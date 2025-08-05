package com.nakibul.ifarmermovieapp.domain.models.remote

data class MovieResponse(
    val genres: List<String>,
    val movies: List<Movie>
)