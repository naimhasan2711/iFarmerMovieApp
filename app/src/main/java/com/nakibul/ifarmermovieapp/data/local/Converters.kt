package com.nakibul.ifarmermovieapp.data.local

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromGenreList(genres: List<String>): String {
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenreList(genres: String): List<String> {
        return genres.split(",").map { it.trim() }
    }
}