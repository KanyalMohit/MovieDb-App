package com.example.moviesbasic.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController, tabList: List<Destination>) {
    NavigationBar(
        modifier = Modifier.height(90.dp),
        containerColor = Color(0XFF000000)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        tabList.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(destination.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(route = it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            contentDescription = destination.title
                        )
                        Text(text = destination.title)
                    }
                })
        }
    }
}