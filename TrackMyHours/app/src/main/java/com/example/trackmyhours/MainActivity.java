package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;



public class MainActivity extends AppCompatActivity {


    private Chronometer chronometer;
    private long pauseOffset;
    private Button mButtonStart;
    private boolean running;
    private Button mButtonReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }





    public void startStopChronometer(View v) {
        mButtonStart = findViewById(R.id.startButton);
        mButtonReset = findViewById(R.id.resetButton);
        if (!running) {
//
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
//
            updateButtons();

        }

        else {


            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

            updateButtons();
    }

    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        chronometer.stop();
        running = false;

        updateButtons();


    }


    private void updateButtons(){
    if (running){
        mButtonReset.setVisibility(View.VISIBLE);
        mButtonStart.setText("Pause Timer");
    }
    else {
        mButtonStart.setText("Start Timer");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    }

    }










