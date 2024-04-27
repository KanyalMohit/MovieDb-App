package com.example.moviesbasic.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesbasic.data.detail.DetailDto
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.data.movieCast.CastDto
import com.example.moviesbasic.network.MovieApi
import kotlinx.coroutines.launch
import okio.IOException

sealed interface DetailScreenUiState{
    data class DetailMovie(val data : DetailDto) : DetailScreenUiState
    data class MovieImage(val data : ImageDto) : DetailScreenUiState
    data class MovieCast(val data : CastDto) : DetailScreenUiState
    object Loading : DetailScreenUiState
    object Error : DetailScreenUiState
}

class DetailScreenViewModel : ViewModel() {
    var detailMovieData : DetailScreenUiState by mutableStateOf(DetailScreenUiState.Loading)

    var movieImageState : DetailScreenUiState by mutableStateOf(DetailScreenUiState.Loading)

    var movieCastState : DetailScreenUiState by mutableStateOf(DetailScreenUiState.Loading)
    fun getMovieDetails(
        movieId: String
    ){
        viewModelScope.launch {
            detailMovieData = try {
                DetailScreenUiState.DetailMovie(MovieApi.retrofitService.getMovieDetails(movieId =movieId.toInt()))
            }catch (e:IOException){
                DetailScreenUiState.Error
            }
        }
    }

    fun getMovieImage(
        movieId: String
    ) {
        viewModelScope.launch {
            movieImageState = try {
                DetailScreenUiState.MovieImage(MovieApi.retrofitService.getMovieImage(movieId.toInt()))
            } catch (e: IOException) {
                DetailScreenUiState.Error
            }
        }
    }
    fun getMovieCast(
        movieId: String
    ){
        viewModelScope.launch {
            movieCastState = try {
                DetailScreenUiState.MovieCast(MovieApi.retrofitService.getMovieCast(movieId = movieId.toInt()))
            }catch (e : IOException){
                DetailScreenUiState.Error
            }
        }
    }


}