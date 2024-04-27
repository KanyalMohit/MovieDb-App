package com.example.moviesbasic.screen.similar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.similar.Result
import com.example.moviesbasic.data.similar.SimilarDto
import com.example.moviesbasic.screen.recommendation.RecommendCard


@Composable
fun SimilarMovies(data: SimilarDto) {

    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(12.dp)) {
        items(data.results){
            val painter = ImagePainter(it = it)
            if(painter.state is AsyncImagePainter.State.Loading){
                Text(text = "Loading")
            }
            RecommendCard(painter)
        }
    }
}
@Composable
private fun ImagePainter(
    imageBaseUrl: String = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/",
    it: Result
) = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
        .data(imageBaseUrl + it?.posterPath).crossfade(true).size(Size.ORIGINAL)
        .build(),
)

