package com.gmail.ngampiosauvet.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Math.random


class HomeViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<String>("10 - A")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    private  val letter = MutableStateFlow("W")
    private  val number = MutableStateFlow(20)


  /**  fun launchChrono() {
        viewModelScope.launch {
            letters.collect {seconde ->
                _uiState.update { seconde }
            }
        }
    } **/

    fun launchGame() {

      viewModelScope.launch {
         /** letters().collect { let ->
          letter.update { let }
          }

          chrono().collect{ sec ->
          number.update { sec }
          } **/

          letters().combine(chrono()) { letter, number ->
              "$letter - $number"
          }.collect { letnum ->
              _uiState.update { letnum }
          }
      }


      }



       val myStateFlow:StateFlow<String> = chrono().combine( letters()) { let, num ->
      "$let - $num"
      }
           .map { "R $it" }
           .stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(5000),
      "10-A"
      )

      private fun chrono(): Flow<Int> = flow {
          for (i in 10 downTo 0) {
              delay(1000)
              emit(i)
          }
      }

      private fun letters(): Flow<String> = flow {
          val letterList = listOf("A", "B", "Z", "C", "O", "Q")
              .shuffled()

          for (i in letterList) {
              delay(1000)
              emit(i)
          }
      }


  }
