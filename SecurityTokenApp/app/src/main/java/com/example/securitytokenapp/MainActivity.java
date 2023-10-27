package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Calendar cal;
    private int current_minute;
    private int current_second;
    private int passcode;
    private TextView textview_passcode;
    private TextView textview_countdown;
    private CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview_passcode = findViewById(R.id.textView_passcode);
        textview_countdown = findViewById(R.id.textView_countdown);

        renew_passcode();

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

    private void renew_passcode () {
        cal = Calendar.getInstance();

        current_minute = cal.get(Calendar.MINUTE);
        passcode = current_minute * 1245 + 10000;

        textview_passcode.setText(String.valueOf(passcode));
    }
}