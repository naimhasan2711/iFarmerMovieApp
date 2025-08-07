package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun toggleFavorite(movieId: Int) = movieRepository.toggleFavoriteStatus(movieId)
    suspend fun getFavoriteMovies() = movieRepository.getFavoriteMovies()
    suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean) = movieRepository.setFavoriteStatus(movieId, isFavorite)
}
