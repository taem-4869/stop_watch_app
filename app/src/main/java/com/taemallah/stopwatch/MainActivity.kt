package com.taemallah.stopwatch

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.taemallah.stopwatch.mainScreen.presentation.StopWatchViewModel
import com.taemallah.stopwatch.mainScreen.presentation.components.MainScreen
import com.taemallah.stopwatch.ui.theme.StopWatchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<StopWatchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        enableEdgeToEdge()
        setContent {
            StopWatchTheme {
                MainScreen(state = viewModel.state.collectAsState().value, onEvent = viewModel::onEvent )
            }
        }
    }
}