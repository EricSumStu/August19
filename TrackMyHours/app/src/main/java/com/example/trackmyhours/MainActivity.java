package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;



public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private Button mButtonStart;
    private Button mButtonReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());


    }



    public void startStopChronometer(View v) {
        mButtonStart = findViewById(R.id.startButton);
        mButtonReset = findViewById(R.id.resetButton);
        if (!running) {
           mButtonStart.setText("Stop Timer");
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            mButtonReset.setVisibility(View.VISIBLE);

        }
        //Stop button
        else {
           mButtonStart.setText("Start Timer");

            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            mButtonReset.setVisibility(View.INVISIBLE);

        }

    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        mButtonReset.setVisibility(View.INVISIBLE);
        chronometer.stop();
        running = false;
        mButtonStart.setText("Start Timer");


    }
}








