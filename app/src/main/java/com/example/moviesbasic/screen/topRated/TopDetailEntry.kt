package com.example.moviesbasic.screen.topRated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesbasic.data.detail.DetailDto
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.data.movieCast.CastDto
import com.example.moviesbasic.data.toprated.Result
import com.example.moviesbasic.data.toprated.TopRatedMovieDto
import com.example.moviesbasic.screen.ErrorScreen
import com.example.moviesbasic.screen.detail.DetailScreen
import com.example.moviesbasic.screen.detail.TopDetailScreen
import com.example.moviesbasic.viewModel.DetailScreenUiState
import com.example.moviesbasic.viewModel.DetailScreenViewModel
import kotlin.Result as Result1

@Composable
fun TopDetailEntryScreen(movieId: String?, data: TopRatedMovieDto) {
    val detailScreenViewModel : DetailScreenViewModel = viewModel()
    val movieImageUiState = detailScreenViewModel.movieImageState
    val detailMovieUiState = detailScreenViewModel.detailMovieData
    val movieCastState = detailScreenViewModel.movieCastState
    val result = remember {
        data.results?.find{
            it?.id.toString() == movieId
        }
    }
    when (movieImageUiState) {
        is DetailScreenUiState.Loading -> LoadingScreen(
            detailScreenViewModel = detailScreenViewModel,
            movieId = movieId!!
        )
        is DetailScreenUiState.MovieImage -> {
            when (detailMovieUiState) {
                is DetailScreenUiState.Error -> ErrorScreen()
                is DetailScreenUiState.DetailMovie ->
                    when (movieCastState) {
                        is DetailScreenUiState.MovieCast -> {
                              TopDetailScreen(
                                  movieImageUiState.data,
                                  detailMovieUiState.data,
                                  result,
                                  movieCastState.data
                              )
                        }
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



@Composable
private fun LoadingScreen(detailScreenViewModel: DetailScreenViewModel, movieId: String) {
    detailScreenViewModel.getMovieDetails(movieId)
    detailScreenViewModel.getMovieImage(movieId)
    detailScreenViewModel.getMovieCast(movieId)
}