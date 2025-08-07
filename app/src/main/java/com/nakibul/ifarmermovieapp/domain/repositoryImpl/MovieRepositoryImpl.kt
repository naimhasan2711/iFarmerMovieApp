package com.nakibul.ifarmermovieapp.domain.repositoryImpl

import com.nakibul.ifarmermovieapp.data.datasource.MovieDataSource
import com.nakibul.ifarmermovieapp.data.local.GenreDao
import com.nakibul.ifarmermovieapp.data.local.MovieDao
import com.nakibul.ifarmermovieapp.data.local.toDomain
import com.nakibul.ifarmermovieapp.data.local.toEntity
import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse
import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository

const val TAG = "MovieRepositoryImpl"

class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao
) : MovieRepository {

    /*
        * This function fetches the movie response from the remote data source.
        * If the fetch is successful, it clears the existing movies in the local database,
        * inserts the new movies, and updates the genres.
     */
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

    /*
        * This function searches for movies based on the provided query.
        * It retrieves the movies from the local database that match the query
        * and maps them to the domain model.
     */
    override suspend fun searchMovies(query: String): List<Movie> {
        return movieDao.searchMovies(query).map { it.toDomain() }
    }

    /*
        * This function retrieves all genres from the local database.
        * It returns a list of Genre objects.
     */
    override suspend fun getAllGenres(): List<Genre> {
        return genreDao.getAllGenres()
    }

    /*
        * This function retrieves paginated movies from the local database.
        * It takes a limit and offset as parameters to control pagination.
        * It returns a list of Movie objects.
     */
    override suspend fun getMoviesPaged(limit: Int, offset: Int): List<Movie> {
        return movieDao.getMoviesPaged(limit, offset).map { it.toDomain() }
    }

    /*
        * This function retrieves a movie by its ID from the local database.
        * It returns a Movie object if found, or null if not found.
     */
    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toDomain()
    }

    /*
         * This function toggles the favorite status of a movie by its ID.
         * It updates the isFavorite field in the local database.
      */
    override suspend fun toggleFavoriteStatus(movieId: Int) {
        movieDao.toggleFavorite(movieId)
    }

    /*
        * This function retrieves all favorite movies from the local database.
        * It returns a list of Movie objects that are marked as favorite.
     */
    override suspend fun getFavoriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies().map { it.toDomain() }
    }

    /*
        * This function sets the favorite status of a movie by its ID.
        * It updates the isFavorite field in the local database to the specified value.
     */
    override suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        movieDao.setFavoriteStatus(movieId, isFavorite)
    }
}
