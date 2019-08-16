package com.example.trackmyhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Report extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final int N = 10; // total number of textviews to add

        final TextView[] myTextViews = new TextView[N]; // create an empty array;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        for (int i = 0; i < N; i++) {
            // create a new textview
            final TextView rowTextView = new TextView(this);

            // set some properties of rowTextView or something
            rowTextView.setTextSize(20);

            // rowTextView.setText("Date: 3rd Aug\n" + "Working hours: " + "00:00:00\n\n");

            rowTextView.setText(Html.fromHtml("<u>Date:</u> 15Aug <br> <u>Working hours:</u> 00:00:00<br> <u>Lunch:</u> 00:00:00 <br> <u>Other breaks:</u> 00:00:00 <br>___________________________________<br> "));

            // add the textview to the linearlayout
            linearLayout.addView(rowTextView);

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }
    }

    public void goToMainActivity (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
