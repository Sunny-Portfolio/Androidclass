package com.example.securitytokenapp;

import androidx.activity.OnBackPressedCallback;
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

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerificationActivity extends AppCompatActivity {

    private ListView timeStampListView;
    private Button button_clear;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> timestamps_arraylist = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private int passcode;
    private boolean restoreAllTimestamp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        /**
         * Setup views, buttons
         */
        timeStampListView = findViewById(R.id.time_stamp_list);
        button_clear = findViewById(R.id.btn_clear);


        /**
         * Find out if restore all timestamp (back button pressed) or not (app closed and restarted)
         */
        sharedPreferences = getSharedPreferences("passcode_preference", MODE_PRIVATE);
        restoreAllTimestamp = sharedPreferences.getBoolean("isRestoreAll_key", false);

        if (restoreAllTimestamp) {
            loadAllTimestampsFromSharedPreferences();
            setRestoreAllTimestamp(false);
        } else {
            loadLastTimestampsFromSharedPreferences();
        }


        /**
         * Setup adaptor after loading saved preferences
         */
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, timestamps_arraylist);
        timeStampListView.setAdapter(adapter);


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
         * Get the verified code from intent
         */
        passcode = getIntent().getIntExtra("passcode", 0);

        // Add the new timestamp to the list and update the ListView
        String timestamp = getCurrentTimestamp();
        timestamps_arraylist.add(timestamp + "\t\t\t" + passcode);
        saveTimeStamp(timestamp + "\t\t\t" + passcode);
        adapter.notifyDataSetChanged();


        /**
         * This will save all timestamps in the arraylist when back button is pressed
         * This is implement to separate the data being saved and restored from shared preference
         * Because both back and app closes calls onDestroy() and the apps needs to keep its growing
         * list if only back button is pressed. But it should only restore one last timestamps on
         * startup.
         */
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                saveAllTimeStamp(timestamps_arraylist);
                finish();
            }
        });

    }



    /**
     * Create the timestamp string
     * @return
     */
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        return sdf.format(new Date());
    }


    /**
     * Method used to save only the last timestamp to shared preferences
     * @param timestamp
     */
    private void saveTimeStamp (String timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_timestamp_key", timestamp);
        editor.apply();
    }

    /**
     * Method used to save All timestamp to shared preferences
     * @param timestamp
     */
    private void saveAllTimeStamp (ArrayList timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save timestamp ArrayList to JSON format to preserve order of the list
        Gson gson = new Gson();
        String json = gson.toJson(timestamps_arraylist);
        editor.putString("all_timestamp_key", json);
        editor.putBoolean("isRestoreAll_key", true);
        editor.apply();
    }

    /**
     * Method used to load only the last timestamp from shared preferences
     * Used on app startup
     */
    private void loadLastTimestampsFromSharedPreferences() {
        sharedPreferences = getSharedPreferences("passcode_preference", MODE_PRIVATE);

        String lastTimestamp = sharedPreferences.getString("last_timestamp_key", "");

        if (lastTimestamp.isEmpty()) {
            timestamps_arraylist.add("Beginning of list");
        } else {
//            timestamps_arraylist.clear();
            timestamps_arraylist.add(lastTimestamp);
        }
    }

    /**
     * Method used to load all timestamp from shared preferences
     * Used on app resume
     */
    private void loadAllTimestampsFromSharedPreferences() {
        sharedPreferences = getSharedPreferences("passcode_preference", MODE_PRIVATE);

        // Load ArrayList from JSON string
        String allTimestamp = sharedPreferences.getString("all_timestamp_key", null);

        if (allTimestamp == null) {
            timestamps_arraylist.add("Beginning of list");
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            timestamps_arraylist = gson.fromJson(allTimestamp, type);
        }
    }


    /**
     * Method used to load all timestamp from shared preferences
     * Used on app resume
     * Currently not used
     * @deprecated
     */
    private void loadTimestampsFromSharedPreferences() {
        String allTimestamp = sharedPreferences.getString("all_timestamp_key", null);

        if (allTimestamp == null) {
            timestamps_arraylist.add("Beginning of list");
        } else {
            timestamps_arraylist.clear();
            timestamps_arraylist.addAll(Collections.singleton(allTimestamp));
        }
    }

    /**
     * Method used to clear timestamp from shared preferences
     * Currently not used
     */
    private void clearTimestamps() {
        SharedPreferences last_time_pref = getSharedPreferences("passcode_preference", MODE_PRIVATE);
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
     * Method to change the saved shared preference for restoreAllTimstamp
     * This is used to indicate if the app needs to restore all timestamps
     * @param bool
     */
    private void setRestoreAllTimestamp(boolean bool) {
        restoreAllTimestamp = bool;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRestoreAll_key", restoreAllTimestamp);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: 1");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("TAG", "onPostResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "onRestart: 1");
    }


}