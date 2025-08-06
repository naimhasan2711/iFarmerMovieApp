package com.nakibul.ifarmermovieapp.domain.repository

import com.nakibul.ifarmermovieapp.data.datasource.MovieDataSource
import com.nakibul.ifarmermovieapp.data.local.GenreDao
import com.nakibul.ifarmermovieapp.data.local.MovieDao
import com.nakibul.ifarmermovieapp.data.local.toDomain
import com.nakibul.ifarmermovieapp.data.local.toEntity
import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse

const val TAG = "MovieRepositoryImpl"

class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) : MovieRepository {

    // this function is used to fetch the movie response from the remote data source and then cache it in the local database
    override suspend fun fetMovieResponse(): MovieResponse {
        return try {
            val remote = movieDataSource.getMovieResponse()
            movieDao.clearMovies()
            movieDao.insertMovies(remote.movies.map { it.toEntity() })
            remote.genres.forEach {
                genreDao.insertGenre(
                    Genre(
                        name = it
                    )
                )
            }
            remote
        } catch (e: Exception) {
            val cached = movieDao.getAllMovies().map { it.toDomain() }
            MovieResponse(
                genres = cached.flatMap { it.genres }.distinct(),
                movies = cached
            )
        }
    }

    // this function is used to search the movies list from the local database
    override suspend fun searchMovies(query: String): List<Movie> {
        return movieDao.searchMovies(query).map { it.toDomain() }
    }

    // this function is used to get all genres from the local database
    override suspend fun getAllGenres(): List<Genre> {
        return genreDao.getAllGenres()
    }

    // this function is used to get the movies list from the local database with pagination
    override suspend fun getMoviesPaged(limit: Int, offset: Int): List<Movie> {
        return movieDao.getMoviesPaged(limit, offset).map { it.toDomain() }
    }

    // this function is used to get the movie by id from the local database
    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toDomain()
    }

    // Toggle the favorite status of a movie
    override suspend fun toggleFavoriteStatus(movieId: Int) {
        movieDao.toggleFavorite(movieId)
    }

    // Get all favorite movies
    override suspend fun getFavoriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies().map { it.toDomain() }
    }

    // Set the favorite status of a movie
    override suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        movieDao.setFavoriteStatus(movieId, isFavorite)
    }
}
