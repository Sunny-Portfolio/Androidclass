package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StandingActivity extends AppCompatActivity {

    TextView title;
    ListView playerStandingList;
    String PLAYER_STANDING_FILE = "players_standing.txt";
    List<String> player_data;
    String player_data_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing);

//        title.findViewById(R.id.ID_standing_title);
       playerStandingList = findViewById(R.id.ID_standing_list);
       title = findViewById(R.id.ID_standing_title);


        /**
         * Set Standing title
         */
        final String title_text = String.format("%-15s %3s %3s %3s", "Name", "W", "D", "%");
        title.setText(title_text);

//        player_data = readFromFile();
        player_data_str = readFromFile();

        if (player_data_str != null) {
            Log.d("TAG", "onCreate: received data: " + player_data_str);
            player_data = splitData(player_data_str);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, player_data);
            playerStandingList.setAdapter(adapter);
        }

    }



    private List<String> splitData (String player_data_str) {
        String [] player_data_array = player_data_str.split("\n");
        List<String> player_list = new ArrayList<>();

        for (int i = 0; i < player_data_array.length; i++) {
            String player_name, player_win, player_draw, player_total_games;

            // Get the parts of the player data [0] is name, [1] is data
            String[] parts = player_data_array[i].split("``");
            Log.d("TAG", "splitData: parts 0 : " + parts[0]);
            Log.d("TAG", "splitData: parts 1 : " + parts[1]);

            // Separate the player data
            String existingName = parts[0];
            int existingWins = Integer.parseInt(parts[1].split(",,")[0]);
            int existingDraws = Integer.parseInt(parts[1].split(",,")[1]);
            int existingTotalGames = Integer.parseInt(parts[1].split(",,")[2]);
            int winPercentage = (int) Math.round((double) existingWins / existingTotalGames * 100);
            Log.d("STAND", "splitData: win " + existingWins + " total " + existingTotalGames + " percentage " + winPercentage);

            // Update the player's data
            String formatted_data = String.format("%-35s %8s %8s %8s", existingName, existingWins, existingDraws, winPercentage + "%");
            player_list.add(formatted_data);
        }
        return player_list;
    }


    /**
     * Method to load player scores from local file
     */
    private String readFromFile() {

        Log.d("TAG", "readFromFile: 1");
        if (checkFileExist(PLAYER_STANDING_FILE)) {
            // Read file and update edit text
            FileInputStream fileInput = null;
            Log.d("TAG", "readFromFile: 2");

            try {
                fileInput = openFileInput(PLAYER_STANDING_FILE);
                Log.d("TAG", "readFromFile: 3");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }

            InputStreamReader isr = new InputStreamReader(fileInput);
            BufferedReader br = new BufferedReader(isr);

            String sLine = null;
            String output_text = "";
//            List<String> player_list = new ArrayList<>();

            try {
                while ((sLine = br.readLine()) != null) {
                    Log.d("TAG", "readFromFile: 4");

//                    player_list.add(sLine);
                    output_text += sLine + "\n";
                }

                fileInput.close();
                return output_text;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG", "readFromFile: 5");
                return null;
            }

        } else {
            Toast.makeText(this, "File Doesn't Exist!", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "readFromFile: 6");

            return null;
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
}