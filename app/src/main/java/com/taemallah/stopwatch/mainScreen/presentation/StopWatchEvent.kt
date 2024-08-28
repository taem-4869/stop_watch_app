package com.taemallah.stopwatch.mainScreen.presentation

sealed class StopWatchEvent {
    data object Start: StopWatchEvent()
    data object Pause: StopWatchEvent()
    data object Continue: StopWatchEvent()
    data object Stop: StopWatchEvent()
    data object Reset: StopWatchEvent()
    data object Update: StopWatchEvent()
}