package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.data.local.MovieDao
import com.nakibul.ifarmermovieapp.data.local.toDomain
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao
) {
    suspend fun fetchMovieResponse() = movieRepository.fetMovieResponse()
    suspend fun searchMovies(query: String) = movieRepository.searchMovies(query)
    suspend fun getMoviesPaged(limit: Int, offset: Int) = movieRepository.getMoviesPaged(limit, offset)
    suspend fun getMovieById(movieId: Int) = movieRepository.getMovieById(movieId)
    suspend fun getCachedMovies(): List<Movie> = movieDao.getAllMovies().map { it.toDomain() }
}
