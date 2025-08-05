package com.nakibul.ifarmermovieapp.domain.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nakibul.ifarmermovieapp.data.local.Converters

@Entity(tableName = "movies")
@TypeConverters(Converters::class)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val plot: String,
    val posterUrl: String,
    val runtime: String,
    val year: String,
    val director: String,
    val actors: String,
    @TypeConverters(Converters::class)
    val genres: List<String>
)