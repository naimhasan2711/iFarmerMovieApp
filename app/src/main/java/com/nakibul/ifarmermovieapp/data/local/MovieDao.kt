package com.nakibul.ifarmermovieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakibul.ifarmermovieapp.domain.models.local.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY year DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

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

    @Query("SELECT * FROM movies ORDER BY year DESC LIMIT :limit OFFSET :offset")
    suspend fun getMoviesPaged(limit: Int, offset: Int): List<MovieEntity>
}
