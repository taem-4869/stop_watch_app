package com.taemallah.stopwatch.mainScreen.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taemallah.stopwatch.R
import com.taemallah.stopwatch.mainScreen.presentation.StopWatchEvent
import com.taemallah.stopwatch.mainScreen.presentation.StopWatchState

@Composable
fun MainScreen(state: StopWatchState, onEvent: (StopWatchEvent)-> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Stop watch",
                style = MaterialTheme.typography.displaySmall)
            OutlinedCard(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(300.dp)
                    .shadow(
                        8.dp,
                        CircleShape,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        spotColor = MaterialTheme.colorScheme.primary
                    ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = state.duration,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                    onEvent(StopWatchEvent.Reset)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.reset),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    if (state.isActive){
                        onEvent(StopWatchEvent.Pause)
                    }else{
                        onEvent(StopWatchEvent.Start)
                    }
                }) {
                    Icon(
                        painter = if (state.isActive) painterResource(id = R.drawable.pause) else painterResource(id = R.drawable.play),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    onEvent(StopWatchEvent.Stop)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.stop),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(state = StopWatchState(), onEvent = {})
}