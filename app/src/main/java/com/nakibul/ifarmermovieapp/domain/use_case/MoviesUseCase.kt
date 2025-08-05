package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun fetchMovieList() = movieRepository.fetMovieResponse()
    suspend fun searchMovies(query: String) = movieRepository.searchMovies(query)
    suspend fun getAllGenres() = movieRepository.getAllGenres()
}

