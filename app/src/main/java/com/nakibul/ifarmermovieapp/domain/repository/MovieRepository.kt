package com.nakibul.ifarmermovieapp.domain.repository

import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

interface MovieRepository {
    suspend fun fetMovieResponse(): MovieResponse
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getAllGenres(): List<Genre>
    suspend fun getMoviesPaged(limit: Int, offset: Int): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie?
}
