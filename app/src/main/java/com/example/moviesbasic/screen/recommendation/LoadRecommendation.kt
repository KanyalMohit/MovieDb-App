package com.example.moviesbasic.screen.recommendation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesbasic.screen.ErrorScreen
import com.example.moviesbasic.viewModel.RecommendationUiState
import com.example.moviesbasic.viewModel.RecommendationViewModel

@Composable
fun LoadRecommendation(imageData: Int? , recommendationViewModel: RecommendationViewModel = viewModel()) {
    val recommendationUiState= recommendationViewModel.recommendationUiState
    when(recommendationUiState){
        is RecommendationUiState.loading -> RecommendationLoading(recommendationViewModel,imageData)
        is RecommendationUiState.RecommendationMovie -> RecommendationScreen(recommendationUiState.data)
        is RecommendationUiState.Error -> ErrorScreen()
    }
}


@Composable
fun RecommendationLoading(recommendationViewModel: RecommendationViewModel, movieId: Int?) {

        if (movieId != null) {
            recommendationViewModel.getRecommendationMovie(movieId)
        }

}