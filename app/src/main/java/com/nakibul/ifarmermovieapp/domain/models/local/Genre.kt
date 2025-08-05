package com.nakibul.ifarmermovieapp.domain.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val name: String
)
