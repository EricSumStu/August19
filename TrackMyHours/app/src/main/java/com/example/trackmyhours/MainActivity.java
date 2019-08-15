package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {


    public static final String FILE_NAME = "example.txt" ;
    private Chronometer lunchChronometer;
    private Chronometer chronometer;
    private long pauseOffset;
    private long lunchPauseOffset;
    private Button mButtonStart;
    private boolean running;
    private boolean lunchRunning;
    private Button mButtonReset;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        lunchChronometer = findViewById(R.id.lunchChronometer);
        lunchChronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }





    public void startStopChronometer(View v) {
        mButtonStart = findViewById(R.id.startButton);
        mButtonReset = findViewById(R.id.resetButton);
        if (!running) {
//
/*            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            load(v);
            updateButtons();*/
            startMainChronometer(v);
            updateButtons();
        }

        else {
   /*         String time = showElapsedTime();*/

/*            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;*/
/*            resetMainChronometer(v);*/
            resetAllChronometers(v);
            updateButtons();
/*            save(v, time );*/
        }

    }
    public void resetAllChronometers(View v) {
        String time = showElapsedTime();/*
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        chronometer.stop();
        running = false;*/
        resetMainChronometer(v);
        resetLunchChronometer(v);
        updateButtons();
        mButtonReset.setVisibility(View.INVISIBLE);
        save(v, time );
    }

    public void lunchButtonHandler(View v) {
        if (!lunchRunning) {
            pauseMainChronometer(v);
            startLunchChronometer(v);

        }
        else{
            pauseLunchChronometer(v);
            startMainChronometer(v);
        }

    }
    public void startMainChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
        running = true;
    }

    public void pauseMainChronometer(View v) {
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        running = false;
    }

    public void resetMainChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        chronometer.stop();
        running = false;
    }

    public void startLunchChronometer(View v) {
        lunchChronometer.setBase(SystemClock.elapsedRealtime() - lunchPauseOffset);
        lunchChronometer.start();
        lunchRunning = true;
    }

    public void pauseLunchChronometer (View v) {
        lunchChronometer.stop();
        lunchPauseOffset = SystemClock.elapsedRealtime() - lunchChronometer.getBase();
        lunchRunning = false;
    }

    public void resetLunchChronometer (View v) {
        lunchChronometer.setBase(SystemClock.elapsedRealtime());
        lunchPauseOffset = 0;

        lunchChronometer.stop();
        lunchRunning = false;
    }
    private void updateButtons(){
        if (running){
            mButtonReset.setVisibility(View.VISIBLE);
            mButtonStart.setText("Finish Work and Reset");
        }
        else {
            mButtonStart.setText("Start Working Time");
            mButtonReset.setVisibility(View.VISIBLE);
        }

    }
    public String showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h = (int) (elapsedMillis/ 3600000);
        int m = (int) (elapsedMillis - h * 3600000) / 60000;
        int s = (int) (elapsedMillis - (h * 3600000 ) - (m * 60000)) / 1000;
        String totalWorkingHours = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
        Toast.makeText(MainActivity.this, "Total Time Worked: " + totalWorkingHours,
                Toast.LENGTH_LONG).show();
     return totalWorkingHours;
    }
    public void save(View v, String elapsedTime) {


        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(getFilesDir() + "/" + "example.txt"), true));
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String row = "Day " + date + " : [Working Hours: " + elapsedTime + "] " ;
            writer.println(row);
            writer.close();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + MainActivity.FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
    }


    public void load(View v) {
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




            public void goToReport (View view){
        Intent intent = new Intent (this, Report.class);
        startActivity(intent);
    }


}