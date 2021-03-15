package com.example.broadcastsenderdemo.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitBroadcastReceiver : BroadcastReceiver() {
    companion object{
        const val EXPLICIT_ACTION = "com.example.broadcastrecieverdemo.EXPLICIT_ACTION"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Sender Explicit Broadcast", Toast.LENGTH_SHORT).show()
    }
}