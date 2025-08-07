package com.nakibul.ifarmermovieapp.data.local

import androidx.room.TypeConverter

/*
    * This file contains the Converters class which provides methods to convert
    * a list of genres to a string and vice versa.
 */

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