package com.example.temp.a30seconds;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Winner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        TextView winnerTV = (TextView) findViewById(R.id.tv_winner);
        winnerTV.setText(getIntent().getStringExtra("WINNER").toUpperCase() + " WINS!!!");
        Button newGame = (Button) findViewById(R.id.btn_new_game);
        Button closeGame = (Button) findViewById(R.id.btn_close_game);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newGameIntent = new Intent(Winner.this, Main2Activity.class);
                startActivity(newGameIntent);

            }
        });

        closeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(Winner.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("This action closes the game. Are you sure you want to exit?")
                        .setTitle("Close Game?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
