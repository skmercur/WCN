package com.sk.wcnwebchangesnotifier;

import android.content.Context;
import android.os.Environment;
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
import java.io.OutputStreamWriter;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    doc[0] = Jsoup.connect("http://www.univ-bba.dz").get();
                    String data = doc[0].body().toString();

                    Builder.append(data).append("\n");
                    File folder = new File(Environment.getExternalStorageDirectory() + "/wcn");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();

                    } else {
                        Toast.makeText(getBaseContext(),
                                "Error while creating the folder",
                                Toast.LENGTH_SHORT).show();
                    }

                Toast.makeText(getBaseContext(),
                        "Done writing SD 'data.txt'",
                        Toast.LENGTH_SHORT).show();
                }catch (Exception e){
Builder.append("Errure : ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
TextView tet = (TextView)findViewById(R.id.textView);
                        try{
                        File myFile = new File("/sdcard/wcn/data.txt");
                        myFile.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter =
                                new OutputStreamWriter(fOut);
                        myOutWriter.append(Builder);
                        myOutWriter.close();
                        fOut.close();
                        tet.setText("Done !");}catch (Exception e){
                            Toast.makeText(getBaseContext(), e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}
