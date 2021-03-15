package com.example.broadcastrecieverdemo.broadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

//Basically a broadcast intent action is only a string that that conventionally mean something.
//Similarly we can create any custom action string and listen/send broadcast for it.
//To make the action string unique and not confuse with others, we conventionally prefix with package name

class CustomBroadcastReciever : BroadcastReceiver() {
    companion object{
        const val CUSTOM_ACTION = "com.example.broadcastrecieverdemo.EXAMPLE_ACTION"
        const val CUSTOM_ACTION_EXTRA_TEXT = "com.example.broadcastrecieverdemo.EXAMPLE_ACTION_EXTRA_TEXT"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if(CUSTOM_ACTION == intent?.action){
            val textRecieved = intent.getStringExtra(CUSTOM_ACTION_EXTRA_TEXT)
            Toast.makeText(context, "In Receiver $textRecieved",Toast.LENGTH_SHORT).show()
        }
    }
}