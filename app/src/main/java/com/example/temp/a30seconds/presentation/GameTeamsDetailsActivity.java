package com.example.temp.a30seconds.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.temp.a30seconds.R;
import com.example.temp.a30seconds.model.DataProvider;


public class GameTeamsDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText numberOfTeams;
    private Button next;
    private int numberOfTeamsInt = 0;
    private int boardSize = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_teams_details);

        numberOfTeams = findViewById(R.id.et_number_of_teams);


        next = findViewById(R.id.btn_start);

        numberOfTeams.setInputType(InputType.TYPE_CLASS_NUMBER);

        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        toolbar.setTitle("30 seconds");

        ActionBar ab = this.getSupportActionBar();
        assert ab != null;
        ab.setTitle("The 30 Seconds Game");

        final Spinner spinner = findViewById(R.id.game_size_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.game_sizes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        numberOfTeams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!DataProvider.isNumeric(numberOfTeams.getText().toString())) {
                    numberOfTeams.clearComposingText();
                    if (numberOfTeams.getText().toString().length() > 0)
                        numberOfTeams.setError("Number 0f teams is numeric");
                } else if (Integer.parseInt(numberOfTeams.getText().toString()) > 5 || Integer.parseInt(numberOfTeams.getText().toString()) <= 0) {
                    numberOfTeams.setError("Restricted to 5 teams Max");

                } else {
                    if (Integer.parseInt(numberOfTeams.getText().toString()) == 1) {
                        numberOfTeams.setError("Are you sure you want to play alone");
                        numberOfTeamsInt = 1;
                    }else {
                        numberOfTeamsInt = Integer.parseInt(numberOfTeams.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final Intent intent = new Intent(GameTeamsDetailsActivity.this, GamePlayActivity.class);
                intent.putExtra("NUMBER_OF_TEAMS", numberOfTeamsInt);
                intent.putExtra("BOARD_SIZE", boardSize);

                if (numberOfTeams.getText().toString().length() != 0 && (Integer.parseInt(numberOfTeams.getText().toString()) > 0 && Integer.parseInt(numberOfTeams.getText().toString()) < 6)) {
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameTeamsDetailsActivity.this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("First player has to be ready. timer starts instantly on 'Yes'")
                            .setTitle("Start Game?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(intent);
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
                } else {
                    numberOfTeams.setError("Maximum number of teams is 5");
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 1)
            boardSize = 40;
        else if (i == 2)
            boardSize = 55;
        else if (i == 3) boardSize = 70;
        else boardSize = 40;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        boardSize = 40;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
