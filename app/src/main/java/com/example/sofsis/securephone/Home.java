package com.example.sofsis.securephone;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView TxtImei,TxtSoftwareId,Reg,Contact,Imei,Softid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        TxtImei=(TextView)findViewById(R.id.txtimei);
        TxtSoftwareId=(TextView)findViewById(R.id.txtsoftID);
        Reg=(TextView)findViewById(R.id.regfail);
        Contact=(TextView)findViewById(R.id.contact);
        Imei=(TextView)findViewById(R.id.imei);
        Softid=(TextView)findViewById(R.id.softid);


        SharedPreferences pref = getSharedPreferences("Register", MODE_PRIVATE);
        final String stringReg = pref.getString("Registered","");
        if(!stringReg.equals("1")){

            getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstrun", true).commit();
            TxtImei.setText(null);
            TxtSoftwareId.setText(null);
            Contact.setText(null);
            Imei.setText(null);
            Softid.setText(null);
            Reg.setText("Registration Failed Check your internet and try again");


            SharedPreferences prefs = getSharedPreferences("SaveData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("IMEI","");
            editor.putString("SoftwareID","");
            editor.commit();
        }
        else {

        SharedPreferences prefs = getSharedPreferences("SaveData", MODE_PRIVATE);
        final String string1 = prefs.getString("IMEI", "");
        final String string2 = prefs.getString("SoftwareID", "");



        TxtImei.setText(string1);
        TxtSoftwareId.setText(string2);

        new CheckStatus(this).execute(string1, string2);
        }
    }
}
