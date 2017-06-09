package com.example.sofsis.securephone;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetNumbers extends AppCompatActivity {

    EditText ETNumber1,ETNumber2,ETNumber3;
    Button btSave,btClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_numbers);

        ETNumber1=(EditText)findViewById(R.id.num1);
        ETNumber2=(EditText)findViewById(R.id.num2);
        ETNumber3=(EditText)findViewById(R.id.num3);

       // ETNumber1.setBackgroundResource(android.R.drawable.edit_text);
       // ETNumber2.setBackgroundResource(android.R.drawable.edit_text);
       // ETNumber3.setBackgroundResource(android.R.drawable.edit_text);

        btSave=(Button)findViewById(R.id.save);
        btClear=(Button)findViewById(R.id.clear);


        SharedPreferences prefs = getSharedPreferences("Numbers", MODE_PRIVATE);
        final String string1 = prefs.getString("MobNumber1", "");
        final String string2 = prefs.getString("MobNumber2", "");
        final String string3 = prefs.getString("MobNumber3", "");



        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ETNumber1.isFocused()){
                    ETNumber1.setText(null);
                    SharedPreferences prefs = getSharedPreferences("Numbers", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("MobNumber1", null);
                    Toast.makeText(SetNumbers.this, "Number1 Deleted", Toast.LENGTH_SHORT).show();

                }
                if (ETNumber2.isFocused()){
                    ETNumber2.setText(null);
                    SharedPreferences prefs = getSharedPreferences("Numbers", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("MobNumber1",null);
                    Toast.makeText(SetNumbers.this, "Number2 Deleted", Toast.LENGTH_SHORT).show();
                }
                if (ETNumber3.isFocused()) {
                    ETNumber3.setText(null);
                    SharedPreferences prefs = getSharedPreferences("Numbers", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("MobNumber1", null);
                    Toast.makeText(SetNumbers.this, "Number3 Deleted", Toast.LENGTH_SHORT).show();
                }

                //ETNumber1.setText(null);
                // ETNumber2.setText(null);
                // ETNumber3.setText(null);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Number1 = ETNumber1.getText().toString();
                String Number2 = ETNumber2.getText().toString();
                String Number3 = ETNumber3.getText().toString();

                SharedPreferences prefs = getSharedPreferences("Numbers", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("MobNumber1", Number1);
                editor.putString("MobNumber2", Number2);
                editor.putString("MobNumber3", Number3);
                editor.commit();
                //Toast.makeText(SetNumbers.this, "Numbers Saved ", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(SetNumbers.this);
                builder1.setIcon(R.drawable.aa);
                builder1.setTitle("Saved");
                builder1.setMessage("Numbers Saved Successfully");
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                                startActivity(getIntent());
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

              //  finish();
              //  startActivity(getIntent());

            }
        });
        ETNumber1.setText(string1);
       // ETNumber1.setTextColor(Color.RED);
        ETNumber2.setText(string2);
       // ETNumber2.setTextColor(Color.RED);
        ETNumber3.setText(string3);
       // ETNumber3.setTextColor(Color.RED);

    }
}
