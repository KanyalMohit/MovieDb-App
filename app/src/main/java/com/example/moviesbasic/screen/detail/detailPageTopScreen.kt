package com.example.moviesbasic.screen.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.detail.DetailDto
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.utils.PosterImage

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun DetailTopScreen(
    imageData: ImageDto,
    baseUrl: String ="https://image.tmdb.org/t/p/w600_and_h900_bestv2/",
    detailData: DetailDto
) {
    val pagerState = rememberPagerState {
        imageData.backdrops?.size!!
    }
    Box(
        modifier = Modifier.height(350.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 4.dp,
            beyondBoundsPageCount = 3
        ) { page ->
            val backdrop = imageData.backdrops?.get(page)
            backdrop?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    elevation = CardDefaults.cardElevation(11.dp),
                    shape = RectangleShape
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(baseUrl + it.filePath)
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.FillBounds
                    )
                    if (painter.state is AsyncImagePainter.State.Loading) {
                        Text(text = "Loading")
                    }
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .consumeWindowInsets(WindowInsets.statusBars),
                        painter = painter,
                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        alpha = .875f
                    )
                }
            }
        }
        Row(
            modifier = Modifier.offset(y=120.dp)
        ) {
            PosterImage(baseUrl, detailData)
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${detailData.title}", fontSize = 30.sp, color = Color(
                        0xFFF7F7F7
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        modifier = Modifier,
                        border = BorderStroke(4.dp, Color.Black),
                        onClick = { },
                        label = {
                            Text(
                                text = if (detailData.adult == true) "Pg-18" else "Pg-13",
                                color = Color.White
                            )
                        })
                    Text(text = "${detailData.releaseDate}", color = Color.White)
                    Text(text = "${detailData.runtime} Mins", color = Color.White)
                }
            }
        }
    }
}