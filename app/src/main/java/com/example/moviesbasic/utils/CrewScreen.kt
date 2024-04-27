package com.example.moviesbasic.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.moviesbasic.data.movieCast.CastDto

@Composable
fun CrewScreen(castData: CastDto) {
    val baseUrl: String ="https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    Box {
        Column {
            LazyColumn {
                items(castData.cast?: listOf()){cast ->
                    CrewCard(baseUrl, cast)
                }
            }
        }
    }
}