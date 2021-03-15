package com.example.broadcastsenderdemo

import android.Manifest
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.broadcastsenderdemo.broadcastReceiver.ExplicitBroadcastReceiver
import com.example.broadcastsenderdemo.broadcastReceiver.LocalBroadcastReceiver
import com.example.broadcastsenderdemo.broadcastReceiver.ResultOrderedBroadcastReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private val explicitBroadcastReceiver = ExplicitBroadcastReceiver()
    private val localBroadcastReceiver = LocalBroadcastReceiver()
    private lateinit var localBroadcastManager : LocalBroadcastManager
    companion object{
        const val CUSTOM_ACTION = "com.example.broadcastrecieverdemo.EXAMPLE_ACTION"
        const val CUSTOM_ACTION_EXTRA_TEXT = "com.example.broadcastrecieverdemo.EXAMPLE_ACTION_EXTRA_TEXT"

        const val ORDERED_ACTION = "com.example.broadcastrecieverdemo.ORDERED_ACTION"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.message_textView)

        //All localBroadcast function has to be called using localBroadcastManager
        //Hene it can only be dynamic and explicit to this this package
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
    }

    fun sendCustomBroadCast(view: View) {
        //Sending Custom Implicit Broadcast
        val intent = Intent(CUSTOM_ACTION)
        intent.putExtra(CUSTOM_ACTION_EXTRA_TEXT,"Custom Broadcast Received")
        sendBroadcast(intent)
    }

    //To make change to the UI of components of this activity we can create the BroadcastReceiver anonymous class here to access the view from this activity
    //But ideally there is better way to send broadcast to own app using localBroadcast, becoz with this method our sensitive information could be received by anyone who is listening to this action

    private val broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(CUSTOM_ACTION == intent?.action){
                val textRecieved = intent.getStringExtra(CUSTOM_ACTION_EXTRA_TEXT)
                textView.text = textRecieved
            }
        }
    }


    override fun onStart() {
        super.onStart()
        //Registering the Custom broadcastReceiver
        val intentFilter = IntentFilter(CUSTOM_ACTION)
        registerReceiver(broadcastReceiver,intentFilter)

        //Registering the Explicit broadcastReceiver
        val explicitIntentFilter = IntentFilter(ExplicitBroadcastReceiver.EXPLICIT_ACTION)
        registerReceiver(explicitBroadcastReceiver,explicitIntentFilter)

        //Registering localBroadcast Reciever
        val localIntentFilter = IntentFilter(CUSTOM_ACTION)
        localBroadcastManager.registerReceiver(localBroadcastReceiver,localIntentFilter)
    }

    override fun onStop() {
        super.onStop()
        //unRegistering the Custom broadcastReceiver
        unregisterReceiver(broadcastReceiver)

        //unRegistering the Explicit broadcastReceiver
        unregisterReceiver(explicitBroadcastReceiver)
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver)
    }

    fun sendExplicitBroadCast(view: View) {
        //Explicit Broadcast requires package name and broadcastReceiver class in Intent constructor
        //Here sending to own package, can be replaced by any package name (app name)
//        val intent = Intent(this,ExplicitBroadcastReceiver::class.java)
//

//        Or same can be written as
//        val intent = Intent()
//        intent.setClass(this,ExplicitBroadcastReceiver::class.java)

        //        Or same can be written as
//        val intent = Intent()
//        val componentName = ComponentName(this,"com.example.broadcastsenderdemo.broadcastReceiver")
//        intent.component = componentName

        //        Or same can be written as
//        intent.setClassName(this,"com.example.broadcastsenderdemo.broadcastReceiver")
//        sendBroadcast(intent)


        //Or we should define the action name with the package [Note: The receiver has to listen to this action]
//        val intent = Intent(ExplicitBroadcastReceiver.EXPLICIT_ACTION)
//        intent.setPackage("com.example.broadcastrecieverdemo")
//        sendBroadcast(intent)

        //Also if we want to send this broadcast to all apps registered with this action then
        val intent = Intent(ExplicitBroadcastReceiver.EXPLICIT_ACTION)
        val infos = packageManager.queryBroadcastReceivers(intent,0)
        for (info in infos){
            val componentName = ComponentName(info.activityInfo.packageName,info.activityInfo.name)
            intent.component = componentName
            sendBroadcast(intent)
        }



    }

    fun sendOrderedBroadCast(view: View) {
        val intent = Intent(ORDERED_ACTION)
        //Making explicit so that the reciever registered in manifest can also recieve the broadcast
        intent.setPackage("com.example.broadcastrecieverdemo")
        //ResultOrderedBroadcastReceiver used here is the optional final broadcast that will be triggered after all completed
        //the handler paramaeter can be used if we want to run the broadcast on thread other than main UI
        //initial code, initial data and initial extras are fields that can be propagated and manipulated along the broadcast it traverses and can be used in any way to  be interptreted
        val extras = Bundle()
        extras.putString("stringExtra","Start")
        //Also set permission that the reciever app has to have to recieve broadcast
        sendOrderedBroadcast(intent,Manifest.permission.WAKE_LOCK,ResultOrderedBroadcastReceiver(),null,0,"Start",extras)
    }

    fun sendLocalBroadCast(view: View) {
        val intent = Intent(CUSTOM_ACTION)
        localBroadcastManager.sendBroadcast(intent)
    }
}