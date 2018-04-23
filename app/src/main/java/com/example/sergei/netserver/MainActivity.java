package com.example.sergei.netserver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.*;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
        String word;
    String service =
            "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20180416T135101Z.9f3038a4301aa43c.6800c98279349eba128f4e8647d452a970b25277&lang=en-ru&text=";
        TextView textView;
        EditText editText;
        Button button;
    URL url;
    HttpURLConnection connection;
    Scanner scanner;
    String res;
    String r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);


        //new myNetTask().execute();
       // word="дверь";


    }

    public void getText(View view){

        word = editText.getText().toString();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try
                { url = new URL(service + word);
                }
                catch (Exception e)
                {
                }
                try {
                    connection = (HttpURLConnection) url.openConnection();
                }
                catch (Exception e) {
                }
                try {
                    scanner = new Scanner(connection.getInputStream());
                } catch (Exception e) {
                }
                res = "";
                while (scanner.hasNextLine())
                {
                    res += scanner.nextLine();
                    //Log.d("Help",res);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Help",res);
                        try {
                            JSONObject article = new JSONObject(res);
                            JSONArray def=article.getJSONArray("def");
                           r = ((JSONObject)(def.get(0))).getString("text");
// r = (JSONObject)(((JSONObject)(def.get(0)))).get(0).getString("text");
                        }
                        catch (Exception e){}

                                textView.setText(r);

                    }
                });
            }

        }.start();


    }
    class myNetTask extends AsyncTask<Void,Void,String> {
        String res = null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast toast = Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

//            try {
//
//                URLConnection urlConnection = new URLConnection() {
//                    @Override
//                    public void connect() throws IOException {
//
//                    }
//                };
//                Socket socket = new Socket("172.30.101.140", 1234);
//                PrintStream out = new PrintStream(socket.getOutputStream());
//                Scanner in = new Scanner(socket.getInputStream());
//                out.println("GET / HTTP/1.0\n");
//
//                while (in.hasNextLine()) {
//                    res = "";
//                    res += in.nextLine();
//                }
//                return res;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
         return null;
        }
    }
}
