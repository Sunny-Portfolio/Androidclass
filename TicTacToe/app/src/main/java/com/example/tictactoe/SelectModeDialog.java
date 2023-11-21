package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SelectModeDialog extends Dialog {

    private Intent intent;
    private String P1_name;
    private String P2_name;
    private String message;
    private boolean insane_mode_on = false;


    public SelectModeDialog(Context context, String P1_name, String P2_name) {

        super(context);
        this.P1_name = P1_name;
        this.P2_name = P2_name;
        Log.d("MODE", "SelectModeDialog: start " + P1_name + " " + P2_name);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_mode_dialog_layout);

        TextView messageText = findViewById(R.id.ID_hi_msg);
        Button startButton = findViewById(R.id.ID_start_game);
        Switch insaneMode = findViewById(R.id.ID_insane_switch);


        // Set welcome message
        message = setMessage();
        Log.d("TAG", "onCreate: message " + message);
        messageText.setText(message);

        insaneMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    insane_mode_on = true;
                else
                    insane_mode_on = false;
            }
        });


        /**
         * Start Button on click listener
         */
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Change ListView item color on click
                 */
                view.setBackgroundColor(getContext().getColor(R.color.myLimeGreen));

                /**
                 * Reset ListView item color after delay
                 */
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.setBackgroundColor(getContext().getColor(R.color.myLightBlue));
//                    }
//                }, 1000);

                // Send intent and start game
                intent = new Intent(getContext(), PlayGameActivity.class);
                intent.putExtra("Key_P1_name", P1_name);
                intent.putExtra("Key_P2_name", P2_name);
                intent.putExtra("Key_insane", insane_mode_on);
                getContext().startActivity(intent);
                dismiss();
            }
        });

    }

    /**
     * Method to compose welcome message for the dialog
     * @return String of message
     */
    private String setMessage() {
        String msg = "Hi!\nPlease select play mode!";
        if (P1_name.equals("Player 1 (AI)"))
            msg = "Hi! " + P2_name + "\nPlease select play mode!";
        else if (P2_name.equals("Player 2 (AI)"))
            msg = "Hi! " + P1_name + "\nPlease select play mode!";

        return msg;
    }
}