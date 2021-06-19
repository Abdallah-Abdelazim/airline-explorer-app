package com.example.airline_explorer.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airline(
    val id: String?,
    val name: String?,
    val logo: String?,
    val slogan: String?,
    val established: String?,
    val country: String?,
    val website: String?,
    @Json(name = "head_quaters") val headQuarters: String?
)
