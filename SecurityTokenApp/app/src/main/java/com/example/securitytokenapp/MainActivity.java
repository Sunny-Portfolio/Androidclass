package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
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
    private BroadcastReceiver minuteChangedReceiver;
    // Broadcast intent action
    public static final String PASSCODE_UPDATE_ACTION = "com.example.securitytokenapp.passcode_update";




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

        passcode = renew_passcode();
        textview_passcode.setText(String.valueOf(passcode));



        /**
         * This broadcast receiver receive ACTION_TIME_TICK which is sent when the minutes changesd
         * After that, the passcode is changed and synced
         */
        minuteChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    // Update the passcode when the minutes change
                    int passcode = renew_passcode();
                    textview_passcode.setText(String.valueOf(passcode));

                    // Start the 60 seconds timer
                    timer.start();

                    // Broadcast the passcode to Activity2
                    Intent passcodeIntent = new Intent(PASSCODE_UPDATE_ACTION);
                    passcodeIntent.putExtra("passcode", passcode);
                    sendBroadcast(passcodeIntent);
                }
            }
        };

        // Register the broadcast receiver for ACTION_TIME_TICK
        registerReceiver(minuteChangedReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));



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
//                int passcode = renew_passcode();
//                textview_passcode.setText(String.valueOf(passcode));
//
//                // TODO: 10/28/23 send braoadcast this way maybe? check gpt
//                // maybe use ACTION_TIME_TICK instead of 60 secs countdown
//                // because it suppose to sync with system instead my own countdown
//                // Broadcast an intent
//                Intent intent = new Intent();
//                intent.setAction("")
//                intent.putExtra("passcode", passcode);
//                sendBroadcast(intent);
//
//                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
//
//
//                start();
            }
        };


        Log.d("TAG", "onCreate: minutes is = " + current_minute);

    }



    /**
     * Unregister the broadcast receiver when MainActivity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(minuteChangedReceiver);
    }

    /**
     * Method to update the passcode
     * Using the Java Calendar class instead of System.currentTimeMillis()
     * Both should work the same way
     */
    public int renew_passcode () {
        cal = Calendar.getInstance();

        current_minute = cal.get(Calendar.MINUTE);
        return current_minute * 1245 + 10000;

    }


}