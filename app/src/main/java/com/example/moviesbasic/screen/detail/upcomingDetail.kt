package com.example.moviesbasic.screen.detail


import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AssistChip

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed

import com.example.moviesbasic.data.detail.DetailDto
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.data.movieCast.CastDto
import com.example.moviesbasic.data.upcoming.Result
import com.example.moviesbasic.screen.recommendation.LoadRecommendation
import com.example.moviesbasic.screen.similar.SimilarScreen
import com.example.moviesbasic.utils.CrewScreen
import com.example.moviesbasic.utils.InformationItem


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpcomingDetailScreen(
    imageData: ImageDto,
    detailData: DetailDto,
    movie: Result?,
    castData: CastDto
) {
    var selectedIndex: Int by remember {
        mutableIntStateOf(0)
    }
    val tabList = listOf("About","Cast" , "Similar" , "Recommendation")
    val tabPagerState = rememberPagerState {
        tabList.size
    }
    LaunchedEffect(selectedIndex) {
        tabPagerState.animateScrollToPage(selectedIndex)
    }
    LaunchedEffect(tabPagerState.currentPage , tabPagerState.isScrollInProgress) {
        if(!tabPagerState.isScrollInProgress){
            selectedIndex = tabPagerState.currentPage
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column {
            DetailTopScreen(imageData, detailData = detailData)
            Scaffold(
                modifier = Modifier,
                topBar = {
                    ScrollableTabRow(
                        modifier = Modifier,
                        selectedTabIndex = selectedIndex,
                        containerColor = Color.Black,
                        edgePadding = 15.dp,
                        divider = {}
                    ) {
                        tabList.fastForEachIndexed { index, item ->
                            Tab(
                                selected = index == selectedIndex,
                                onClick = {
                                    selectedIndex = index

                                }) {
                                Text(text = item, color = Color(0xC9FFD600), fontSize = 20.sp)
                            }
                        }
                    }
                },
                containerColor = Color.Black,
            ) {
                Box(
                    Modifier.padding(paddingValues = it)
                ) {
                    HorizontalPager(state = tabPagerState , beyondBoundsPageCount = 2) {index->
                        when (index) {
                            0 -> {
                                UpcomingDetailTab(detailData,movie,castData)
                            }
                            1 -> {
                                CrewScreen(castData = castData)
                            }
                            2 -> {
                                SimilarScreen(imageId = movie?.id)
                            }
                            else -> {
                                LoadRecommendation(imageData = movie?.id)
                            }
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun UpcomingDetailTab(detailData: DetailDto, movie: Result?, castData: CastDto) {
    val castMap: MutableMap<String, String> = mutableMapOf()
    var pCompanies  =""
    castData.crew?.forEach { crewMember ->
        if (crewMember?.department == "Directing" || crewMember?.department == "Production") {
            crewMember.name?.let { name ->
                castMap[name] = crewMember.department
            }
        }
    }
    LazyColumn(modifier = Modifier.padding(2.dp)) {
        item {
            Column(
                modifier = Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${detailData.tagline}",
                    fontSize = 30.sp,
                    color = Color(
                        0xFF00796B
                    )
                )
                Text(
                    text = "${movie?.overview}",
                    color = Color.White,
                    softWrap = true,
                    lineHeight = 25.sp,
                    fontSize = 17.sp
                )
                HorizontalDivider(modifier = Modifier, thickness = .3.dp)
            }

        }
        item {
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(2.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Genres",
                        modifier = Modifier,
                        fontSize = 25.sp,
                        color = Color(0xFFEEEAE2)
                    )
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(detailData.genres ?: listOf()) {
                            AssistChip(
                                modifier = Modifier.padding(3.dp),
                                onClick = { },
                                label = {
                                    Text(
                                        text = "${it?.name}",
                                        color = Color(0xFFFBC02D)
                                    )
                                })
                        }
                    }
                    HorizontalDivider(thickness = .5.dp, modifier = Modifier)
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text(
                        text = "Featured Crew >",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 27.sp,
                            letterSpacing = 1.sp
                        )
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        castMap.onEachIndexed { index, entry ->
                            if (index < 5) {
                                Row(
                                    modifier = Modifier
                                        .width(400.dp)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(text = entry.key, color = Color(0xFFF7E1E1), fontSize = 20.sp)
                                    Text(text = entry.value, color = Color(0xFFF7E1E1), fontSize = 15.sp)
                                }

                            }
                        }
                    }
                }

            }
        }
        item {
            HorizontalDivider(thickness = .5.dp, modifier = Modifier)
            Box(modifier = Modifier.padding(12.dp)) {
                Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text(text = "Information", color = Color.White , style = MaterialTheme.typography.headlineMedium)
                    InformationItem("Original Title", detailData.originalTitle ?: "")
                    InformationItem("Status", detailData.status ?: "")
                    InformationItem(title = "Runtime", value = (detailData.runtime?.toString() + " mins"))
                    InformationItem("Original Language", detailData.originalLanguage ?: "")

                    detailData.productionCountries?.forEach {
                        pCompanies += it?.name + " "
                    }
                    InformationItem(title = "Production Countries", value = pCompanies)
                    InformationItem(
                        title = "Companies",
                        value = detailData.productionCompanies?.get(0)?.name ?: ""
                    )
                    InformationItem(
                        title = "Budget", value = ("$" + detailData.budget?.toString())

                    )
                    InformationItem(
                        title = "Revenue",
                        value = ("$"+ detailData.revenue?.toString())
                    )
                }
            }
        }

    }
}
