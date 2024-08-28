package com.taemallah.stopwatch.mainScreen.presentation

import kotlin.time.Duration

data class StopWatchState(
    val isInitialized: Boolean = false,
    val isActive: Boolean = false,
    val duration: String = "00:00:000"
)