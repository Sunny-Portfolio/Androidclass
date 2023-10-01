package com.example.simpletourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Fragment_A.OnFragmentInteractionListener, Fragment_B.OnFragmentInteractionListener {

//    private int test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("SaveState", "onCreate: MainActivity");
//
//        if (savedInstanceState != null) {
//            cityDetails_text.setText(savedInstanceState.getString(STATE_cityDetails));
//            Log.d("SaveState", "onCreate in main: Recovered saved instance state: detail is "
//                    + savedInstanceState.getInt("testkey"));
//            test1 = savedInstanceState.getInt("testkey");
//            onFragmentInteraction(test1);
//        }
    }

    @Override
    public void onFragmentInteraction(int position){
//        test1 = position;
        Log.d("TAG", "In MainActivity --->>> Item " + position + " is selected!");
        FragmentManager FragMgr = getSupportFragmentManager();
        Fragment_B fragB = (Fragment_B) FragMgr.findFragmentById(R.id.fragmentContainerViewB);
        fragB.update_text(position);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
////        outState.putString("testkey", test1);
//        outState.putInt("testkey",test1);
//        // Call superclass to save any view hierarchy.
//        super.onSaveInstanceState(outState);
//        Log.d("SaveState", "onSaveInstanceState: MainActivity");
//
//    }
}