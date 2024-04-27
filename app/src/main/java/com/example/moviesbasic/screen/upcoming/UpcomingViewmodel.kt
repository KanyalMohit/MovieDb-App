package com.example.moviesbasic.screen.upcoming

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UpcomingViewmodel : ViewModel() {
    var upPage by mutableIntStateOf(1)

    fun updatePage(todo : Int){
        if(todo == 1){
            upPage+=1
        }else{
            upPage-=1
        }
    }
}