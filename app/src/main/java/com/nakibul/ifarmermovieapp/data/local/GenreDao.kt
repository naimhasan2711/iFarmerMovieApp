package com.nakibul.ifarmermovieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakibul.ifarmermovieapp.domain.models.local.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<Genre>

    @Delete
    suspend fun deleteGenre(genre: Genre)
}