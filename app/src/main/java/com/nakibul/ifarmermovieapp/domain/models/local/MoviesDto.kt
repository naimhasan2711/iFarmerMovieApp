package com.nakibul.ifarmermovieapp.domain.models.local

import com.nakibul.ifarmermovieapp.domain.models.remote.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    val year: String,
    val runtime: String,
    val genres: List<String>,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String
) {
    fun toDomain(): Movie = Movie(
        id = id,
        title = title,
        year = year,
        runtime = runtime,
        genres = genres,
        director = director,
        actors = actors,
        plot = plot,
        posterUrl = posterUrl
    )
}
