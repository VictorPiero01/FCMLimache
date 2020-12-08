package com.example.fcmlimache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button notify;
    String tkn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tkn = FirebaseInstanceId.getInstance().getToken();

        notify = (Button) findViewById(R.id.notify);

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Notify().execute();
            }
        });
    }

    public class  Notify extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            try{
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Autorization","key=AAAAUzYQLUU:APA91bHFb4f_UiqSAgSivHCs0qZyaWJ3N9wTnC7qUw-skWsexHwWdrAq7Mm77JrRcROW6_fLHx_9AhGfPZ12_wuTcUUccnJxkU54toIbNAAUZKo9FvCaA1oYq-4WPD3v--QxYkIYDTZz\t\n");
                conn.setRequestProperty("Context-Type","application/json");

                JSONObject json = new JSONObject();
                json.put("to",tkn);

                JSONObject info = new JSONObject();
                info.put("title","TechmoWeb");
                info.put("body","Hello Test notificacion");

                json.put("notification",info);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();


            }
            catch (Exception e){
                Log.d("Error",""+e);
            }



            return null;
        }
    }
}