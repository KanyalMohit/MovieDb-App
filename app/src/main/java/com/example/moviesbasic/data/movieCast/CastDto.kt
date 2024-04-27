package com.example.moviesbasic.data.movieCast


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("cast")
    val cast: List<Cast?>?,
    @SerialName("crew")
    val crew: List<Crew?>?
)