package com.example.moviesbasic.data.images


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Backdrop(
    @SerialName("file_path")
    val filePath: String?,
)