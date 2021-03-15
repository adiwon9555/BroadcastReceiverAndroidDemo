package com.example.broadcastrecieverdemo.broadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

/* Recievers defined in manifest are static reciever, and can recieve intent even when app is not running
Implicit broadcast intent is for all apps that have subscribed to that intent
Explicit broadcast intent is for specific app whose package name is defined in broadcast intent
Some system level implicit broadcast intent action can not be subscribed by static reciever -> eg. Connectivity change
The reason being that the system resource can be consumed significantly if frequently same action is recieved by all app
hence only rarely recieved intent action like BOOT_COMPLETED can be statically registered, meaning can recieve intent even when app is not running
On the other hand dynamically registered reciever are scoped in the context of app, and can recieve braodcast only when app is running
hence any intent action can be registered dynamically/programitally to recieve broadcast */

class DemoBroadcastReciever : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if(Intent.ACTION_BOOT_COMPLETED == intent?.action){
            Toast.makeText(context,"Boot Completed",Toast.LENGTH_LONG).show()
        }
        //Connectivity Action is a sticky broadcast meaning it sticks around after is send unless recieved
        if(ConnectivityManager.CONNECTIVITY_ACTION == intent?.action){
            val isNotConnected = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY,false
            )
            if(isNotConnected){
                Toast.makeText(context,"Internet Lost",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Internet Connected",Toast.LENGTH_SHORT).show()
            }

        }
    }
}