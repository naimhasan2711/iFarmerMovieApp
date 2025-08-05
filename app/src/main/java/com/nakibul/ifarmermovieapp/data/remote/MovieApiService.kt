package com.nakibul.ifarmermovieapp.data.remote

import com.nakibul.ifarmermovieapp.domain.models.MovieResponse
import retrofit2.http.GET

interface MovieApiService {
    @GET("db.json")
    suspend fun getMovieResponse(): MovieResponse
}