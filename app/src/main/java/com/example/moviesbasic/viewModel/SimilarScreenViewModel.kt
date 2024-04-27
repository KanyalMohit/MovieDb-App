package com.example.moviesbasic.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesbasic.data.similar.SimilarDto
import com.example.moviesbasic.network.MovieApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SimilarScreenUiState{
    data class SimilarMovie(val data : SimilarDto) : SimilarScreenUiState
    object loading : SimilarScreenUiState
    object Error : SimilarScreenUiState
}

class SimilarScreenViewModel : ViewModel(){
    var similarMovieUiState : SimilarScreenUiState by mutableStateOf(SimilarScreenUiState.loading)

    fun getSimilarMovies(movieId : Int){
        viewModelScope.launch {
            similarMovieUiState = try {
                SimilarScreenUiState.SimilarMovie(MovieApi.retrofitService.getSimilar(movieId))
            }catch (e: IOException){
                SimilarScreenUiState.Error
            }
        }
    }

}