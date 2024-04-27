package com.example.moviesbasic.data.images


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerialName("id")
    val id: Int?,
)