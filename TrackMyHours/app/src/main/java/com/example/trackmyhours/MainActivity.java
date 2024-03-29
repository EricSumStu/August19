package com.example.trackmyhours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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


    public static final String FILE_NAME = "new.txt";
    private Chronometer lunchChronometer;
    private Chronometer chronometer;
    private Chronometer breakChronometer;
    private long pauseOffset;
    private long lunchPauseOffset;
    private long breakPauseOffset;
    private Button mButtonStart;
    private Button mBreakButton;
    private boolean running;
    private boolean lunchRunning;
    private boolean breakRunning;
    private Button mLunchButton;
    private TextView lunchDescriptor;
    private TextView breakDescriptor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        lunchChronometer = findViewById(R.id.lunchChronometer);
        lunchChronometer.setFormat("%s");
        lunchChronometer.setBase(SystemClock.elapsedRealtime());
        breakChronometer = findViewById(R.id.breakChronometer);
        breakChronometer.setFormat("%s");
        breakChronometer.setBase(SystemClock.elapsedRealtime());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



    }


    public void startStopChronometer(final View v) {
        mButtonStart = findViewById(R.id.startButton);
        mLunchButton = findViewById(R.id.resetButton);
        mBreakButton = findViewById(R.id.breakButton);
        lunchDescriptor = findViewById(R.id.lunchChronometer_text_description);
        breakDescriptor = findViewById(R.id.breakChronometer_text_description);
        if (lunchRunning) {
            Toast.makeText(MainActivity.this, "Please finish lunch before finishing work",
                    Toast.LENGTH_LONG).show();

        }
        else if (breakRunning) {
            Toast.makeText(MainActivity.this, "Please finish your break before finishing work",
                    Toast.LENGTH_LONG).show();
        }
        else if (!running) {
            startMainChronometer(v);
            mButtonStart.setText("Finish Work and Reset");
            mLunchButton.setVisibility(View.VISIBLE);
            mBreakButton.setVisibility(View.VISIBLE);
            otherTimersAppear();
        } else {

            AlertDialog.Builder  builder = new AlertDialog.Builder(this)
                    .setTitle("Finish Work")
                    .setMessage("Are you sure you want to finish work for the day and reset the timer?")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           resetAndSaveOutput(v);

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null);

            AlertDialog areYouSure = builder.create();
            areYouSure.show();
                }





        }


    public void resetAndSaveOutput(View v) {
        resetAllChronometers(v);
        mButtonStart.setText("Start Working Time");
        mLunchButton.setVisibility(View.INVISIBLE);
        mBreakButton.setVisibility(View.INVISIBLE);
        otherTimersDisappear();
    }


    public void resetAllChronometers(View v) {
        String time = showElapsedTime();
        String lunchTime = calculateLunchTime();
        String breakTime = calculateBreakTime();
        resetMainChronometer(v);
        resetLunchChronometer(v);
        resetBreakChronometer(v);
        updateButtons();
        save(v, time, lunchTime, breakTime);
    }

    public void lunchButtonHandler(View v) {
        if (breakRunning){
            Toast.makeText(MainActivity.this, "Please finish your break before starting lunch",
                    Toast.LENGTH_LONG).show();
        }
        else if (!lunchRunning) {
            pauseMainChronometer(v);
            startLunchChronometer(v);
            mLunchButton.setText("Finish Lunch");
        } else {
            pauseLunchChronometer(v);
            startMainChronometer(v);
            mLunchButton.setText("Start Lunch");
        }


    }

    public void breakButtonHandler(View v) {
        if (lunchRunning) {
            Toast.makeText(MainActivity.this, "Please finish lunch before starting a break",
                    Toast.LENGTH_LONG).show();
        }
        else if (!breakRunning) {
            pauseMainChronometer(v);
            startBreakChronometer(v);
            mBreakButton.setText("Finish Break");

        }
        else {
            pauseBreakChronometer(v);
            startMainChronometer(v);
            mBreakButton.setText("Start Break");
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

    public void pauseLunchChronometer(View v) {
        lunchChronometer.stop();
        lunchPauseOffset = SystemClock.elapsedRealtime() - lunchChronometer.getBase();
        lunchRunning = false;
    }

    public void resetLunchChronometer(View v) {
        lunchChronometer.setBase(SystemClock.elapsedRealtime());
        lunchPauseOffset = 0;

        lunchChronometer.stop();
        lunchRunning = false;
    }

    public void startBreakChronometer(View v) {
        breakChronometer.setBase(SystemClock.elapsedRealtime() - breakPauseOffset);
        breakChronometer.start();
        breakRunning = true;
    }

    public void pauseBreakChronometer(View v) {
        breakChronometer.stop();
        breakPauseOffset = SystemClock.elapsedRealtime() - breakChronometer.getBase();
        breakRunning = false;
    }

    public void resetBreakChronometer(View v) {
        breakChronometer.setBase(SystemClock.elapsedRealtime());
        breakPauseOffset = 0;

        breakChronometer.stop();
        breakRunning = false;
    }

    private void updateButtons() {
        if (running) {

            mButtonStart.setText("Finish Work and Reset");
        } else {
            mButtonStart.setText("Start Working Time");

        }

    }

    public String showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h = (int) (elapsedMillis / 3600000);
        int m = (int) (elapsedMillis - h * 3600000) / 60000;
        int s = (int) (elapsedMillis - (h * 3600000) - (m * 60000)) / 1000;
        String totalWorkingHours = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        Toast.makeText(MainActivity.this, "Total Time Worked: " + totalWorkingHours,
                Toast.LENGTH_LONG).show();
        return totalWorkingHours;
    }

    public String calculateLunchTime() {
        lunchChronometer.setBase(SystemClock.elapsedRealtime() - lunchPauseOffset);
        long lunchElapsedMillis = SystemClock.elapsedRealtime() - lunchChronometer.getBase();
        int hLunch = (int) (lunchElapsedMillis / 3600000);
        int mLunch = (int) (lunchElapsedMillis - hLunch * 3600000) / 60000;
        int sLunch = (int) (lunchElapsedMillis - (hLunch * 3600000) - (mLunch * 60000)) / 1000;
        String totalLunchHours = (hLunch < 10 ? "0" + hLunch : hLunch) + ":" + (mLunch < 10 ? "0" + mLunch : mLunch) + ":" + (sLunch < 10 ? "0" + sLunch : sLunch);
        return totalLunchHours;
    }
    public String calculateBreakTime() {
        breakChronometer.setBase(SystemClock.elapsedRealtime() - breakPauseOffset);
        long breakElapsedMillis = SystemClock.elapsedRealtime() - breakChronometer.getBase();
        int hBreak = (int) (breakElapsedMillis / 3600000);
        int mBreak = (int) (breakElapsedMillis - hBreak * 3600000) / 60000;
        int sBreak = (int) (breakElapsedMillis - (hBreak * 3600000) - (mBreak * 60000)) / 1000;
        String totalBreakHours = (hBreak < 10 ? "0" + hBreak : hBreak) + ":" + (mBreak < 10 ? "0" + mBreak : mBreak) + ":" + (sBreak < 10 ? "0" + sBreak : sBreak);
        return totalBreakHours;
    }

    public void save(View v, String elapsedTime, String elapsedLunchTime, String elapsedBreakTime) {


        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(getFilesDir() + "/" + "new.txt"), true));
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String row = date + " " + elapsedTime + " " + elapsedLunchTime + " " + elapsedBreakTime;
            writer.println(row);
            writer.close();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + MainActivity.FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
    }


    public void goToReport(View view) {
        if (running) {
            Toast.makeText(MainActivity.this, "Please finish work before viewing stats",
                    Toast.LENGTH_LONG).show();
        } else if (lunchRunning) {
            Toast.makeText(MainActivity.this, "Please finish work before viewing stats",
                    Toast.LENGTH_LONG).show();
        } else if (breakRunning) {
            Toast.makeText(MainActivity.this, "Please finish work before viewing stats",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Report.class);
            startActivity(intent);

        }
    }

    public void otherTimersAppear() {
        lunchDescriptor.setVisibility(View.VISIBLE);
        breakDescriptor.setVisibility(View.VISIBLE);
        lunchChronometer.setVisibility(View.VISIBLE);
        breakChronometer.setVisibility(View.VISIBLE);
    }
    public void otherTimersDisappear() {
        lunchDescriptor.setVisibility(View.INVISIBLE);
        breakDescriptor.setVisibility(View.INVISIBLE);
        lunchChronometer.setVisibility(View.INVISIBLE);
        breakChronometer.setVisibility(View.INVISIBLE);
    }



}



