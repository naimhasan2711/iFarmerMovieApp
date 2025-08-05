package com.nakibul.ifarmermovieapp.data.remote

import com.nakibul.ifarmermovieapp.domain.models.MovieResponse
import retrofit2.http.GET
import retrofit2.Response

interface MovieApiService {
    @GET("db.json")
    suspend fun getMovies(): Response<MovieResponse>
}