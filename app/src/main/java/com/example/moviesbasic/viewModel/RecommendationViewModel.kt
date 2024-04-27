package com.example.moviesbasic.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesbasic.data.recommendation.RecommendationDto
import com.example.moviesbasic.network.MovieApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface RecommendationUiState{
    data class RecommendationMovie(val data: RecommendationDto) : RecommendationUiState
    object loading : RecommendationUiState
    object Error : RecommendationUiState
}
class RecommendationViewModel: ViewModel() {
    var recommendationUiState : RecommendationUiState by mutableStateOf(RecommendationUiState.loading)

    fun getRecommendationMovie(movieId : Int){
        viewModelScope.launch {
            recommendationUiState = try {
                RecommendationUiState.RecommendationMovie(MovieApi.retrofitService.getRecommendation(movieId))
            }catch (e : IOException){
                RecommendationUiState.Error
            }
        }
    }
}