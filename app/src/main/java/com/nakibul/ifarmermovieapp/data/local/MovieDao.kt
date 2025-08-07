package com.nakibul.ifarmermovieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakibul.ifarmermovieapp.domain.models.local.MovieEntity


@Dao
interface MovieDao {
    /*
    Insert a list of movies into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    /*
    Get all movies from the database ordered by year in descending order.
     */
    @Query("SELECT * FROM movies ORDER BY year DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    /*
    Clear all movies from the database.
     */
    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    /*
    Search for movies based on a query string.
     */
    @Query(
        """
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%' 
        OR plot LIKE '%' || :query || '%' 
        OR actors LIKE '%' || :query || '%' 
        OR director LIKE '%' || :query || '%'
        """
    )
    suspend fun searchMovies(query: String): List<MovieEntity>

    /*
    Get a paginated list of movies, ordered by year in descending order.
     */
    @Query("SELECT * FROM movies ORDER BY year DESC LIMIT :limit OFFSET :offset")
    suspend fun getMoviesPaged(limit: Int, offset: Int): List<MovieEntity>

    /*
    Get a movie by its ID.
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    /*
    Toggle the favorite status of a movie by its ID.
     */
    @Query("UPDATE movies SET isFavorite = NOT isFavorite WHERE id = :movieId")
    suspend fun toggleFavorite(movieId: Int)

    /*
    Get all favorite movies, ordered by year in descending order.
     */
    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY year DESC")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    /*
    Set the favorite status of a movie by its ID.
     */
    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun setFavoriteStatus(movieId: Int, isFavorite: Boolean)
}
