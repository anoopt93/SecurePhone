package com.example.sofsis.securephone;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {

    Button BtnNumber,BtnPassword,BtLocation;
    String Sim_Card_Number;
    TextView Sim;
    private BroadcastReceiver mIntentReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//########################################################################################################
        // READ SIM CARD SERIAL NUMBER & SAVED IN SHARED PREFERENCE

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getSimSerialNumber();
        String simID = telephonyManager.getSimSerialNumber();

       // Toast.makeText(MainActivity.this,simID, Toast.LENGTH_SHORT).show();

        SharedPreferences pre = getSharedPreferences("SIM", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString("ID", simID);
        edit.commit();

        Sim=(TextView)findViewById(R.id.sim);
        Sim_Card_Number=pre.getString("ID","");
        Sim.setText(Sim_Card_Number);
//########################################################################################################
        // SAVED AS ACTIVATED
        SharedPreferences prefs = getSharedPreferences("Activation", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Activated", "1");
        editor.commit();

        checkAndRequestPermissions();
        BtnNumber=(Button)findViewById(R.id.setnumber);
        BtnPassword=(Button)findViewById(R.id.setpassword);
        BtLocation =(Button)findViewById(R.id.location);

        BtnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,SetNumbers.class);
                startActivity(i);

            }
        });

        BtnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,SetPassword.class);
                startActivity(i);
            }
        });

        BtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Location.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");

                msg = msg.replace("\n", "");
                String body = msg.substring(msg.lastIndexOf(":")+1, msg.length());
                String pNumber = msg.substring(0,msg.lastIndexOf(":"));


                Toast.makeText(MainActivity.this,body, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();

                System.out.println(body);
                System.out.println(pNumber);


            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);


    }
    @Override
    protected void onPause() {

        super.onPause();
        this.unregisterReceiver(this.mIntentReceiver);
    }


    private  boolean checkAndRequestPermissions()
    {

        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (sms != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
           // ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new
             //       String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


}

