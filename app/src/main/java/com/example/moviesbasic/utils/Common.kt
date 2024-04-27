package com.example.moviesbasic.utils

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.tvSeries.Result

@Composable
fun AppButton(
    text : String,
    onClick : ()->Unit
) {
    OutlinedButton(onClick = { onClick() }) {
        Text(text = text)
    }
}

@Composable
fun asyncImagePainter(
    imageBaseUrl: String,
    it: Result?
) = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
        .data(imageBaseUrl + it?.posterPath).crossfade(true).size(Size.ORIGINAL)
        .build(),
)
