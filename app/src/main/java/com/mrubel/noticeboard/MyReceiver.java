package com.mrubel.noticeboard;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by mosharrofrubel on 1/22/17.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, CheckNotification.class);
        context.startService(myIntent);

    }
}