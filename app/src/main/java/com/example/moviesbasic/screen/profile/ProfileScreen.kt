package com.example.moviesbasic.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesbasic.R


sealed class ProfileData(val title : String , val icon: Int , val clickIcon : Int) {
    data object Watchlist : ProfileData(title = "Watchlist", icon = R.drawable.watchlist, clickIcon = R.drawable.more)
    data object History : ProfileData(title = "History", icon = R.drawable.history, clickIcon = R.drawable.more)
    data object Collection : ProfileData(title = "Collection" , icon = R.drawable.collection, clickIcon = R.drawable.more)
    data object Ratings : ProfileData(title = "Ratings" , icon = R.drawable.rating, clickIcon = R.drawable.more)
    data object Personal : ProfileData(title = "Personal Lists" , icon = R.drawable.plist, clickIcon = R.drawable.more)

    data object Reminders : ProfileData(title = "Reminders" , icon = R.drawable.reminder, clickIcon = R.drawable.more)
    data object Hidden : ProfileData(title = "Hidden Items" , icon = R.drawable.hidden, clickIcon = R.drawable.more)
    data object Favourite : ProfileData(title = "Favourite People" , icon = R.drawable.people, clickIcon = R.drawable.more)
    data object Trailers : ProfileData(title = "Favourite Trailers" , icon = R.drawable.trailer, clickIcon = R.drawable.more)


    data object Premium : ProfileData(title = "Get Premium" , icon = R.drawable.premium, clickIcon = R.drawable.more)
    data object Restore : ProfileData(title = "Restore Purchase" , icon = R.drawable.restore, clickIcon = R.drawable.more)
    data object Settings : ProfileData(title = "Settings" , icon = R.drawable.setting, clickIcon = R.drawable.more)
    data object Help : ProfileData(title = "Help & feedback" , icon = R.drawable.help, clickIcon = R.drawable.more)
    data object About : ProfileData(title = "About", icon = R.drawable.info, clickIcon = R.drawable.more)

    companion object{
        val MyList = listOf(Watchlist,History,Collection,Ratings,Personal)
        val MyEntries = listOf(Reminders,Hidden,Favourite,Trailers)
        val Setting = listOf(Premium,Restore,Settings,Help,About)
    }

}





@Preview
@Composable
fun ProfileScreen() {
    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier , contentPadding = PaddingValues(12.dp) , verticalArrangement = Arrangement.spacedBy(30.dp)) {

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Lists",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent)
                    ) {
                        ProfileData.MyList.forEach { data ->
                            ProfileOutlineCard(data = data)
                        }
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "My Entries",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent)
                    ) {
                        ProfileData.MyEntries.forEach { data ->
                            ProfileOutlineCard(data = data)
                        }
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Settings",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent)
                    ) {
                        ProfileData.Setting.forEach { data ->
                            ProfileOutlineCard(data = data)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileOutlineCard(data: ProfileData) {
    OutlinedCard(onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(30.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF583E42))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(41.dp),
                        painter = painterResource(id = data.icon),
                        contentDescription = data.title,
                    )
                }
            }
            Text(text = data.title, color = Color.White)
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = data.clickIcon),
                    contentDescription = "more"
                )
            }
        }
    }

}
