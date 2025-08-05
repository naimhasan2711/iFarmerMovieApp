package com.nakibul.ifarmermovieapp.domain.repository

import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

interface MovieRepository {
    suspend fun fetMovieResponse(): MovieResponse
}
