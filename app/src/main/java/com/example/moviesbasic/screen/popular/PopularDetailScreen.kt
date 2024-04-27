package com.example.moviesbasic.screen.popular

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesbasic.data.popular.PopularMovieDto
import com.example.moviesbasic.screen.ErrorScreen
import com.example.moviesbasic.screen.detail.DetailScreen
import com.example.moviesbasic.viewModel.DetailScreenUiState
import com.example.moviesbasic.viewModel.DetailScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularDetailScreen(movieId: String?, data: PopularMovieDto) {

    val detailScreenViewModel : DetailScreenViewModel = viewModel()
    val movieImageUiState = detailScreenViewModel.movieImageState
    val detailMovieUiState = detailScreenViewModel.detailMovieData
    val movieCastState = detailScreenViewModel.movieCastState
    val result = remember {
        data.results?.find{
            it?.id.toString() == movieId
        }
    }
    Surface(
        color = Color.Black
    ) {
        when (movieImageUiState) {
            is DetailScreenUiState.Loading -> LoadingScreen(detailScreenViewModel, movieId!!)
            is DetailScreenUiState.MovieImage -> {
                when (detailMovieUiState) {
                    is DetailScreenUiState.Error -> ErrorScreen()
                    is DetailScreenUiState.DetailMovie ->
                        when (movieCastState) {
                            is DetailScreenUiState.MovieCast ->
                                DetailScreen(
                                    movieImageUiState.data,
                                    detailMovieUiState.data,
                                    result,
                                    movieCastState.data
                                )
                            else -> {
                                ErrorScreen()
                            }
                        }

                    else -> LoadingScreen(
                        detailScreenViewModel = detailScreenViewModel,
                        movieId = movieId!!
                    )
                }
            }

            else -> ErrorScreen()
        }
    }





}



@Composable
fun LoadingScreen(detailScreenViewModel: DetailScreenViewModel, movieId: String) {
        detailScreenViewModel.getMovieDetails(movieId)
        detailScreenViewModel.getMovieImage(movieId)
        detailScreenViewModel.getMovieCast(movieId)
}
