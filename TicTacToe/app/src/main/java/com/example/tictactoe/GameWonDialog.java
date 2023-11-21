package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameWonDialog extends Dialog {
    private final String message;
    private final PlayGameActivity playGameActivity;


    public GameWonDialog(Context context, String message, PlayGameActivity playGameActivity) {

        super(context);
        this.message = message;
        this.playGameActivity = playGameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won_dialog_layout);

        TextView messageText = findViewById(R.id.winMsg);
        Button startAgainButton = findViewById(R.id.restartButton);
        Button mainMenuButton = findViewById(R.id.backToMenuButton);

        messageText.setText(message);

        /**
         * Button to start again
         */
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGameActivity.restartGame();
                dismiss();
            }
        });

        /**
         * Button to go back to main menu
         */
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new intent and clear activity stack
                Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(mainActivityIntent);
                dismiss();
            }
        });
    }
}