package com.nakibul.ifarmermovieapp.domain.repository

import com.nakibul.ifarmermovieapp.data.datasource.MovieDataSource
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun fetMovieResponse(): MovieResponse {
        return movieDataSource.getMovieResponse()
    }
}
