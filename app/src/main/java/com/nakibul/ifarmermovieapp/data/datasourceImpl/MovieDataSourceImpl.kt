package com.nakibul.ifarmermovieapp.data.datasourceImpl

import com.nakibul.ifarmermovieapp.data.datasource.MovieDataSource
import com.nakibul.ifarmermovieapp.data.remote.MovieApiService
import com.nakibul.ifarmermovieapp.domain.models.MovieResponse

class MovieDataSourceImpl(private var apiService: MovieApiService) : MovieDataSource {
    override suspend fun getMovieResponse(): MovieResponse {
        return apiService.getMovieResponse()
    }
}
