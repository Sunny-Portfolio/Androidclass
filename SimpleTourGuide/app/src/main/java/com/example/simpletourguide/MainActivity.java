package com.example.simpletourguide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Fragment_A.OnFragmentInteractionListener, Fragment_B.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("ddd", "onCreate: ");
    }

    @Override
    public void onFragmentInteraction(int position){
        FragmentManager FragMgr = getSupportFragmentManager();
        Fragment_B fragB = (Fragment_B) FragMgr.findFragmentById(R.id.fragmentContainerViewB);
        fragB.update_text(position);
    }



//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("ddd", "onStart: ");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("ddd", "onResume: ");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d("ddd", "onPause: ");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d("ddd", "onStop: ");
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d("ddd", "onDestroy: ");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("ddd", "onRestart: ");
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.d("ddd", "onRestoreInstanceState: ");
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d("ddd", "onSaveInstanceState: ");
//    }
}