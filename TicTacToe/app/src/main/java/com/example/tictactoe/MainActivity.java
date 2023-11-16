package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView menu_list;
    private ArrayAdapter<String> adapter;
    private List<String> menu_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_list = findViewById(R.id.ID_menu_btn);

        menu_btn = new ArrayList<>();
        menu_btn.add("Enter Names");
        menu_btn.add("Play Game");
        menu_btn.add("Standing");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item ,android.R.id.text1, menu_btn);
        menu_list.setAdapter(adapter);

        /**
         * Setup Listener for menu item selection click. Then start activity accordingly.
         */
        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = parent.getItemAtPosition(position).toString();
                Intent intent;

                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, EnterNameActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, PlayGameActivity.class);
                        // TODO: 11/16/23 may need to pass player name here
//                        intent.putExtra();
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, StandingActivity.class);
                        startActivity(intent);
                        break;
                }

                /**
                 * Reset the animation back to normal
                 */
                view.animate().setDuration(1000).scaleXBy(-10).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setScaleX(1);
                    }
                });




            }
        });

    }
}