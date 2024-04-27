package com.example.moviesbasic.screen.popular

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.popular.PopularMovieDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieRow(data: PopularMovieDto, onTrendClick: () -> Unit, onImageClick: (String) -> Unit) {
    val imageBaseUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    Box{
        Column (
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(12.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Trending Shows",
                    modifier = Modifier,
                    style = TextStyle(fontSize = 25.sp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = "next",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            onTrendClick()
                        }
                )
            }

            LazyRow(modifier = Modifier, contentPadding = PaddingValues(12.dp)){
                items(data.results?: listOf()){
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageBaseUrl + it?.posterPath).crossfade(true).size(Size.ORIGINAL)
                            .build(),
                        )
                    if(painter.state is AsyncImagePainter.State.Loading){
                        Text(text = "Loading")
                    }
                    OutlinedCard(onClick = {
                        onImageClick(it?.id.toString())
                    }, modifier = Modifier
                        .padding(4.dp)
                        ,
                        elevation = CardDefaults.outlinedCardElevation(22.dp),
                        border = BorderStroke(3.dp, Color.Black),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.Black)
                    ) {
                        Image(
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .height(200.dp)
                                .width(150.dp),
                            painter = painter,
                            contentDescription = "Poster Image"
                        )
                    }
                }
            }
        }
    }
}