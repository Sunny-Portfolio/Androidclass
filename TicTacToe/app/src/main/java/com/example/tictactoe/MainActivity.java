package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView menu_list;
    private ArrayAdapter<String> adapter;
    private List<String> menu_btn;
    String PLAYERNAMES = "players.txt";
    private String P1_name, P2_name;

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
                Intent intent;

                // Game menu selection
                switch (position) {
                    case 0:
                        // Go to Enter Name Activity
                        intent = new Intent(MainActivity.this, EnterNameActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        // Read player names from file and send them by intent to start game
                        if (checkFileExist(PLAYERNAMES)) {
                            readFile(PLAYERNAMES);

                            intent = new Intent(MainActivity.this, PlayGameActivity.class);
                            intent.putExtra("Key_P1_name", P1_name);
                            intent.putExtra("Key_P2_name", P2_name);
                            startActivity(intent);
                        } else
                            Toast.makeText(MainActivity.this, "Enter Player Names!", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        // Go to Standing Activity
                        intent = new Intent(MainActivity.this, StandingActivity.class);
                        startActivity(intent);
                        break;
                }

                /**
                 * Button animation, and back to normal
                 */
                view.animate().setDuration(1000).scaleXBy(-10).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setScaleX(1);
                    }
                });

                /**
                 * Change ListView item color on click
                 */
                view.setBackgroundColor(getColor(R.color.myLimeGreen));

                /**
                 * Reset ListView item color after delay
                 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(getColor(R.color.myLightBlue));
                    }
                }, 200);



            }
        });

    }


    /**
     * Method to read file containing player names
     * @param FILENAME
     */
    private void readFile (String FILENAME) {
        if (checkFileExist(FILENAME)) {
            // Read file and update edit text
            FileInputStream fileInput = null;

            try {
                fileInput = openFileInput(FILENAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStreamReader isr = new InputStreamReader(fileInput);
            BufferedReader br = new BufferedReader(isr);

            String sLine = null;
            String output_text = "";

            try {

                if ((sLine = br.readLine()) != null)
                    P1_name = sLine;
                if ((sLine = br.readLine()) != null)
                    P2_name = sLine;

                Log.d("TAG", "readFile: P1 name : " + P1_name);
                Log.d("TAG", "readFile: P2 name : " + P2_name);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Enter Player Names!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check if the a internal file exist
     * @param fileName
     * @return Bool
     */
    private boolean checkFileExist (String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


}