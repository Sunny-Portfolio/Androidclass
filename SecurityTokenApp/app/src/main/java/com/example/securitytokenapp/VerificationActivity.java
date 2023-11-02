package com.example.securitytokenapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private ArrayList<String> timestamps_arraylist = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        /**
         * Setup views, buttons, SharedPreferences
         */
        timeStampListView = findViewById(R.id.time_stamp_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, timestamps_arraylist);
        timeStampListView.setAdapter(adapter);
        button_clear = findViewById(R.id.btn_clear);


        if (timestamps_arraylist.isEmpty()) {
            loadTimestampsFromSharedPreferences();
            Log.d("TAG", "onCreate: Timestamp is empty");
        }


        /**
         * Clear button action
         */
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timestamps_arraylist.clear();
//                clearTimestamps();
                adapter.notifyDataSetChanged();
            }
        });



        /**
         * Broadcast receiver for passcode update
         */
        passcodeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("TAG", "onReceive: in broadcast receiver");
                if (PASSCODE_UPDATE_ACTION.equals(intent.getAction())) {
                    int passcode = intent.getIntExtra("passcode", 0);
                    String timestamp = getCurrentTimestamp();

                    // Add the timestamp to the list and update the ListView
                    timestamps_arraylist.add(timestamp + "\t\t\t" + passcode);
                    saveTimeStamp(timestamp + "\t\t\t" + passcode);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "onReceive: in broadcast receiver2");
                    int count = 1;
                    for (String time : timestamps_arraylist){
                        Log.d("TAG", "onReceive: in broadcast receiver2 : " + count + ") " + time);
                        count++;
                    }

                }

            }
        };

        // Register the broadcast receiver to listen for passcode updates
        registerReceiver(passcodeReceiver, new IntentFilter(PASSCODE_UPDATE_ACTION));

    }



    /**
     * Create the timestamp string
     * @return
     */
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        return sdf.format(new Date());
    }


    /**
     * Method used to save the timestamp to shared preferences
     * @param timestamp
     */
    private void saveTimeStamp (String timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_timestamp_key", timestamp);
        editor.apply();
    }

    /**
     * Method used to load last timestamp from shared preferences
     */
    private void loadTimestampsFromSharedPreferences() {
        sharedPreferences = getSharedPreferences("passcode_preference", MODE_PRIVATE);

        String lastTimestamp = sharedPreferences.getString("last_timestamp_key", "");

        if (lastTimestamp.isEmpty()) {
            timestamps_arraylist.add("Beginning of list");
        } else {
            timestamps_arraylist.add(lastTimestamp);
            Log.d("TAG", "loadTimestampsFromSharedPreferences: 1 :");
            for (String time : timestamps_arraylist){
                Log.d("TAG", "loadTimestampsFromSharedPreferences: : " + time);
            }
        }
    }

    /**
     * Method used to clear timestamp from shared preferences
     * Currently not used
     */
    private void clearTimestamps() {
        SharedPreferences sharedPreferences = getSharedPreferences("Timestamps", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }


    /**
     * Save instance state when screen rotates
     * @param outState Bundle in which to place your saved state.
     *
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("Passcode_key", timestamps_arraylist);
        Log.d("TAG", "onSaveInstanceState: 1");
    }

    /**
     * Restore instance state after screen rotation
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     *
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Update listView adaptor with restored arraylist data
        ArrayList<String> restoredTimestamps = savedInstanceState.getStringArrayList("Passcode_key");
        if (restoredTimestamps != null) {
            timestamps_arraylist.clear();
            timestamps_arraylist.addAll(restoredTimestamps);
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * Unregister the broadcast receiver when VerificationActivity is destroyed
     * Receiver registered in onCreate(), therefore unregister in onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(passcodeReceiver);
    }
}