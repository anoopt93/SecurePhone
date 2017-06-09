package com.example.sofsis.securephone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import static android.R.id.message;

/**
 * Created by SOFSIS on 03/11/2017.
 */

public class SmsReceiver extends BroadcastReceiver {

    GPSTracker gps;
    double latitude;
    double longitude;
    String NUMBER1,NUMBER2,NUMBER3;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle =intent.getExtras();

//##################################################################################################
        // READ NUMBERS AND PASSWORD FROM SHARED PREFERENCE
        SharedPreferences prefs = context.getSharedPreferences("Numbers", Context.MODE_PRIVATE);
        final String number1 = prefs.getString("MobNumber1", "");
        final String number2 = prefs.getString("MobNumber2", "");
        final String number3 = prefs.getString("MobNumber3", "");
        SharedPreferences pref = context.getSharedPreferences("Password", Context.MODE_PRIVATE);
        final String password = pref.getString("pass", "");

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
        // GET LOCATION FROM GPSTracker Class........

        gps = new GPSTracker(context);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
           // Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }

//##################################################################################################
        // READ RECEIVED SMS MESSAGE AND PHONE NUMBER...................

        if(bundle !=null){
            Object[] pdus = (Object[])bundle.get("pdus");
            String sendNumber =null;
            for(int i=0; i<pdus.length; i++){
                SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[i]);

                sendNumber = sms.getDisplayOriginatingAddress();
                String message= sms.getDisplayMessageBody();

//##################################################################################################
                // REPLY FOR SMS WITH LOCATION COORDINATES................

                if(message.equals(password)){
                    if(sendNumber.equals(NUMBER1)|| sendNumber.equals(NUMBER2) || sendNumber.equals(NUMBER3)){
                        //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                        String link = "http://maps.google.com/maps?q=loc:" + String.format("%f,%f", latitude, longitude);
                         SmsManager smsManager = SmsManager.getDefault();
                         smsManager.sendTextMessage(sendNumber,null,link,null,null);
                    }
                }
            }
        }
    }
}
