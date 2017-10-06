package com.sk.wcnwebchangesnotifier;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText urlBar = (EditText) findViewById(R.id.editText2);
       String urls =  urlBar.getText().toString();
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });


    }

    private void getWebsite(){
        final Document[] doc = {null};
        final StringBuilder Builder = new StringBuilder();
        final FileOutputStream[] outputStream = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    doc[0] = Jsoup.connect("http://www.univ-bba.dz").get();
                    String data = doc[0].body().toString();

                    Builder.append(data).append("\n");
                    outputStream[0] = openFileOutput("helloo",Context.MODE_PRIVATE);
                    outputStream[0].write(Builder.toString().getBytes());
                    outputStream[0].close();
                }catch (Exception e){
Builder.append("Errure : ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
TextView tet = (TextView)findViewById(R.id.textView);
                        tet.setText("Done !");
                    }
                });
            }
        }).start();

    }
}
