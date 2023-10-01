package com.example.simpletourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Fragment_A.OnFragmentInteractionListener, Fragment_B.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(int position){
        FragmentManager FragMgr = getSupportFragmentManager();
        Fragment_B fragB = (Fragment_B) FragMgr.findFragmentById(R.id.fragmentContainerViewB);
        fragB.update_text(position);
    }

}