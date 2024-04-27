package com.example.moviesbasic.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviesbasic.R
import com.example.moviesbasic.screen.popular.PopularMovieRow
import com.example.moviesbasic.screen.topRated.TopRow
import com.example.moviesbasic.screen.tv.TvRow
import com.example.moviesbasic.screen.upcoming.UpComingRow
import com.example.moviesbasic.viewModel.MovieAppUiState
import com.example.moviesbasic.viewModel.MovieAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController,movieAppViewModel: MovieAppViewModel) {

    val popularMovieState = movieAppViewModel.popularMovieState
    val tvSeriesState = movieAppViewModel.tvSeriesState
    val topRatedState= movieAppViewModel.topRatedState
    val upComingState = movieAppViewModel.upcomingState

    Surface(modifier = Modifier, color = Color.Black) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Card(modifier = Modifier, shape = CircleShape) {
                        Image(
                            modifier = Modifier.size(70.dp),
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Hello User")
                        Text(text = "Enjoy Your Favourite Movies")
                    }
                }
            }
            item {
                when (popularMovieState) {
                    is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                    is MovieAppUiState.PopularMovie -> PopularMovieRow(
                        popularMovieState.data,
                        onTrendClick = {
                            navHostController.navigate("trending")
                        },
                        onImageClick = { movieId ->
                            navHostController.navigate("PopularDetail/$movieId")
                        }
                    )

                    else -> ErrorScreen()
                }
            }
            item {
                when (upComingState) {
                    is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                    is MovieAppUiState.Upcoming -> UpComingRow(
                        upComingState.data,
                        onArrowClick = {
                                       navHostController.navigate("upcoming")
                        },
                        onImageClick = {movieId->
                            navHostController.navigate("upcomingDetail/$movieId")
                        })

                    else -> ErrorScreen()
                }
            }
            item {
                when (topRatedState) {
                    is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                    is MovieAppUiState.TopMovie -> TopRow(
                        data = topRatedState.data,
                        onArrowClick = { navHostController.navigate("top") },
                        onImageClick = { movieId ->
                            navHostController.navigate("TopDetail/$movieId")
                        })

                    else -> ErrorScreen()
                }
            }
            item {
                when (tvSeriesState) {
                    is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                    is MovieAppUiState.TvSeries -> TvRow(data = tvSeriesState.data)
                    else -> ErrorScreen()
                }

            }

        }
    }
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MovieTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Movie App", style = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(
                        0xFFDAC76A
                    ) else Color.White,
                    fontSize = 30.sp,
                    textMotion = TextMotion.Animated
                )
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isSystemInDarkTheme()) Color(0xFF154981) else Color(0xFFE09F9F)
        )
    )
}

@Composable
fun ErrorScreen() {
    Text(text = "Eroor")
}



@Composable
fun LoadingScreen(movieAppViewModel:  MovieAppViewModel) {
    movieAppViewModel.getPopularMovieList()
    movieAppViewModel.getTopMovieList()
    movieAppViewModel.getNowPlayingMovieList()
    movieAppViewModel.getUpComingList()
}

