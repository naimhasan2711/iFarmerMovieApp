package com.nakibul.ifarmermovieapp.domain.models.local

import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

data class MovieResponseDto(
    val genres: List<String>,
    val movies: List<MovieDto>
) {
    fun toDomain(): MovieResponse = MovieResponse(
        genres = genres,
        movies = movies.map { it.toDomain() }
    )
}
