package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EnterNameActivity extends AppCompatActivity {

    String player1_name, player2_name;
    String FILENAME = "players.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        final EditText player1_text = findViewById(R.id.ID_P1_name_entry);
        final EditText player2_text = findViewById(R.id.ID_P2_name_entry);
        final Button start_btn = findViewById(R.id.start_btn);

        /**
         * Setup button on click listener
         */
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1_name = player1_text.getText().toString();
                player2_name = player2_text.getText().toString();

                if (player1_name.isEmpty() && player2_name.isEmpty()) {
                    Toast.makeText(EnterNameActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    savePlayerName();

                    Intent intent = new Intent(EnterNameActivity.this, MainActivity.class);
                    intent.putExtra("P1_name", player1_name);
                    intent.putExtra("P2_name", player2_name);
                    startActivity(intent);
                }
            }
        });

        // Set the focus change listener
        player1_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Set the background for the selected state
                    player1_text.setBackgroundResource(R.drawable.player_border);
                } else {
                    // Set the background for the default state
                    player1_text.setBackgroundResource(R.drawable.player_no_border);
                }
            }
        });

        player2_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Set the background for the selected state
                    player2_text.setBackgroundResource(R.drawable.player_border);
                } else {
                    // Set the background for the default state
                    player2_text.setBackgroundResource(R.drawable.player_no_border);
                }
            }
        });

    }

    /**
     * Save player name to local file
     */
    private void savePlayerName () {
        if (player1_name.isEmpty())
            player1_name = "Player 1 (AI)";
        else if (player2_name.isEmpty()) {
            player2_name = "Player 2 (AI)";
        }
        writeFile();
    }

    /**
     * Restore player name
     * @deprecated
     * @param v
     */
    private void restorePlayerName (View v) {
        readFile();
    }

    /**
     * Read local file
     * Not used in this activity
     */
    private void readFile () {
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
                while ((sLine = br.readLine()) != null)
                    output_text += sLine;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "File Doesn't Exist!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check if the a internal file exist
     * @param fileName
     * @return bool
     */
    private boolean checkFileExist (String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


    /**
     * Write Player names to file
     * Must have at lease 1 Human player
     * Human can be either player 1 or player 2
     * If name is empty, it will be an AI by default
     */
    private void writeFile() {

        try {
            FileOutputStream saveNames = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            saveNames.write(player1_name.getBytes());
            saveNames.write(System.lineSeparator().getBytes());
            saveNames.write(player2_name.getBytes());

            saveNames.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Players Saved!", Toast.LENGTH_SHORT).show();
    }
}