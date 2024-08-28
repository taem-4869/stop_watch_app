package com.taemallah.stopwatch.stopWatchService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.taemallah.stopwatch.R
import com.taemallah.stopwatch.utils.Constants

class StopWatchService: Service() {

    private val notificationBuilder = NotificationCompat
        .Builder(this, Constants.SERVICE_CHANNEL_ID)
        .setSmallIcon(R.drawable.stop_watch)
        .setContentTitle("")

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var duration = intent?.getStringExtra(Constants.SERVICE_KEY_DURATION)?:"00:00:000"
        duration = intent?.extras?.getString(Constants.SERVICE_KEY_DURATION)?:"00:00:000"
        when(intent?.action){
            StopWatchAction.Start.toString() -> start(duration)
            StopWatchAction.Update.toString() -> update(duration)
            StopWatchAction.Pause.toString() ->{}
            StopWatchAction.Stop.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(duration: String){
        val notification = notificationBuilder
            .setContentText(duration)
            .build()
        startForeground(Constants.SERVICE_ID,notification)
    }

    private fun update(duration: String){
        Log.e("kid_e", "duration: $duration")
        val notification = notificationBuilder
            .setContentText(duration)
            .build()
        startForeground(Constants.SERVICE_ID,notification)
    }

    enum class StopWatchAction{
        Start, Pause, Stop,Update
    }
}