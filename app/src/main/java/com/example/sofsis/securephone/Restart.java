package com.example.sofsis.securephone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by SOFSIS on 03/14/2017.
 */

public class Restart extends BroadcastReceiver {

    GPSTracker gps;
    double latitude,longitude;
    String NUMBER1,NUMBER2,NUMBER3;

    @Override
    public void onReceive(Context context, Intent inte) {

//##################################################################################################
        // READ NUMBERS AND PASSWORD FROM SHARED PREFERENCE.........................................

        SharedPreferences prefs = context.getSharedPreferences("Numbers", Context.MODE_PRIVATE);
        final String number1 = prefs.getString("MobNumber1", "");
        final String number2 = prefs.getString("MobNumber2", "");
        final String number3 = prefs.getString("MobNumber3", "");

        if (!number1.equals(null)){
            NUMBER1= "+91"+number1;
        }
        if (!number2.equals(null)){
            NUMBER2= "+91"+number2;
        }
        if (!number3.equals(null)){
            NUMBER3= "+91"+number3;
        }
//##################################################################################################
        // READ OLD SIM SERIAL NUMBER FROM SHARED PREFERENCE........................................

        SharedPreferences pref = context.getSharedPreferences("SIM", Context.MODE_PRIVATE);
        final String OldSimID = pref.getString("ID","");

//##################################################################################################
        // CHECK SIM SERIAL NUMBER ON RESTART................................

        Toast.makeText(context,"System Restarted", Toast.LENGTH_LONG).show();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getSimSerialNumber();
        String newSimID = telephonyManager.getSimSerialNumber();
       // Toast.makeText(context,"New Sim : "+newSimID, Toast.LENGTH_LONG).show();

//#################################################################################################
        // GET LOCATION FROM GPSTracker Class........

        gps = new GPSTracker(context);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(context, "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

//##################################################################################################
        // CHECK IF SIM SERIAL NUMBERS ARE SAME. ELSE SEND SMS TO ALL THREE NUMBERS.................

        if(!OldSimID.equals(newSimID)){

            String link = "http://maps.google.com/maps?q=loc:" + String.format("%f,%f", latitude, longitude);
            if(!NUMBER1.equals(null)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NUMBER1,null,"SIM CARD CHANGED. LOCATION : "+link,null,null);
            }
            if(!NUMBER2.equals(null)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NUMBER2,null,"SIM CARD CHANGED. LOCATION : "+link,null,null);
            }
            if(!NUMBER3.equals(null)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NUMBER3,null,"SIM CARD CHANGED. LOCATION : "+link,null,null);
            }

        }

    }
}
