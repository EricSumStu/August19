package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Report extends AppCompatActivity {

    public static final String FILE_NAME = "new.txt" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        String spinnerValue = mySpinner.getSelectedItem().toString();
        int spinnerValueInt = Integer.parseInt(spinnerValue);
        readDataFromFile(spinnerValueInt);
    }

    public void reDrawTiles(View v){
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        String spinnerValue = mySpinner.getSelectedItem().toString();
        int spinnerValueInt = Integer.parseInt(spinnerValue);
        readDataFromFile(spinnerValueInt);

    }

    private void readDataFromFile(int counter) {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.removeAllViews();
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            int innerCounter = 0;
            ArrayList<Data> dataArrayList = new ArrayList<>();


            while ((text = br.readLine()) != null /*&& innerCounter < counter*/) {
                final TextView rowTextView = new TextView(this);
                rowTextView.setTextSize(20);
                String[] textArray = text.split(" ");
                String date = textArray[0];
                String working = textArray[1];
                String lunch = textArray[2];
                String breakTime = textArray[3];
                Data dataObj = new Data(working, lunch, breakTime, date);
                dataArrayList.add(dataObj);




            System.out.println(sb);

            sb.append(text).append("\n");
            /*innerCounter++*/;
        }
            for(int i =dataArrayList.size()-1; dataArrayList.size()>=counter ? i >=dataArrayList.size()-counter:i>=0; i--){
                final TextView rowTextView = new TextView(this);
                rowTextView.setTextSize(20);
                String workingHours = dataArrayList.get(i).getWorkingHours();
                String breakHours = dataArrayList.get(i).getBreakHours();
                String lunchHours = dataArrayList.get(i).getLunchHours();
                String date = dataArrayList.get(i).getDate();
                rowTextView.setText(Html.fromHtml("<u>Date: </u>" + date + "<br> <u>Working hours: </u>" + workingHours +  "<br> <u>Time spent on lunch: </u>" + lunchHours + "<br> <u>Time spent on breaks: </u>" + breakHours + "<br>_____________________________<br>"));
                linearLayout.addView(rowTextView);
            }
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


    /*public void load() {
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
*//*             textView = findViewById(R.id.reading);
            textView.setText(sb.toString());*//*

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
    }*/


    public void goToMainActivity (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
