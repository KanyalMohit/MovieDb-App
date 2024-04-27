package com.example.moviesbasic.data.similar


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)