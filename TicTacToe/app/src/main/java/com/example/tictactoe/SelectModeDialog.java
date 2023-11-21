package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class SelectModeDialog extends Dialog {

    private Intent intent;
    private String P1_name;
    private String P2_name;
    private String message;
    //    private final int playerScore;
//    private final int computerScore;
//    private final MainActivity mainActivity;
//    private final PlayGameActivity playGameActivity;


    //    public GameWonDialog(Context context, String message, int playerScore, int computerScore, MainActivity mainActivity) {
    public SelectModeDialog(Context context, String P1_name, String P2_name) {

        super(context);
        this.P1_name = P1_name;
        this.P2_name = P2_name;
        Log.d("MODE", "SelectModeDialog: start " + P1_name + " " + P2_name);
//        this.playerScore = playerScore;
//        this.computerScore = computerScore;
//        this.mainActivity = mainActivity;
//        this.playGameActivity = playGameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_mode_dialog_layout);

        TextView messageText = findViewById(R.id.ID_hi_msg);
        Button startButton = findViewById(R.id.ID_start_game);
        Switch insaneMode = findViewById(R.id.ID_insane_switch);

        message = setMessage();
        Log.d("TAG", "onCreate: message " + message);
        messageText.setText(message);
//        scoreText.setText("Player: " + playerScore + " | Computer: " + computerScore);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                playGameActivity.restartGame();
                intent = new Intent(getContext(), PlayGameActivity.class);
                intent.putExtra("Key_P1_name", P1_name);
                intent.putExtra("Key_P2_name", P2_name);
                getContext().startActivity(intent);
                dismiss();
            }
        });

//        mainMenuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Start a new intent and clear activity stack
//                Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
//                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                getContext().startActivity(mainActivityIntent);
//                dismiss();
//            }
//        });
    }

    private String setMessage() {
        String msg = "Hi!\nPlease select play mode!";
        if (P1_name.equals("Player 1 (AI)"))
            msg = "Hi! " + P2_name + "\nPlease select play mode!";
        else if (P2_name.equals("Player 2 (AI)"))
            msg = "Hi! " + P1_name + "\nPlease select play mode!";

        return msg;
    }
}