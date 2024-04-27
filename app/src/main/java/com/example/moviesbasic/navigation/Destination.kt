package com.example.moviesbasic.navigation

import com.example.moviesbasic.R

sealed class Destination (val route : String , val icon : Int,val title : String){

    data object Home : Destination(
        route = "home",
        icon = R.drawable.home,
        title = "Home"
    )
    data object Search : Destination(
        route = "search",
        icon =R.drawable.search ,
        title = "Search"
    )
    data object Profile : Destination(
        route = "profile",
        icon = R.drawable.outline_account_circle_24,
        title = "Account"
    )
    companion object{
        val tabList = listOf(Home,Search,Profile)
    }
}