package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Report extends AppCompatActivity {

    public static final String FILE_NAME = "new.txt" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                final TextView rowTextView = new TextView(this);
                rowTextView.setTextSize(20);
                String[] textArray = text.split(" ");
                String date = textArray[0];
                String working = textArray[1];

                rowTextView.setText(Html.fromHtml("<u>Date: </u>" + date + "<br> <u>Working hours: </u>" + working + "<br> _____________________________<br>"));
                linearLayout.addView(rowTextView);

                sb.append(text).append("\n");
            }


            System.out.println(sb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            System.out.println(sb);
/*             textView = findViewById(R.id.reading);
            textView.setText(sb.toString());*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void goToMainActivity (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
