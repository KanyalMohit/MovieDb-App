package com.example.moviesbasic.screen.tv

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import com.example.moviesbasic.data.tvSeries.Result
import com.example.moviesbasic.data.tvSeries.TvSeriesDto
import com.example.moviesbasic.utils.asyncImagePainter

@Composable
fun TvRow(data: TvSeriesDto) {
    val imageBaseUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    Box{
        Column (
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Tv Shows",
                    modifier = Modifier,
                    style = TextStyle(fontSize = 25.sp)
                )
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "next", modifier = Modifier.size(35.dp))
            }
            LazyRow(modifier = Modifier){
                items(data.results?.subList(0,10)?: listOf()){
                    val painter = asyncImagePainter(imageBaseUrl, it)
                    if(painter.state is AsyncImagePainter.State.Loading){
                        Text(text = "Loading")
                    }
                    TvImageCard(painter, it)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TvImageCard(
    painter: AsyncImagePainter,
    it: Result?
) {
    Column (
    ){
        OutlinedCard(
            onClick = {

            }, modifier = Modifier.padding(4.dp),
            elevation = CardDefaults.outlinedCardElevation(20.dp),
            border = BorderStroke(3.dp, Color.Black),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.Black)
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                painter = painter,
                contentDescription = "Poster Image",
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            text = "${it?.name}",
            modifier = Modifier
                .width(110.dp)
                .padding(8.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 12.sp, lineBreak = LineBreak.Paragraph
            ),
            softWrap = true,
        )
    }
}