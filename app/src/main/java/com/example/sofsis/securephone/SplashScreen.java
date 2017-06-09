package com.example.sofsis.securephone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    String IMEI;
    int SoftwareID;

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences("Activation", MODE_PRIVATE);
        final String string = prefs.getString("Activated", "");

        boolean firstRun = getSharedPreferences("preferences", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstRun) {

           // if (AppStatus.getInstance(this).isOnline()) {
                //set the firstrun to false so the next run can see it.
                getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit();

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                IMEI = telephonyManager.getDeviceId();
                Random rnd = new Random();
                SoftwareID = 100000 + rnd.nextInt(900000);

                SharedPreferences pref = getSharedPreferences("SaveData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("IMEI", IMEI);
                editor.putString("SoftwareID", String.valueOf(SoftwareID));
                editor.commit();

                new SaveDetails(this).execute(IMEI, String.valueOf(SoftwareID));

           // SharedPreferences pre = getSharedPreferences("Register", MODE_PRIVATE);
            //final String stringReg = pre.getString("Registered", "");

            //if(stringReg.equals("1")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                }
            }, SPLASH_TIME_OUT);
            //}


            //} else {
                //getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstrun", true).commit();
                //Toast.makeText(this, "You are not online!!!!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Connect to internet and Try again.", Toast.LENGTH_SHORT).show();
              //  SplashScreen.this.finish();
            //}
        } else {
            Toast.makeText(getApplicationContext(), "Loading....", Toast.LENGTH_SHORT).show();
            // }

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    if (string.equals("1")) {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashScreen.this, Home.class);
                        startActivity(i);
                    }
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}

