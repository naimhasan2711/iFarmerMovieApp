package com.nakibul.ifarmermovieapp.data.local

import com.nakibul.ifarmermovieapp.domain.models.local.MovieEntity
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie

/*
    * This file contains extension functions to convert between Movie and MovieEntity.
 */

fun Movie.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    plot = plot,
    posterUrl = posterUrl,
    runtime = runtime,
    year = year,
    director = director,
    actors = actors,
    genres = genres,
    isFavorite = isFavorite
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    plot = plot,
    posterUrl = posterUrl,
    runtime = runtime,
    year = year,
    director = director,
    actors = actors,
    genres = genres,
    isFavorite = isFavorite
)
