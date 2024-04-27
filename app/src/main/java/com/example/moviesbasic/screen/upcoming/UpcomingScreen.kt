package com.example.moviesbasic.screen.upcoming

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.upcoming.Result
import com.example.moviesbasic.data.upcoming.UpComingDto
import com.example.moviesbasic.utils.AppButton
import com.example.moviesbasic.viewModel.MovieAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingScreen(
    data: UpComingDto,
    movieAppViewModel: MovieAppViewModel,
    onImageClick: (String) -> Unit,
    upcomingViewmodel: UpcomingViewmodel = viewModel()
) {
    var page by remember {
        mutableIntStateOf(upcomingViewmodel.upPage)
    }

    val imageBaseUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Trending Movies") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "arrow"
                        )
                    }
                }
            )
        }
    ) {paddingValues->
        Surface(modifier = Modifier.padding(paddingValues = paddingValues), color = Color.Black) {

            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(4.dp)) {
                items(data.results ?: listOf()) {
                    if (it != null) {
                        MovieCard(
                            it, modifier = Modifier.padding(10.dp), baseUrl = imageBaseUrl,
                            onClick = onImageClick
                        )
                    }
                }
                item {
                    Text(text = "")
                }
                item {
                    if (page == 1) {
                        Text(text = "")
                    } else {
                        AppButton(text = "Previous") {
                            upcomingViewmodel.updatePage(-1)
                            page = upcomingViewmodel.upPage
                            movieAppViewModel.getUpComingList(page=page)
                        }
                    }
                }
                item {
                    Text(text = "")
                }
                item {
                    AppButton(text = "Next") {
                        upcomingViewmodel.updatePage(1)
                        page = upcomingViewmodel.upPage
                        movieAppViewModel.getUpComingList(page=page)
                    }
                }
            }
        }
    }

}

@Composable
fun MovieCard(
    data: Result,
    modifier: Modifier = Modifier,
    baseUrl: String,
    onClick: (String) -> Unit
) {
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedCard(
            onClick = {
                onClick(
                    data?.id.toString()
                )
            },
            modifier = modifier,
            elevation = CardDefaults.cardElevation(22.dp),
            border = BorderStroke(
                3.dp,
                Color.Black
            ),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.Black)
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(baseUrl + data?.posterPath)
                    .size(Size.ORIGINAL).crossfade(true).build()
            )
            if (painter.state is AsyncImagePainter.State.Loading) {
                Text(text = "Loading")
            }
            Image(
                painter = painter,
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(200.dp)
            )
        }
        Text(text = "${data?.title}", color = Color.White)
    }
}