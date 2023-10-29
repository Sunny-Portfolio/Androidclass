package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VerificationActivity extends AppCompatActivity {

    private ListView timeStampListView;
    private Button button_clear;
    private ArrayAdapter<String> adapter;
    private BroadcastReceiver passcodeReceiver;
    private static final String PASSCODE_UPDATE_ACTION = "com.example.securitytokenapp.passcode_update";
    private List<String> timestamps = new ArrayList<>();
//    String [] time_stamp_list = {"test1", "test2", "test3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        /**
         * Setup views and buttons
         */
        timeStampListView = findViewById(R.id.time_stamp_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, timestamps);
        timeStampListView.setAdapter(adapter);

        timestamps.add("999999");

        /**
         * Broadcast receiver for passcode update
         */
        passcodeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (PASSCODE_UPDATE_ACTION.equals(intent.getAction())) {
                    int passcode = intent.getIntExtra("passcode", 0);
                    String timestamp = getCurrentTimestamp();

                    // Add the timestamp to the list and update the ListView
                    timestamps.add(timestamp + "  " + passcode);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        // Register the broadcast receiver to listen for passcode updates
        registerReceiver(passcodeReceiver, new IntentFilter(PASSCODE_UPDATE_ACTION));

    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        return sdf.format(new Date());
    }
}