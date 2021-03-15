package com.example.broadcastrecieverdemo

import android.Manifest
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.broadcastrecieverdemo.broadcastReciever.CustomBroadcastReciever
import com.example.broadcastrecieverdemo.broadcastReciever.DemoBroadcastReciever
import com.example.broadcastrecieverdemo.broadcastReciever.OrderedBroadcastReceiver1

//dynamically registered reciever are scoped in the context of app, and can recieve braodcast only when app is running
//The context can be application or activity
//In activity broadcast can be registered/unregistered in any lifcycle function - onStart/onStop , onResume/onPause, onCreate-onDestroy(This will activate reciever in background also)
class MainActivity : AppCompatActivity() {
    private val demoBroadcastReciever = DemoBroadcastReciever()
    private val customBroadcastReciever = CustomBroadcastReciever()
    private val orderedBroadcastReceiver1 = OrderedBroadcastReceiver1()


    companion object{
        const val ORDERED_ACTION = "com.example.broadcastrecieverdemo.ORDERED_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //since custom events are being sent from other app, this app will be in background, so to recieve we are writing in onCreate
        val intentFilter = IntentFilter(CustomBroadcastReciever.CUSTOM_ACTION)
        registerReceiver(customBroadcastReciever,intentFilter)

        val intentFilterOrdered = IntentFilter(ORDERED_ACTION)
        intentFilterOrdered.priority = 1
        // parmission set for dynamically registered reciever
        //the sender app has to have this permission to send broadcast to this reciever
        registerReceiver(orderedBroadcastReceiver1,intentFilterOrdered,Manifest.permission.VIBRATE,null)




    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(demoBroadcastReciever,intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(demoBroadcastReciever)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(customBroadcastReciever)
        unregisterReceiver(orderedBroadcastReceiver1)
    }
}