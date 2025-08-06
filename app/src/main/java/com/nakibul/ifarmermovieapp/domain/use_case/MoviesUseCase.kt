package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun fetchMovieList() = movieRepository.fetMovieResponse()
    suspend fun searchMovies(query: String) = movieRepository.searchMovies(query)
    suspend fun getAllGenres() = movieRepository.getAllGenres()
    suspend fun getMoviesPaged(limit: Int, offset: Int) = movieRepository.getMoviesPaged(limit, offset)
    suspend fun getMovieById(movieId: Int) = movieRepository.getMovieById(movieId)
    suspend fun toggleFavorite(movieId: Int) = movieRepository.toggleFavoriteStatus(movieId)
    suspend fun getFavoriteMovies() = movieRepository.getFavoriteMovies()
    suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean) = 
        movieRepository.setFavoriteStatus(movieId, isFavorite)
}
