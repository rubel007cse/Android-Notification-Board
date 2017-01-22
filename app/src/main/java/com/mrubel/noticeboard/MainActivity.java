package com.mrubel.noticeboard;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bs = (Button) findViewById(R.id.myB);

        startService(new Intent(getBaseContext(), CheckNotification.class));


        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = isServiceRunning(CheckNotification.class.getName());
                Toast.makeText(getApplicationContext(), b+" --", Toast.LENGTH_LONG).show();
            }
        });


    }


   /*
           Notification notification=new    Notification.Builder(MainActivity.this)
                   .setContentTitle(" Title")
                   .setContentText(" Description")
                   .setSmallIcon(R.mipmap.ic_launcher)
                   .build();
           NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
           notification.flags |=Notification.FLAG_AUTO_CANCEL;
           notificationManager.notify(1,notification);

   */




    public boolean isServiceRunning(String serviceClassName){

         ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
    }

}
