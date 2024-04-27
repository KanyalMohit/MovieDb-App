package com.example.moviesbasic.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.detail.DetailDto

@Composable
fun PosterImage(
    baseUrl: String,
    detailData: DetailDto
) {
    Card(
        modifier = Modifier
            .height(200.dp),
        elevation = CardDefaults.cardElevation(50.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(
                baseUrl + detailData.posterPath
            ).size(Size.ORIGINAL).crossfade(true).build()
        )
        if (painter.state is AsyncImagePainter.State.Loading) {
            Text(text = "Loading")
        }
        Image(
            painter = painter,
            contentDescription = "Poster",
            modifier = Modifier
        )
    }
}