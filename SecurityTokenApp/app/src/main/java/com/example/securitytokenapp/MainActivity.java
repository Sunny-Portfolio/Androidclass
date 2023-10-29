package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Calendar cal;
    private int current_minute;
    private int current_second;
    private int passcode;
    private TextView textview_passcode;
    private TextView textview_countdown;
    private Button button_verify;
    private CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Setup views and buttons
         */
        textview_passcode = findViewById(R.id.textView_passcode);
        textview_countdown = findViewById(R.id.textView_countdown);
        button_verify = findViewById(R.id.btn_verify);

        /**
         * On Click Listener for the verification button
         */
        button_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start verification activity via Explicit intent
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
        });


        renew_passcode();

        /**
         * Setup the timer and start counting
         * Count down 60000 milli secs (60 secs)
         */
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds_remain = (int) millisUntilFinished / 1000;
                textview_countdown.setText(seconds_remain + " seconds remaining");
            }

            @Override
            public void onFinish() {
                renew_passcode();
                start();
            }
        }.start();


        Log.d("TAG", "onCreate: minutes is = " + current_minute);

    }

    /**
     * Method to update the passcode
     * Using the Java Calendar class instead of System.currentTimeMillis()
     * Both should work the same way
     */
    public void renew_passcode () {
        cal = Calendar.getInstance();

        current_minute = cal.get(Calendar.MINUTE);
        passcode = current_minute * 1245 + 10000;

        textview_passcode.setText(String.valueOf(passcode));
    }


}