package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlayGameActivity extends AppCompatActivity {

    private TextView player1_name_tag, player2_name_tag;
    private ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;
    private List<int[]> combo_list = new ArrayList<>();
    private int[] box_position = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1;
    private int totalBoxSelected = 1;
    private String P1_name, P2_name;
    String FILENAME = "players.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        /**
         * Setup all TextView, ImageView, etc.
         */
        player1_name_tag = findViewById(R.id.ID_P1_name_inGame);
        player2_name_tag = findViewById(R.id.ID_P2_name_inGame);

        box1 = findViewById(R.id.ID_Box1);
        box2 = findViewById(R.id.ID_Box2);
        box3 = findViewById(R.id.ID_Box3);
        box4 = findViewById(R.id.ID_Box4);
        box5 = findViewById(R.id.ID_Box5);
        box6 = findViewById(R.id.ID_Box6);
        box7 = findViewById(R.id.ID_Box7);
        box8 = findViewById(R.id.ID_Box8);
        box9 = findViewById(R.id.ID_Box9);


        /**
         * Combination of the winning strikes
         */
        combo_list.add(new int[] {0,1,2});
        combo_list.add(new int[] {3,4,5});
        combo_list.add(new int[] {6,7,8});
        combo_list.add(new int[] {0,3,6});
        combo_list.add(new int[] {1,4,7});
        combo_list.add(new int[] {2,5,8});
        combo_list.add(new int[] {2,4,6});
        combo_list.add(new int[] {0,4,8});


        /**
         * Get the players name
         */
        // TODO: 11/17/23 this probably should get from internal file 
        P1_name = getIntent().getStringExtra("Key_P1_name");
        P2_name = getIntent().getStringExtra("Key_P2_name");

        /**
         * Set player name on the player card
         */
        if (!P1_name.isEmpty())
            player1_name_tag.setText(P1_name);
        if (!P2_name.isEmpty())
            player2_name_tag.setText(P2_name);



    }





    private class AImove extends AsyncTask<Integer, String, Integer> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * AI's move goes here
         * @param integers The parameters of the task.
         *
         * @return
         */
        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }


    }
}