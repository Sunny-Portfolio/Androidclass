package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
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
    private View P1_name_card, P2_name_card;
    private ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;
    private List<int[]> combo_list = new ArrayList<>();
    private int[] box_position = {0,0,0,0,0,0,0,0,0};
    private int turnNumber = 0;
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
        P1_name_card = findViewById(R.id.ID_P1_player_card);
        P2_name_card = findViewById(R.id.ID_P2_player_card);

        setupBoxes(R.id.ID_Box1, box1);
        setupBoxes(R.id.ID_Box2, box2);
        setupBoxes(R.id.ID_Box3, box3);
        setupBoxes(R.id.ID_Box4, box4);
        setupBoxes(R.id.ID_Box5, box5);
        setupBoxes(R.id.ID_Box6, box6);
        setupBoxes(R.id.ID_Box7, box7);
        setupBoxes(R.id.ID_Box8, box8);
        setupBoxes(R.id.ID_Box9, box9);


        /**
         * Get the screen size and set the game board height matching the screen size
         */
        GridLayout layout = findViewById(R.id.ID_playboard);
        int screenWidth = getResources().getDisplayMetrics().widthPixels - 64;
        layout.getLayoutParams().height = screenWidth;


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
        try {
            if (!P1_name.isEmpty())
                player1_name_tag.setText(P1_name);
            if (!P2_name.isEmpty())
                player2_name_tag.setText(P2_name);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Player Name ERROR!", Toast.LENGTH_SHORT).show();
        }




    }

    /**
     * Initialize the game board boxes, and set onClickListener
     * @param ID view ID
     * @param v ImageView
     */
    private void setupBoxes (int ID, View v) {
        v = findViewById(ID);
        v.setOnClickListener(Listener);
    }

    /**
     * OnClickListener for the game board
     */
    View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isBoxEmpty((ImageView) v)) {
                Log.d("TAG", "onClick: box isempty");
                playMove((ImageView) v);
            }
            Log.d("TAG", "onClick: box NOT empty");
        }
    };


    /**
     * Async Task for AI's moves
     */
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

    private boolean isBoxEmpty(ImageView box) {
        return box.getDrawable() == null;
    }

    private void playMove(ImageView v) {
        if (turnNumber % 2 == 0) {

            v.setImageResource(R.drawable.cross);
            // Set the box_position to mark Player 1's move
            int boxIndex = getBoxIndex(v);
            box_position[boxIndex] = 1;

            if (checkResults()) {
                displayResult(player1_name_tag.getText().toString() + " is a Winner!");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, player1_name_tag.getText().toString() + " WON!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else if (turnNumber == 8) {
                displayResult("Match Draw");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, "It's a Draw!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else {
                turnNumber++;
                switchPlayer();
            }

        } else {
            v.setImageResource(R.drawable.circle);
            // Set the box_position to mark Player 2's move
            int boxIndex = getBoxIndex(v);
            box_position[boxIndex] = 2;

            if (checkResults()) {
                displayResult(player2_name_tag.getText().toString() + " is a Winner!");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, player2_name_tag.getText().toString() + " WON!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else if (turnNumber == 8) {
                displayResult("Match Draw");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, "It's a Draw!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else {
                turnNumber++;
                switchPlayer();
            }

        }
    }

    private void displayResult (String s) {
        Log.d("TAG", "displayResult: " + s);
    }


    /**
     * Get the corresponding square number being clicked
     * @param v
     * @return the corresponding number of the grid
     */
    private int getBoxIndex(ImageView v) {
        int id = v.getId();
        if (id == R.id.ID_Box1) {
            return 0;
        } else if (id == R.id.ID_Box2) {
            return 1;
        } else if (id == R.id.ID_Box3) {
            return 2;
        } else if (id == R.id.ID_Box4) {
            return 3;
        } else if (id == R.id.ID_Box5) {
            return 4;
        } else if (id == R.id.ID_Box6) {
            return 5;
        } else if (id == R.id.ID_Box7) {
            return 6;
        } else if (id == R.id.ID_Box8) {
            return 7;
        } else if (id == R.id.ID_Box9) {
            return 8;
        } else {
            return -1;
        }
    }

    /**
     * Check to see if the game has been won
     * @return bool
     */
    private boolean checkResults() {
        for (int[] combo : combo_list) {
            int pos1 = combo[0];
            int pos2 = combo[1];
            int pos3 = combo[2];

            if (box_position[pos1] != 0 && box_position[pos1] == box_position[pos2] && box_position[pos2] == box_position[pos3]) {
                // Highlight the winning combo
                highlightWinningCombo(pos1, pos2, pos3);
                return true;
            }
        }
        return false;
    }


    /**
     * Highlight the winning combo at the end of the game
     * @param pos1 position of the grid
     * @param pos2 position of the grid
     * @param pos3 position of the grid
     */
    private void highlightWinningCombo(int pos1, int pos2, int pos3) {
        highlightBox(pos1);
        highlightBox(pos2);
        highlightBox(pos3);
    }

    /**
     * Highlight action of one box of the grid
     * @param position
     */
    private void highlightBox(int position) {
        int resId = getResources().getIdentifier("ID_Box" + (position + 1), "id", getPackageName());
        ImageView box = findViewById(resId);
        box.setBackground(getDrawable(R.drawable.playboard_square_hl));
    }

    /**
     * Method to change the player card border to illustrate which player turn it is
     */
    private void switchPlayer() {
        if (turnNumber % 2 == 0) {
            P1_name_card.setBackground(getDrawable(R.drawable.player_border));
            P2_name_card.setBackground(getDrawable(R.drawable.player_no_border));
        } else {
            P1_name_card.setBackground(getDrawable(R.drawable.player_no_border));
            P2_name_card.setBackground(getDrawable(R.drawable.player_border));
        }
    }

    /**
     * Method to restart the game and reset all counters
     */
    protected void restartGame() {
        // Reset all game variables and UI elements to start a new game
        turnNumber = 0;
//        totalBoxSelected = 1;

        // Clear the box_position array
        for (int i = 0; i < box_position.length; i++) {
            box_position[i] = 0;
        }

        // Reset the image resources of all boxes to null
        resetBoxBackground();

        // Reset the player card borders
        P1_name_card.setBackground(getDrawable(R.drawable.player_border));
        P2_name_card.setBackground(getDrawable(R.drawable.player_no_border));
    }

    /**
     * Method to reset the background to the default state for all boxes
     */
    private void resetBoxBackground() {
        for (int i = 0; i < 9; i++) {
            int resId = getResources().getIdentifier("ID_Box" + (i + 1), "id", getPackageName());
            ImageView box = findViewById(resId);
            box.setImageResource(0);
            box.setBackground(getDrawable(R.drawable.playboard_square));
        }
    }
}