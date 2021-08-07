package org.techtown.mission12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TextReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        sendToActivity(context, msg);
    }

    private void sendToActivity(Context context, String msg) {
        Intent myintent = new Intent(context, MainActivity.class);
        myintent.putExtra("msg", msg);
        myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("MyService", "리시버가 보낸다! : "+msg);
        context.startActivity(myintent);
    }
}