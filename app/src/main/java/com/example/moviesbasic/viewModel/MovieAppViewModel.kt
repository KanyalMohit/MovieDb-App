package com.example.moviesbasic.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.data.popular.PopularMovieDto
import com.example.moviesbasic.data.searchResult.SearchDto
import com.example.moviesbasic.data.toprated.TopRatedMovieDto
import com.example.moviesbasic.data.tvSeries.TvSeriesDto
import com.example.moviesbasic.data.upcoming.UpComingDto
import com.example.moviesbasic.network.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MovieAppUiState{
    data class PopularMovie(val data: PopularMovieDto) : MovieAppUiState
    data class TopMovie(val data : TopRatedMovieDto)  : MovieAppUiState
    data class TvSeries(val data : TvSeriesDto ) : MovieAppUiState
    data class Upcoming(val data : UpComingDto) : MovieAppUiState
    data class SearchResult(val data : SearchDto) : MovieAppUiState
    object Loading : MovieAppUiState
    object Error : MovieAppUiState
}


class MovieAppViewModel : ViewModel() {

    var popularMovieState : MovieAppUiState by mutableStateOf(MovieAppUiState.Loading)

    var topRatedState : MovieAppUiState by mutableStateOf(MovieAppUiState.Loading)

    var tvSeriesState : MovieAppUiState by mutableStateOf(MovieAppUiState.Loading)

    var upcomingState : MovieAppUiState by mutableStateOf(MovieAppUiState.Loading)

    var searchState : MovieAppUiState by mutableStateOf(MovieAppUiState.Loading)

    var popularPage by mutableIntStateOf(1)


    fun getPopularMovieList(
        category : String = "popular",
        page : Int = 1
    ){
        viewModelScope.launch {
            popularMovieState = try {
              MovieAppUiState.PopularMovie(MovieApi.retrofitService.getPopularMovieList(category =category, page = page))
            }catch (e : IOException){
                MovieAppUiState.Error
            }
        }
    }
    fun getTopMovieList(
        category: String = "top_rated",
        page: Int =1
    ){
        viewModelScope.launch {
            topRatedState = try {
                MovieAppUiState.TopMovie(MovieApi.retrofitService.getTopMovieList(page=page, category = category))
            }catch (e : IOException){
                MovieAppUiState.Error
            }
        }
    }
    fun getNowPlayingMovieList(
        category: String = "popular",
        page: Int = 1
    ){
        viewModelScope.launch {
            tvSeriesState = try {
                MovieAppUiState.TvSeries(MovieApi.retrofitService.getNowPlayingList(category, page))
            }catch (e : IOException){
                MovieAppUiState.Error
            }
        }
    }

    fun getUpComingList(
        category: String = "upcoming",
        page: Int = 1
    ){
        viewModelScope.launch{
            upcomingState = try {
                MovieAppUiState.Upcoming(MovieApi.retrofitService.getUpcomingList(category, page))
            }catch (e : IOException){
                MovieAppUiState.Error
            }
        }
    }

    fun getSearchList(
        query : String,
        page: Int
    ){
        viewModelScope.launch {
            searchState = try {
                MovieAppUiState.SearchResult(MovieApi.retrofitService.getSearchList(query, page))
            }catch (e : IOException){
                MovieAppUiState.Error
            }
        }
    }

    fun updatePopularPage(todo : Int){
        if(todo == 1){
            popularPage+=1
        }else{
            popularPage-=1
        }
    }


}