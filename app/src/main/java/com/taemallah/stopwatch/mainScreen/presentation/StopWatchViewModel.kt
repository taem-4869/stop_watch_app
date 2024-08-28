package com.taemallah.stopwatch.mainScreen.presentation

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taemallah.stopwatch.utils.Constants
import com.taemallah.stopwatch.stopWatchService.StopWatchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val application: Application
): ViewModel() {
    private var coroutineScope = CoroutineScope(Dispatchers.Default)
    private val addingValueInMillis : Duration = 20.milliseconds
    private val _duration = MutableStateFlow(Duration.ZERO)
    private val _state = MutableStateFlow(StopWatchState())
    val state = combine(_duration,_state){duration, state ->
        state.copy(
            duration = duration.toString(),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),StopWatchState())

    fun onEvent(event: StopWatchEvent){
        when(event){
            StopWatchEvent.Pause -> {
                _state.update {
                    it.copy(isActive = false)
                }
            }
            StopWatchEvent.Start -> {
                if (!_state.value.isInitialized) startStopWatchCoroutine()
                _state.update {
                    it.copy(
                        isInitialized = true,
                        isActive = true
                    )
                }
                Intent(application, StopWatchService::class.java)
                    .also {
                        it.putExtra(Constants.SERVICE_KEY_DURATION,state.value.duration)
                        it.action = StopWatchService.StopWatchAction.Start.toString()
                        application.startService(it)
                    }
            }
            StopWatchEvent.Stop -> {
                coroutineScope.cancel()
                onEvent(StopWatchEvent.Reset)
                Intent(application, StopWatchService::class.java)
                    .also {
                        it.action = StopWatchService.StopWatchAction.Stop.toString()
                        application.startService(it)
                    }
            }
            StopWatchEvent.Update -> {
                Intent(application, StopWatchService::class.java)
                    .also {
                        it.putExtra(Constants.SERVICE_KEY_DURATION,state.value.duration)
                        it.action = StopWatchService.StopWatchAction.Update.toString()
                        application.startService(it)
                    }
            }
            StopWatchEvent.Reset -> {
                coroutineScope.cancel()
                _duration.update { Duration.ZERO }
                _state.update { StopWatchState() }
            }
            StopWatchEvent.Continue -> {
                _state.update {
                    it.copy(isActive = true)
                }
            }
        }
    }

    private fun startStopWatchCoroutine(){
        coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            while (true){
                while (state.value.isActive){
                    delay(addingValueInMillis)
                    _duration.update {
                        it.plus(addingValueInMillis)
                    }
                    onEvent(StopWatchEvent.Update)
                }
            }
        }
    }

}