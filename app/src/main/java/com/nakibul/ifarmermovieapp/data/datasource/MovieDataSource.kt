package com.nakibul.ifarmermovieapp.data.datasource

import com.nakibul.ifarmermovieapp.domain.models.MovieResponse

interface MovieDataSource {
    suspend fun getMovieResponse(): MovieResponse
}