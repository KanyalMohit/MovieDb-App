package com.example.moviesbasic.screen.similar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesbasic.screen.ErrorScreen

import com.example.moviesbasic.viewModel.SimilarScreenUiState
import com.example.moviesbasic.viewModel.SimilarScreenViewModel

@Composable
fun SimilarScreen(imageId: Int?, similarScreenViewModel: SimilarScreenViewModel = viewModel()) {
    val similarScreenUiState = similarScreenViewModel.similarMovieUiState
    when(similarScreenUiState){
        is SimilarScreenUiState.loading -> SimilarLoading(similarScreenViewModel,imageId)
        is SimilarScreenUiState.SimilarMovie -> SimilarMovies(similarScreenUiState.data)
        is SimilarScreenUiState.Error -> ErrorScreen()
    }
}
@Composable
fun SimilarLoading(similarScreenViewModel: SimilarScreenViewModel, imageId: Int?) {

    if (imageId != null) {
        similarScreenViewModel.getSimilarMovies(imageId)
    }

}