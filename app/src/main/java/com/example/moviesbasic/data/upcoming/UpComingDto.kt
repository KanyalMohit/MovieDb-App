package com.example.moviesbasic.data.upcoming


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpComingDto
    (
    @SerialName("dates")
    val dates: Dates?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Result?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)