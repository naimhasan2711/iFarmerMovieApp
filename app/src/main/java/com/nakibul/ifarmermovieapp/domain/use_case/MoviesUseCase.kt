package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.data.local.GenreDao
import com.nakibul.ifarmermovieapp.data.local.MovieDao
import com.nakibul.ifarmermovieapp.data.local.toDomain
import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) {
    suspend fun fetchMovieList() = movieRepository.fetMovieResponse()
    suspend fun searchMovies(query: String) = movieRepository.searchMovies(query)
    suspend fun getAllGenres() = movieRepository.getAllGenres()
    suspend fun getMoviesPaged(limit: Int, offset: Int) = movieRepository.getMoviesPaged(limit, offset)
    suspend fun getMovieById(movieId: Int) = movieRepository.getMovieById(movieId)
    suspend fun toggleFavorite(movieId: Int) = movieRepository.toggleFavoriteStatus(movieId)
    suspend fun getFavoriteMovies() = movieRepository.getFavoriteMovies()
    suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean) = movieRepository.setFavoriteStatus(movieId, isFavorite)
    suspend fun getCachedMovies(): List<Movie> = movieDao.getAllMovies().map { it.toDomain() }
    suspend fun getCachedGenres(): List<Genre> = genreDao.getAllGenres()
}
