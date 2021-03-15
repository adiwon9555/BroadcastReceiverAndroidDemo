package com.example.broadcastsenderdemo.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

//local broadcast is deprecated and should use live data instead
class LocalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Local demo Broadcast", Toast.LENGTH_SHORT).show()
    }
}