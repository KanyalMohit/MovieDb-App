package com.example.moviesbasic.screen.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesbasic.data.searchResult.SearchDto
import com.example.moviesbasic.viewModel.MovieAppUiState
import com.example.moviesbasic.viewModel.MovieAppViewModel

@Composable
fun SearchScreen(movieAppViewModel: MovieAppViewModel) {
    val searchState = movieAppViewModel.searchState
    var search by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = search) {
        if(search!=""){
            movieAppViewModel.getSearchList(search,1)
        }
    }
    var page by remember {
        mutableIntStateOf(1)
    }
    Scaffold(
        topBar = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = search,
                onValueChange = { search = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Black,
                    focusedContainerColor = Color.Transparent
                ),
                label = {
                    Text(text = "Enter Movie Name", color = Color.White)
                },
                trailingIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                    }
                }
            )
        },
        containerColor = Color.Black
    ) {
        Box(modifier = Modifier.padding(it)) {
            when (searchState) {
                is MovieAppUiState.Loading -> SearchLoadingScreen()
                is MovieAppUiState.SearchResult -> SearchResultShow(data = searchState.data)
                is MovieAppUiState.Error -> SearchLoadingScreen()
                else -> SearchLoadingScreen()
            }
        }
    }

}

@Composable
fun SearchLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun SearchResultShow(data: SearchDto) {
    val imageBaseUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    Surface(modifier = Modifier, color = Color.Black) {

        LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(4.dp)) {
            items(data.results ?: listOf()) {
                if (it != null) {
                    MovieCard(data = it, baseUrl = imageBaseUrl)
                }
            }
        }
    }
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MovieCard(data : com.example.moviesbasic.data.searchResult.Result, modifier: Modifier = Modifier, baseUrl: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedCard(onClick = {

        }, modifier = modifier, elevation = CardDefaults.cardElevation(22.dp), border = BorderStroke(3.dp,
            Color.Black), colors = CardDefaults.outlinedCardColors(containerColor = Color.Black)) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(baseUrl + data?.posterPath)
                    .size(Size.ORIGINAL).crossfade(true).build()
            )
            if (painter.state is AsyncImagePainter.State.Loading) {
                Text(text = "Loading")
            }
            Image(
                painter = painter,
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(200.dp)
            )
        }
        Text(text = "${data.title}", color = Color.White,)
    }
}

