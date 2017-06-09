package com.example.sofsis.securephone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by SOFSIS on 03/11/2017.
 */

public class CheckStatus extends AsyncTask<String, String, String> {

    private Context context;

    public CheckStatus(Context context) {
        this.context = context;
    }
    protected void onPreExecute(){
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            String IMEI = (String)arg0[0];
            String SoftwareID = (String)arg0[1];

            //String link="http://192.168.1.2/SecurePhone/checkStatus.php";
            String link="http://www.sofsisindia.com/care/checkStatus.php";
            String data  = URLEncoder.encode("IMEI", "UTF-8") + "=" +
                    URLEncoder.encode(IMEI, "UTF-8");
            data += "&" + URLEncoder.encode("SoftwareID", "UTF-8") + "=" +
                    URLEncoder.encode(SoftwareID, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            System.out.println(data);

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();


            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            System.out.println(sb);
            return sb.toString();
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }
    protected void onPostExecute(String result){

        if(result.equals("1")){
            System.out.println("Result : "+result);
            Toast.makeText(context,"App Activated by Admin !!! ",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);

        }
        else {
            System.out.println("Result : "+result);
        }
    }

}
