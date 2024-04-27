package com.example.moviesbasic.screen.topRated

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TopRatedViewModel : ViewModel() {
    var topPage by mutableIntStateOf(1)

    fun updateTopPage(todo : Int){
        if(todo == 1){
            topPage+=1
        }else{
            topPage-=1
        }
    }
}