package com.nakibul.ifarmermovieapp.domain.repository

import android.util.Log
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
    override suspend fun fetMovieResponse(): MovieResponse {
        return try {
            Log.d(TAG, "fetMovieResponse: remote")
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
            Log.d(TAG, "fetMovieResponse: local")
            val cached = movieDao.getAllMovies().map { it.toDomain() }
            MovieResponse(
                genres = cached.flatMap { it.genres }.distinct(),
                movies = cached
            )
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return movieDao.searchMovies(query).map { it.toDomain() }
    }

    override suspend fun getAllGenres(): List<Genre> {
        return genreDao.getAllGenres()
    }

    override suspend fun getMoviesPaged(limit: Int, offset: Int): List<Movie> {
        return movieDao.getMoviesPaged(limit, offset).map { it.toDomain() }
    }
}
