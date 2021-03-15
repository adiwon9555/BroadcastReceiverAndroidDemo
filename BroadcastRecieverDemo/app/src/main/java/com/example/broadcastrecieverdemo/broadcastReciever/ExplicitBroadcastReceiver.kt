package com.example.broadcastrecieverdemo.broadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ExplicitBroadcastReceiver : BroadcastReceiver() {
    companion object{
        const val EXPLICIT_ACTION = "com.example.broadcastrecieverdemo.EXPLICIT_ACTION"
        const val EXPLICIT_ACTION_EXTRA_TEXT = "com.example.broadcastrecieverdemo.EXPLICIT_ACTION_EXTRA_TEXT"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if(EXPLICIT_ACTION == intent?.action){
            Toast.makeText(context, "Receiver Explicit Broadcast", Toast.LENGTH_SHORT).show()
        }
    }
}