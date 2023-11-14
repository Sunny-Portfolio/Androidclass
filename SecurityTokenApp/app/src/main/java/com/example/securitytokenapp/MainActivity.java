package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Timer;

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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Calendar cal;
    private int current_minute;
    private int current_second;
    private int milliSec_remain;
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

        Log.d("TAG", "onCreate: In onCreate*** Start");

        /**
         * Setup views and buttons
         */
        textview_passcode = findViewById(R.id.textView_passcode);
        textview_countdown = findViewById(R.id.textView_countdown);
        button_verify = findViewById(R.id.btn_verify);





        /**
         * First update of the passcode on app startup
         */
        passcode = renew_passcode();
        textview_passcode.setText(String.valueOf(passcode));


        /**
         * Update the remaining secs on startup
         */
        cal = Calendar.getInstance();
        current_second = cal.get(Calendar.SECOND);
        milliSec_remain = (60 - current_second) * 1000;

        timer = new CountDownTimer(milliSec_remain, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds_remain = (int) millisUntilFinished / 1000;
                textview_countdown.setText(seconds_remain + " seconds remaining");
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Passcode Updated", Toast.LENGTH_SHORT).show();
            }
        }.start();


        /**
         * On Click Listener for the verification button
         */
        button_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Start verification activity via Explicit intent
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                Log.d("TAG", "onClick: the passcode in main is : " + passcode);
                intent.putExtra("passcode",passcode);
                startActivity(intent);


            }
        });



        /**
         * This broadcast receiver receive ACTION_TIME_TICK which is sent when the minutes changed
         * After that, the passcode is changed and synced
         */
        minuteChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    // Update the passcode when the minutes change
                    passcode = renew_passcode();
                    Log.d("TAG", "onReceive: Main update passcode: " + passcode);
                    textview_passcode.setText(String.valueOf(passcode));

                    // Start the 60 seconds timer
                    timer.start();

//                    // Broadcast the passcode to be received by VerificationActivity
//                    Intent passcodeIntent = new Intent(PASSCODE_UPDATE_ACTION);
//                    passcodeIntent.putExtra("passcode", passcode);
//                    sendBroadcast(passcodeIntent);
                }
            }
        };

        // Register the broadcast receiver with intent filter for ACTION_TIME_TICK
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
                Toast.makeText(MainActivity.this, "Passcode Updated", Toast.LENGTH_SHORT).show();
            }
        };

    }



    /**
     * Unregister the broadcast receiver when MainActivity is destroyed
     * Receiver registered in onCreate(), therefore unregister in onDestroy()
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