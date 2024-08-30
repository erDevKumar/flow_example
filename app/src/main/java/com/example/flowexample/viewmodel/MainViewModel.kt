package com.example.flowexample.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    var coldFlow :Flow<String>? = null

    val hotFlow= MutableStateFlow<String>("")

    suspend fun publishCold(input:String) {
        coldFlow= flow<String> {
            emit(input)
            delay(1)
            emit(input.reversed())
        }
    }
    suspend fun publishHot(input:String) {
        hotFlow.emit(input)
    }



}