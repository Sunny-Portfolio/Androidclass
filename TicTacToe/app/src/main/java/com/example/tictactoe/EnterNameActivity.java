package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterNameActivity extends AppCompatActivity {

    String player1_name, player2_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        final EditText player1_text = findViewById(R.id.ID_player1_name);
        final EditText player2_text = findViewById(R.id.ID_player2_name);
        final Button start_btn = findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1_name = player1_text.getText().toString();
                player2_name = player2_text.getText().toString();

                if (player1_name.isEmpty() || player2_name.isEmpty()) {
                    Toast.makeText(EnterNameActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(EnterNameActivity.this, MainActivity.class);
                    intent.putExtra("P1_name", player1_name);
                    intent.putExtra("P2_name", player2_name);
                    startActivity(intent);
                }
            }
        });

    }
}