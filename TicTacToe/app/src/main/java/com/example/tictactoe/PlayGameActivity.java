package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class PlayGameActivity extends AppCompatActivity {

    private TextView player1_name_tag, player2_name_tag;
    private TextView P1_win_tag, P2_win_tag, P1_draw_tag, P2_draw_tag;
    private View P1_name_card, P2_name_card;
    private ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;
    private List<int[]> combo_list = new ArrayList<>();
    private int[] box_position = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int turnNumber = 0;
    private int totalBoxSelected = 1;
    private String P1_name, P2_name;
    private int P1_wins = 0;
    private int P2_wins = 0;
    private int draw_count = 0;
    private int ai_side = 0, player_side = 0;
    String FILENAME = "players.txt";
    String PLAYER_STANDING_FILE = "players_standing.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        /**
         * Setup all TextView, ImageView, etc.
         */
        player1_name_tag = findViewById(R.id.ID_P1_name_inGame);
        player2_name_tag = findViewById(R.id.ID_P2_name_inGame);
        P1_win_tag = findViewById(R.id.ID_P1_Win_inGame);
        P1_draw_tag = findViewById(R.id.ID_P1_Draw_inGame);
        P2_win_tag = findViewById(R.id.ID_P2_Win_inGame);
        P2_draw_tag = findViewById(R.id.ID_P2_Draw_inGame);

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
        combo_list.add(new int[]{0, 1, 2});
        combo_list.add(new int[]{3, 4, 5});
        combo_list.add(new int[]{6, 7, 8});
        combo_list.add(new int[]{0, 3, 6});
        combo_list.add(new int[]{1, 4, 7});
        combo_list.add(new int[]{2, 5, 8});
        combo_list.add(new int[]{2, 4, 6});
        combo_list.add(new int[]{0, 4, 8});


        /**
         * Get the players name
         */
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

        setPlaySide();

        if (isAITurn())
            new AImove().execute();

    }

    /**
     * Set playing side for player and AI, Used in AI logic
     */
    private void setPlaySide() {
        if (P1_name.equals("Player 1 (AI)")) {
            ai_side = 1;
            player_side = 2;
        }
        else if (P2_name.equals("Player 2 (AI)")) {
            player_side = 1;
            ai_side = 2;
        }
    }

    /**
     * Initialize the game board boxes, and set onClickListener
     *
     * @param ID view ID
     * @param v  ImageView
     */
    private void setupBoxes(int ID, View v) {
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

                if (isAITurn())
                    new AImove().execute();
            }
            Log.d("TAG", "onClick: box NOT empty");
        }
    };

    /**
     * Determines if it is AI's move
     * @return bool
     */
    private boolean isAITurn() {
        // Check if it's the AI's turn based on player names
        return P1_name.equals("Player 1 (AI)") && turnNumber % 2 == 0 ||
                P2_name.equals("Player 2 (AI)") && turnNumber % 2 == 1;
    }

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
         *
         * @param integers The parameters of the task.
         * @return
         */
        @Override
        protected Integer doInBackground(Integer... integers) {
            return makeAIDecision();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer boxIndex) {
            super.onPostExecute(boxIndex);

            if (boxIndex != -1) {
                // Simulate AI clicking on the chosen box
                int resId = getResources().getIdentifier("ID_Box" + (boxIndex + 1), "id", getPackageName());
                ImageView box = findViewById(resId);
                playMove(box);
            }
        }

        /**
         * Method that chooses a random available box as AI move. Easy Mode
         * @return random number base on available index number
         */
        private int makeRandomDecision() {
            List<Integer> emptyBoxes = getEmptyBoxes();

            if (!emptyBoxes.isEmpty()) {
                // Randomly choose an empty box
                int randomIndex = (int) (Math.random() * emptyBoxes.size());
                return emptyBoxes.get(randomIndex);
            }

            return -1;
        }


        /**
         * Method for AI to make its move
         * @return int position index
         */
        private int makeAIDecision() {
            // Check for winning moves
            int winningMove = checkForWinningMove();
            if (winningMove != -1) {
                return winningMove;
            }

            // Check for blocking opponent's winning moves
            int blockingMove = checkForBlockingMove();
            if (blockingMove != -1) {
                return blockingMove;
            }

            // Check for taking the center square
            if (box_position[4] == 0) {
                return 4;
            }

            // Check for opposite corner move
            int oppositeCornerMove = checkForOppositeCornerMove();
            if (oppositeCornerMove != -1) {
                return oppositeCornerMove;
            }

            // Check for empty corner move
            int emptyCornerMove = checkForEmptyCornerMove();
            if (emptyCornerMove != -1) {
                return emptyCornerMove;
            }

            // Check for empty side move
            int emptySideMove = checkForEmptySideMove();
            if (emptySideMove != -1) {
                return emptySideMove;
            }

            // If no strategic move, make a random move
            return makeRandomDecision();
        }

        /**
         * Check to see if there is a winning move
         * @return int position index
         */
        private int checkForWinningMove() {
            // Check for a winning move for the AI
            for (int[] combo : combo_list) {
                int pos1 = combo[0];
                int pos2 = combo[1];
                int pos3 = combo[2];

                // If two out of three positions are AI's, make the winning move
                if (box_position[pos1] == ai_side && box_position[pos2] == ai_side && box_position[pos3] == 0) {
                    return pos3;
                } else if (box_position[pos1] == ai_side && box_position[pos2] == 0 && box_position[pos3] == ai_side) {
                    return pos2;
                } else if (box_position[pos1] == 0 && box_position[pos2] == ai_side && box_position[pos3] == ai_side) {
                    return pos1;
                }
            }

            return -1;
        }

        private int checkForBlockingMove() {
            // Check for a move to block the opponent from winning
            for (int[] combo : combo_list) {
                int pos1 = combo[0];
                int pos2 = combo[1];
                int pos3 = combo[2];

                // If two out of three positions are the opponent's, block the move
                if (box_position[pos1] == player_side && box_position[pos2] == player_side && box_position[pos3] == 0) {
                    return pos3;
                } else if (box_position[pos1] == player_side && box_position[pos2] == 0 && box_position[pos3] == player_side) {
                    return pos2;
                } else if (box_position[pos1] == 0 && box_position[pos2] == player_side && box_position[pos3] == player_side) {
                    return pos1;
                }
            }

            return -1;
        }

        // Check for opposite corner move
        private int checkForOppositeCornerMove() {
            int[] cornerPairs = {0, 8, 2, 6};
            for (int i = 0; i < cornerPairs.length; i += 2) {
                int corner1 = cornerPairs[i];
                int corner2 = cornerPairs[i + 1];
                if (box_position[corner1] == player_side && box_position[corner2] == 0) {
                    return corner2;
                } else if (box_position[corner2] == player_side && box_position[corner1] == 0) {
                    return corner1;
                }
            }
            return -1;
        }

        // Check for empty corner move
        private int checkForEmptyCornerMove() {
            int[] corners = {0, 2, 6, 8};
            for (int corner : corners) {
                if (box_position[corner] == 0) {
                    return corner;
                }
            }
            return -1;
        }

        // Check for empty side move
        private int checkForEmptySideMove() {
            int[] sides = {1, 3, 5, 7};
            for (int side : sides) {
                if (box_position[side] == 0) {
                    return side;
                }
            }
            return -1;
        }

        private List<Integer> getEmptyBoxes() {
            // Get a list of empty boxes
            List<Integer> emptyBoxes = new ArrayList<>();
            for (int i = 0; i < box_position.length; i++) {
                if (box_position[i] == 0) {
                    emptyBoxes.add(i);
                }
            }
            return emptyBoxes;
        }
    }

    /**
     * Method to check if the box has been played.
     *
     * @param box
     * @return bool
     */
    private boolean isBoxEmpty(ImageView box) {
        return box.getDrawable() == null;
    }


    /**
     * Method to play a move when a button is clicked. It will determine which player's turn
     * and switch player accordingly. It will also stop and display result when the game is finished
     *
     * @param v View of the box being clicked.
     */
    private void playMove(ImageView v) {
        if (turnNumber % 2 == 0) {

            v.setImageResource(R.drawable.cross);
            // Set the box_position to mark Player 1's move
            int boxIndex = getBoxIndex(v);
            box_position[boxIndex] = 1;

            if (checkResults()) {
                // Increment win count and update text
                P1_wins++;
                P1_win_tag.setText(String.valueOf(P1_wins));

                // Display result
                displayResult(player1_name_tag.getText().toString() + " is a Winner!");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, player1_name_tag.getText().toString() + " WON!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();

            } else if (turnNumber == 8) {
                // Increment Draw count and update text
                draw_count++;
                P1_draw_tag.setText(String.valueOf(draw_count));
                P2_draw_tag.setText(String.valueOf(draw_count));

                // Display result
                displayResult("Match Draw");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, "It's a Draw!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else {
                // Increment and next turn
                turnNumber++;
                switchPlayer();
            }

        } else {
            v.setImageResource(R.drawable.circle);
            // Set the box_position to mark Player 2's move
            int boxIndex = getBoxIndex(v);
            box_position[boxIndex] = 2;

            if (checkResults()) {
                // Increment win count and update text
                P2_wins++;
                P2_win_tag.setText(String.valueOf(P2_wins));

                // Display result
                displayResult(player2_name_tag.getText().toString() + " is a Winner!");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, player2_name_tag.getText().toString() + " WON!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else if (turnNumber == 8) {
                // Increment Draw count and update text
                draw_count++;
                P1_draw_tag.setText(String.valueOf(draw_count));
                P2_draw_tag.setText(String.valueOf(draw_count));

                // Display result
                displayResult("Match Draw");
                GameWonDialog showWin = new GameWonDialog(PlayGameActivity.this, "It's a Draw!", PlayGameActivity.this);
                showWin.setCancelable(false);
                showWin.show();
            } else {
                // Increment and next turn
                turnNumber++;
                switchPlayer();
            }

        }
    }

    private void displayResult(String s) {
        Log.d("TAG", "displayResult: " + s);
    }


    /**
     * Get the corresponding square number being clicked
     *
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
     *
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
     *
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
     *
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


    /**
     * Method to save player scores to local file
     */
    private void savePlayerScores() {

        // Save data format: Player name``win,,draw,,total games
        String P1_data = P1_name + "``" + P1_wins + ",," + draw_count + ",," + (P1_wins + P2_wins + draw_count);
        String P2_data = P2_name + "``" + P2_wins + ",," + draw_count + ",," + (P1_wins + P2_wins + draw_count);

        String existingData = readFromFile();
        String updatedData = null;
        boolean data_updated = false;

        if (existingData.contains(P1_name)) {
            existingData = updatePlayerData(existingData, P1_name, P1_wins, draw_count, (P1_wins + P2_wins + draw_count));
            Log.d("PLAY", "savePlayerScores: updated data 1 : \n" + existingData);
            data_updated = true;
        } else {
            existingData += P1_data + "\n";
        }
        if (existingData.contains(P2_name)) {
            existingData = updatePlayerData(existingData, P2_name, P2_wins, draw_count, (P1_wins + P2_wins + draw_count));
            Log.d("PLAY", "savePlayerScores: updated data 2 : \n" + existingData);
            data_updated = true;
        } else {
            existingData += P2_data + "\n";
        }



        try {
            FileOutputStream fos = openFileOutput(PLAYER_STANDING_FILE, Context.MODE_PRIVATE);
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            osw.write(data);
//            osw.close();
            Log.d("PLAY", "savePlayerScores: save update : \n" + existingData);
            fos.write((existingData).getBytes());


//            FileOutputStream saveNames = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            saveNames.write(player1_name.getBytes());
//            saveNames.write(System.lineSeparator().getBytes());
//            saveNames.write(player2_name.getBytes());
//
//            saveNames.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving player scores!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Method to read player data from local file
     *
     * @return String of all player names and scores
     */
    private String readFromFile() {


        if (checkFileExist(PLAYER_STANDING_FILE)) {
            // Read file and update edit text
            FileInputStream fileInput = null;

            try {
                fileInput = openFileInput(PLAYER_STANDING_FILE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "";
            }

            InputStreamReader isr = new InputStreamReader(fileInput);
            BufferedReader br = new BufferedReader(isr);

            String sLine = null;
            String output_text = "";

            try {
                while ((sLine = br.readLine()) != null) {
                    output_text += sLine;
                    output_text += "\n";
                }

                fileInput.close();
                return output_text.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

        } else {
            Toast.makeText(this, "File Doesn't Exist!", Toast.LENGTH_SHORT).show();
            return "";
        }
    }


    /**
     * Check if the a internal file exist
     *
     * @param fileName
     * @return Bool
     */
    private boolean checkFileExist(String fileName) {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


    /**
     * Method to update player data if it already exists in the file
     *
     * @param existingData Data of all players
     * @param playerName Existing player name
     * @param wins Existing player win count
     * @param draws Existing player draw count
     * @param totalGames Existing player total games played
     * @return String of updated list
     */
    private String updatePlayerData(String existingData, String playerName, int wins, int draws, int totalGames) {
        String[] allPlayers = existingData.split("\n");

        // Find the position of the matching player and update
        for (int i = 0; i < allPlayers.length; i++) {
            if (allPlayers[i].contains(playerName)) {
                Log.d("PLAY", "updatePlayerData: has player \n" + playerName);
                // Get the parts of the player data [0] is name, [1] is data
                String[] parts = allPlayers[i].split("``");
                // Separate the player data
                int existingWins = Integer.parseInt(parts[1].split(",,")[0]);
                int existingDraws = Integer.parseInt(parts[1].split(",,")[1]);
                int existingTotalGames = Integer.parseInt(parts[1].split(",,")[2]);

                // Update the player's data
                allPlayers[i] = playerName + "``" + (existingWins + wins) + ",," + (existingDraws + draws) + ",," + (existingTotalGames + totalGames);
                break;
            }
        }

        // Rebuild the updated data
        StringBuilder updatedData = new StringBuilder();
        for (String line : allPlayers) {
            updatedData.append(line).append("\n");
            Log.d("PLAY", "updatePlayerData: update: \n" + updatedData);
        }

        return String.valueOf(updatedData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: Game ended");
        savePlayerScores();
    }
}