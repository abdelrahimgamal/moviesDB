package com.softxpert.moviesdb.data.api.model


import com.softxpert.moviesdb.domain.model.GenresModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGenres(
    @field:Json(name = "genres") val genres: List<ApiGenresModel>,
)


@JsonClass(generateAdapter = true)
data class ApiGenresModel(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
)

fun ApiGenresModel.mapToDomain(): GenresModel {
    return GenresModel(
        id,
        name = name
    )
}
