package com.example.sofsis.securephone;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassword extends AppCompatActivity {
    EditText EtPassword;
    Button btSave,btClear,btShow;
    final int[] count = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        EtPassword = (EditText)findViewById(R.id.password);
        btSave = (Button)findViewById(R.id.save);
        btClear = (Button)findViewById(R.id.clear);
        btShow=(Button)findViewById(R.id.show);

        SharedPreferences prefs = getSharedPreferences("Password", MODE_PRIVATE);
        final String string = prefs.getString("pass", "");

        EtPassword.setText(string);


        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EtPassword.setText(null);
                SharedPreferences prefs = getSharedPreferences("Password", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("pass",null);
                editor.commit();
                Toast.makeText(SetPassword.this, "Password Deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  EtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if(count[0] ==0)
                {
                    EtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count[0]++;
                    btShow.setText("Hide");
                }
                else {

                    EtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btShow.setText("Show");
                    count[0]--;
                }


            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Password = EtPassword.getText().toString();

                SharedPreferences prefs = getSharedPreferences("Password", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("pass", Password);
                editor.commit();
               // Toast.makeText(SetPassword.this, "Password Saved ", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(SetPassword.this);
                builder1.setIcon(R.drawable.aa);
                builder1.setTitle("Saved");
                builder1.setMessage("Password Saved Successfully");
                builder1.setCancelable(true);
                builder1.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }
}
