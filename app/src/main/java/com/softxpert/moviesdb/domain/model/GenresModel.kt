package com.softxpert.moviesdb.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "genres")
class GenresModel(
    @PrimaryKey
    val id: Long,
    val name: String,
    var selected: Boolean = false
)
