package com.example.moviesbasic.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesbasic.navigation.BottomNavigationBar
import com.example.moviesbasic.navigation.Destination
import com.example.moviesbasic.screen.popular.PopularDetailScreen
import com.example.moviesbasic.screen.popular.PopularMovieScreen
import com.example.moviesbasic.screen.profile.ProfileScreen
import com.example.moviesbasic.screen.search.SearchLoadingScreen
import com.example.moviesbasic.screen.search.SearchScreen
import com.example.moviesbasic.screen.topRated.TopDetailEntryScreen
import com.example.moviesbasic.screen.topRated.TopRatedScreen
import com.example.moviesbasic.screen.upcoming.UpcomingDetailEntryScreen
import com.example.moviesbasic.screen.upcoming.UpcomingScreen
import com.example.moviesbasic.viewModel.MovieAppUiState
import com.example.moviesbasic.viewModel.MovieAppUiState.PopularMovie
import com.example.moviesbasic.viewModel.MovieAppViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController,Destination.tabList)
        }
    ) { paddingValues ->
        val movieAppViewModel : MovieAppViewModel = viewModel()
        Box (modifier = Modifier.padding(paddingValues)){
            LoadingScreen(movieAppViewModel = movieAppViewModel)
            AppNavigation(navHostController = navController,movieAppViewModel)
        }

    }
}

@Composable
fun AppNavigation(navHostController: NavHostController, movieAppViewModel: MovieAppViewModel) {
    val popularMovieState = movieAppViewModel.popularMovieState
    val tvSeriesState = movieAppViewModel.tvSeriesState
    val topRatedState= movieAppViewModel.topRatedState
    val upComingState = movieAppViewModel.upcomingState
    LoadingScreen(movieAppViewModel = movieAppViewModel)
    NavHost(
        navController = navHostController,
        startDestination = Destination.Home.route
    ){
        composable(route = Destination.Home.route){
            HomeScreen(navHostController,movieAppViewModel)
        }
        composable(route = Destination.Search.route){
            SearchScreen(movieAppViewModel)
        }
        composable(route = Destination.Profile.route){
            ProfileScreen()
        }

        composable(route = "trending"){
            when (popularMovieState) {
                is PopularMovie -> PopularMovieScreen(
                    data = popularMovieState.data,
                    movieAppViewModel = movieAppViewModel

                ) { movieId ->
                    navHostController.navigate("popularDetail/$movieId")
                }

                else -> LoadingScreen(movieAppViewModel = movieAppViewModel)
            }
        }
        composable(route ="PopularDetail/{movieId}"){
                val movieId = it.arguments?.getString("movieId")
            when(popularMovieState){
                    is PopularMovie ->  PopularDetailScreen(movieId,data = popularMovieState.data)
                    else -> SearchLoadingScreen()
                }
        }

        composable(route = "top") {
            when (topRatedState) {
                is MovieAppUiState.TopMovie -> TopRatedScreen(
                    data = topRatedState.data,
                    movieAppViewModel = movieAppViewModel
                ) {movieId->
                    navHostController.navigate("TopDetail/$movieId")
                }
                is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                else -> ErrorScreen()
            }
        }
        composable(route ="TopDetail/{movieId}"){
            val movieId = it.arguments?.getString("movieId")
            when(topRatedState){
                is MovieAppUiState.TopMovie -> TopDetailEntryScreen(movieId,data = topRatedState.data)
                is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                else -> ErrorScreen()
            }
        }
        composable(route = "upcoming"){
            when(upComingState){
                is MovieAppUiState.Upcoming -> UpcomingScreen(
                    data = upComingState.data,
                    movieAppViewModel = movieAppViewModel,
                    onImageClick = {movieId->
                        navHostController.navigate("upcomingDetail/$movieId")
                    }
                )
                is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                else -> ErrorScreen()
            }
        }
        composable(route = "upcomingDetail/{movieId}"){
            val movieId = it.arguments?.getString("movieId")
            when(upComingState){
                is MovieAppUiState.Upcoming -> UpcomingDetailEntryScreen(movieId,upComingState.data)
                is MovieAppUiState.Loading -> LoadingScreen(movieAppViewModel = movieAppViewModel)
                else -> ErrorScreen()
            }
        }
    }

}












