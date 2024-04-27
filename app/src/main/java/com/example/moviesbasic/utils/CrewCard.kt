package com.example.moviesbasic.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.movieCast.Cast

@Composable
fun CrewCard(baseUrl: String, cast: Cast?) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        border = BorderStroke(0.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row {
            Card(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(baseUrl + cast?.profilePath)
                        .size(Size.ORIGINAL)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    modifier = Modifier,
                    painter = painter,
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                )
            }
            Column(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = "${cast?.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = "${cast?.character}", color = Color.White)
            }
        }

    }
}