package com.example.broadcastrecieverdemo.broadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class OrderedBroadcastReceiver1 : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //Intent Extras can not be propagated to other broadcast but, resultExtras can
        var resultData = resultData
        var resultCode = resultCode
        var resultExtras = getResultExtras(true)
        var stringExtra = resultExtras.getString("stringExtra")

        resultCode++
        resultData = "OR1"
        stringExtra += "->OR1"

        val stringToast = "OR1\n" +
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