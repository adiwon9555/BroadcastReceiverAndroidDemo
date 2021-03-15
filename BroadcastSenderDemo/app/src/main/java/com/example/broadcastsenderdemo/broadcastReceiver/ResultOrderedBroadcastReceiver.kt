package com.example.broadcastsenderdemo.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ResultOrderedBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //Intent Extras can not be propagated to other broadcast but, resultExtras can
        var resultData = resultData
        var resultCode = resultCode
        var resultExtras = getResultExtras(true)
        var stringExtra = resultExtras.getString("stringExtra")

        resultCode++
        resultData = "ResultBroadcast"
        stringExtra += "->ResultBroadcast"

        val stringToast = "ResultBroadcast\n" +
                "resultCode" + resultCode + "\n" +
                "resultData" + resultData + "\n" +
                "stringExtra" + stringExtra

        Toast.makeText(context,stringToast,Toast.LENGTH_SHORT).show()
        resultExtras.putString("stringExtra",stringExtra)

        //setResult sets the result for the next receiver to recieve acc to priority set
        setResult(resultCode,resultData,resultExtras)

        //Aborts the broadcast to propagate further
//        abortBroadcast()
    }
}