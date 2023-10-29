package com.example.securitytokenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;

public class VerificationActivity extends AppCompatActivity {

    ListView timeStampListView;
    String [] time_stamp_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        timeStampListView = findViewById(R.id.time_stamp_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, time_stamp_list);
        timeStampListView.setAdapter(adapter);

    }
}