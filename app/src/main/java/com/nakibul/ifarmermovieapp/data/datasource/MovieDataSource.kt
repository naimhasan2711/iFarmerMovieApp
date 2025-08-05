package com.nakibul.ifarmermovieapp.data.datasource

import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

interface MovieDataSource {
    suspend fun getMovieResponse(): MovieResponse
}