package com.mrubel.noticeboard;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



    void fetchingData(){


        String myURL = "http://mrubel.com/tuntuninews/api/gettingnews.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                final String[] news_title = new String[response.length()];
                final String[] news_detail = new String[response.length()];
                final String[] news_time = new String[response.length()];

                for (int i =0; i < response.length(); i++){

                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        news_title[i] = jsonObject.getString("title");
                        news_detail[i] = jsonObject.getString("news");
                        news_time[i] = jsonObject.getString("time");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log", error);
            }
        });


        com.mrubel.noticeboard.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Toast.makeText(getApplicationContext(), "Data Loaded Successfully!", Toast.LENGTH_SHORT).show();

    }


}
