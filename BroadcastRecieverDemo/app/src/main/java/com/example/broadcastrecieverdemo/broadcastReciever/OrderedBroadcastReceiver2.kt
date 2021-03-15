package com.example.broadcastrecieverdemo.broadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import android.widget.Toast
import com.example.broadcastrecieverdemo.MainActivity


//We will experiment with goAsync() here,
//Basically if we want to run long running task in Broadcast reciever then we can not do directly on onReceive since it runs on UI thread and may freeze the UI
//So the ways we can do is to start IntentService or JobScheduler or more easier start a thread in onReceive
//But starting a thread has a problem, as once the control comes out of onReceive and if app is not in foreground then the the spawned thread will also be terminated.
//Till control is in onReceive, the thread will work as onRecieve makes Broadcast to be run on priority and app is not killed.
//Hence we need to somehow make the onReceive to wait and the answer is goAsync()
//goAsync returns PendingResult which when called with finish() only then will terminate onReceive
//Pending can  be till 10secs if called with FOREGROUND_SERVICE flag in intent or 30 secs normally.

class OrderedBroadcastReceiver2 : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //Need handler to run the Toast on main thread
        val handler = Handler()
        val pendingResult = goAsync()
        Thread(Runnable {
            //Making the thread sleep for 10secs
            SystemClock.sleep(10000)

            //Now all results has to called using PendingResult
            var resultData = pendingResult.resultData
            var resultCode = pendingResult.resultCode
            var resultExtras = pendingResult.getResultExtras(true)
            var stringExtra = resultExtras.getString("stringExtra")

            resultCode++
            resultData = "OR2"
            stringExtra += "->OR2"

            val stringToast = "OR2\n" +
                    "resultCode" + resultCode + "\n" +
                    "resultData" + resultData + "\n" +
                    "stringExtra" + stringExtra
            handler.post(Runnable {
                Toast.makeText(context,stringToast,Toast.LENGTH_SHORT).show()
            })
            resultExtras.putString("stringExtra",stringExtra)

            //setResult sets the result for the next receiver to recieve acc to priority set
            pendingResult.setResult(resultCode,resultData,resultExtras)
            pendingResult.finish()
            //Aborts the broadcast to propagate further
//        abortBroadcast()
        }).start()


    }
}