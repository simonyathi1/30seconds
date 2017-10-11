package com.example.temp.a30seconds;

import android.app.Activity;
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

import model.DataProvider;


public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText numberOfTeams, team1, team2, team3, team4, team5, numPlayers1, numPlayers2, numPlayers3, numPlayers4, numPlayers5;
    TextView tvTeam1, tvTeam2, tvTeam3, tvTeam4, tvTeam5, tvNumPlayers1, tvNumPlayers2, tvNumPlayers3, tvNumPlayers4, tvNumPlayers5;
    CardView cvTeam1, cvTeam2, cvTeam3, cvTeam4, cvTeam5;
    Button next;
    int numberOfTeamsInt = 0;
    int boardSize = 10;
    String team1Name = "Team 1", team2Name = "Team 2", team3Name = "Team 3", team4Name = "Team4", team5Name = "Team 5";
    int players1 = -1, players2 = -1, players3 = -1, players4 = -1, players5 = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        numberOfTeams = (EditText) findViewById(R.id.et_number_of_teams);
        team1 = (EditText) findViewById(R.id.et_team_name_1);
        team2 = (EditText) findViewById(R.id.et_team_name_2);
        team3 = (EditText) findViewById(R.id.et_team_name_3);
        team4 = (EditText) findViewById(R.id.et_team_name_4);
        team5 = (EditText) findViewById(R.id.et_team_name_5);
        numPlayers1 = (EditText) findViewById(R.id.et_number_of_players_1);
        numPlayers2 = (EditText) findViewById(R.id.et_number_of_players_2);
        numPlayers3 = (EditText) findViewById(R.id.et_number_of_players_3);
        numPlayers4 = (EditText) findViewById(R.id.et_number_of_players_4);
        numPlayers5 = (EditText) findViewById(R.id.et_number_of_players_5);

        tvTeam1 = (TextView) findViewById(R.id.tv_team_name_1);
        tvTeam2 = (TextView) findViewById(R.id.tv_team_name_2);
        tvTeam3 = (TextView) findViewById(R.id.tv_team_name_3);
        tvTeam4 = (TextView) findViewById(R.id.tv_team_name_4);
        tvTeam5 = (TextView) findViewById(R.id.tv_team_name_5);
        tvNumPlayers1 = (TextView) findViewById(R.id.tv_number_of_players_1);
        tvNumPlayers2 = (TextView) findViewById(R.id.tv_number_of_players_2);
        tvNumPlayers3 = (TextView) findViewById(R.id.tv_number_of_players_3);
        tvNumPlayers4 = (TextView) findViewById(R.id.tv_number_of_players_4);
        tvNumPlayers5 = (TextView) findViewById(R.id.tv_number_of_players_5);

        cvTeam1 = (CardView)findViewById(R.id.cv_team_1);
        cvTeam2 = (CardView)findViewById(R.id.cv_team_2);
        cvTeam3 = (CardView)findViewById(R.id.cv_team_3);
        cvTeam4 = (CardView)findViewById(R.id.cv_team_4);
        cvTeam5 = (CardView)findViewById(R.id.cv_team_5);

        next = (Button) findViewById(R.id.btn_start);

        tvTeam1.setVisibility(View.INVISIBLE);
        team1.setVisibility(View.INVISIBLE);
        tvTeam2.setVisibility(View.INVISIBLE);
        team2.setVisibility(View.INVISIBLE);
        tvTeam3.setVisibility(View.INVISIBLE);
        team3.setVisibility(View.INVISIBLE);
        tvTeam4.setVisibility(View.INVISIBLE);
        team4.setVisibility(View.INVISIBLE);
        tvTeam5.setVisibility(View.INVISIBLE);
        team5.setVisibility(View.INVISIBLE);

        cvTeam1.setVisibility(View.INVISIBLE);
        cvTeam2.setVisibility(View.INVISIBLE);
        cvTeam3.setVisibility(View.INVISIBLE);
        cvTeam4.setVisibility(View.INVISIBLE);
        cvTeam5.setVisibility(View.INVISIBLE);

        numPlayers1.setVisibility(View.INVISIBLE);
        numPlayers2.setVisibility(View.INVISIBLE);
        numPlayers3.setVisibility(View.INVISIBLE);
        numPlayers4.setVisibility(View.INVISIBLE);
        numPlayers5.setVisibility(View.INVISIBLE);

        tvNumPlayers1.setVisibility(View.INVISIBLE);
        tvNumPlayers2.setVisibility(View.INVISIBLE);
        tvNumPlayers3.setVisibility(View.INVISIBLE);
        tvNumPlayers4.setVisibility(View.INVISIBLE);
        tvNumPlayers5.setVisibility(View.INVISIBLE);

        final Intent intent = new Intent(Main2Activity.this, MainActivity.class);

        numberOfTeams.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPlayers1.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPlayers2.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPlayers3.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPlayers4.setInputType(InputType.TYPE_CLASS_NUMBER);
        numPlayers5.setInputType(InputType.TYPE_CLASS_NUMBER);



        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        toolbar.setTitle("30 seconds");

        ActionBar ab = this.getSupportActionBar();
        assert ab != null;
        ab.setTitle("The 30 Seconds Game");

        final Spinner spinner = (Spinner) findViewById(R.id.game_size_spinner);
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

                        tvTeam1.setVisibility(View.VISIBLE);
                        team1.setVisibility(View.VISIBLE);

                        cvTeam1.setVisibility(View.VISIBLE);
                        cvTeam2.setVisibility(View.INVISIBLE);
                        cvTeam3.setVisibility(View.INVISIBLE);
                        cvTeam4.setVisibility(View.INVISIBLE);
                        cvTeam5.setVisibility(View.INVISIBLE);

                        tvTeam2.setVisibility(View.INVISIBLE);
                        team2.setVisibility(View.INVISIBLE);
                        tvTeam3.setVisibility(View.INVISIBLE);
                        team3.setVisibility(View.INVISIBLE);
                        tvTeam4.setVisibility(View.INVISIBLE);
                        team4.setVisibility(View.INVISIBLE);
                        tvTeam5.setVisibility(View.INVISIBLE);
                        team5.setVisibility(View.INVISIBLE);

                        numPlayers1.setVisibility(View.VISIBLE);
                        numPlayers2.setVisibility(View.INVISIBLE);
                        numPlayers3.setVisibility(View.INVISIBLE);
                        numPlayers4.setVisibility(View.INVISIBLE);
                        numPlayers5.setVisibility(View.INVISIBLE);

                        tvNumPlayers1.setVisibility(View.VISIBLE);
                        tvNumPlayers2.setVisibility(View.INVISIBLE);
                        tvNumPlayers3.setVisibility(View.INVISIBLE);
                        tvNumPlayers4.setVisibility(View.INVISIBLE);
                        tvNumPlayers5.setVisibility(View.INVISIBLE);

                    }
                    if (Integer.parseInt(numberOfTeams.getText().toString()) == 2) {
                        numberOfTeamsInt = 2;

                        tvTeam1.setVisibility(View.VISIBLE);
                        team1.setVisibility(View.VISIBLE);
                        tvTeam2.setVisibility(View.VISIBLE);
                        team2.setVisibility(View.VISIBLE);
                        tvTeam3.setVisibility(View.INVISIBLE);
                        team3.setVisibility(View.INVISIBLE);
                        tvTeam4.setVisibility(View.INVISIBLE);
                        team4.setVisibility(View.INVISIBLE);
                        tvTeam5.setVisibility(View.INVISIBLE);
                        team5.setVisibility(View.INVISIBLE);

                        numPlayers1.setVisibility(View.VISIBLE);
                        numPlayers2.setVisibility(View.VISIBLE);
                        numPlayers3.setVisibility(View.INVISIBLE);
                        numPlayers4.setVisibility(View.INVISIBLE);
                        numPlayers5.setVisibility(View.INVISIBLE);

                        tvNumPlayers1.setVisibility(View.VISIBLE);
                        tvNumPlayers2.setVisibility(View.VISIBLE);
                        tvNumPlayers3.setVisibility(View.INVISIBLE);
                        tvNumPlayers4.setVisibility(View.INVISIBLE);
                        tvNumPlayers5.setVisibility(View.INVISIBLE);

                        cvTeam1.setVisibility(View.VISIBLE);
                        cvTeam2.setVisibility(View.VISIBLE);
                        cvTeam3.setVisibility(View.INVISIBLE);
                        cvTeam4.setVisibility(View.INVISIBLE);
                        cvTeam5.setVisibility(View.INVISIBLE);
                    }
                    if (Integer.parseInt(numberOfTeams.getText().toString()) == 3) {
                        numberOfTeamsInt = 3;

                        tvTeam1.setVisibility(View.VISIBLE);
                        team1.setVisibility(View.VISIBLE);
                        tvTeam2.setVisibility(View.VISIBLE);
                        team2.setVisibility(View.VISIBLE);
                        tvTeam3.setVisibility(View.VISIBLE);
                        team3.setVisibility(View.VISIBLE);
                        tvTeam4.setVisibility(View.INVISIBLE);
                        team4.setVisibility(View.INVISIBLE);
                        tvTeam5.setVisibility(View.INVISIBLE);
                        team5.setVisibility(View.INVISIBLE);

                        numPlayers1.setVisibility(View.VISIBLE);
                        numPlayers2.setVisibility(View.VISIBLE);
                        numPlayers3.setVisibility(View.VISIBLE);
                        numPlayers4.setVisibility(View.INVISIBLE);
                        numPlayers5.setVisibility(View.INVISIBLE);

                        tvNumPlayers1.setVisibility(View.VISIBLE);
                        tvNumPlayers2.setVisibility(View.VISIBLE);
                        tvNumPlayers3.setVisibility(View.VISIBLE);
                        tvNumPlayers4.setVisibility(View.INVISIBLE);
                        tvNumPlayers5.setVisibility(View.INVISIBLE);

                        cvTeam1.setVisibility(View.VISIBLE);
                        cvTeam2.setVisibility(View.VISIBLE);
                        cvTeam3.setVisibility(View.VISIBLE);
                        cvTeam4.setVisibility(View.INVISIBLE);
                        cvTeam5.setVisibility(View.INVISIBLE);
                    }
                    if (Integer.parseInt(numberOfTeams.getText().toString()) == 4) {
                        numberOfTeamsInt = 4;

                        tvTeam1.setVisibility(View.VISIBLE);
                        team1.setVisibility(View.VISIBLE);
                        tvTeam2.setVisibility(View.VISIBLE);
                        team2.setVisibility(View.VISIBLE);
                        tvTeam3.setVisibility(View.VISIBLE);
                        team3.setVisibility(View.VISIBLE);
                        tvTeam4.setVisibility(View.VISIBLE);
                        team4.setVisibility(View.VISIBLE);
                        tvTeam5.setVisibility(View.INVISIBLE);
                        team5.setVisibility(View.INVISIBLE);

                        numPlayers1.setVisibility(View.VISIBLE);
                        numPlayers2.setVisibility(View.VISIBLE);
                        numPlayers3.setVisibility(View.VISIBLE);
                        numPlayers4.setVisibility(View.VISIBLE);
                        numPlayers5.setVisibility(View.INVISIBLE);

                        tvNumPlayers1.setVisibility(View.VISIBLE);
                        tvNumPlayers2.setVisibility(View.VISIBLE);
                        tvNumPlayers3.setVisibility(View.VISIBLE);
                        tvNumPlayers4.setVisibility(View.VISIBLE);
                        tvNumPlayers5.setVisibility(View.INVISIBLE);

                        cvTeam1.setVisibility(View.VISIBLE);
                        cvTeam2.setVisibility(View.VISIBLE);
                        cvTeam3.setVisibility(View.VISIBLE);
                        cvTeam4.setVisibility(View.VISIBLE);
                        cvTeam5.setVisibility(View.INVISIBLE);
                    }
                    if (Integer.parseInt(numberOfTeams.getText().toString()) == 5) {
                        numberOfTeamsInt = 5;

                        tvTeam1.setVisibility(View.VISIBLE);
                        team1.setVisibility(View.VISIBLE);
                        tvTeam2.setVisibility(View.VISIBLE);
                        team2.setVisibility(View.VISIBLE);
                        tvTeam3.setVisibility(View.VISIBLE);
                        team3.setVisibility(View.VISIBLE);
                        tvTeam4.setVisibility(View.VISIBLE);
                        team4.setVisibility(View.VISIBLE);
                        tvTeam5.setVisibility(View.VISIBLE);
                        team5.setVisibility(View.VISIBLE);

                        numPlayers1.setVisibility(View.VISIBLE);
                        numPlayers2.setVisibility(View.VISIBLE);
                        numPlayers3.setVisibility(View.VISIBLE);
                        numPlayers4.setVisibility(View.VISIBLE);
                        numPlayers5.setVisibility(View.VISIBLE);

                        tvNumPlayers1.setVisibility(View.VISIBLE);
                        tvNumPlayers2.setVisibility(View.VISIBLE);
                        tvNumPlayers3.setVisibility(View.VISIBLE);
                        tvNumPlayers4.setVisibility(View.VISIBLE);
                        tvNumPlayers5.setVisibility(View.VISIBLE);

                        cvTeam1.setVisibility(View.VISIBLE);
                        cvTeam2.setVisibility(View.VISIBLE);
                        cvTeam3.setVisibility(View.VISIBLE);
                        cvTeam4.setVisibility(View.VISIBLE);
                        cvTeam5.setVisibility(View.VISIBLE);
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
                //String s = team1.getText().toString();
                if (team1.getText().toString().length() > 0)
                    team1Name = team1.getText().toString();
                if (team2.getText().toString().length() > 0)
                    team2Name = team2.getText().toString();
                if (team3.getText().toString().length() > 0)
                    team3Name = team3.getText().toString();
                if (team4.getText().toString().length() > 0)
                    team4Name = team4.getText().toString();
                if (team5.getText().toString().length() > 0)
                    team5Name = team5.getText().toString();

                if (numPlayers1.getText().toString().length() > 0)
                    players1 = Integer.parseInt(numPlayers1.getText().toString());
                if (numPlayers2.getText().toString().length() > 0)
                    players2 = Integer.parseInt(numPlayers2.getText().toString());
                if (numPlayers3.getText().toString().length() > 0)
                    players3 = Integer.parseInt(numPlayers3.getText().toString());
                if (numPlayers4.getText().toString().length() > 0)
                    players4 = Integer.parseInt(numPlayers4.getText().toString());
                if (numPlayers5.getText().toString().length() > 0)
                    players5 = Integer.parseInt(numPlayers5.getText().toString());

                if (numberOfTeamsInt == 1) {
                    intent.putExtra("1", team1Name);
                    intent.putExtra("p1", players1);

                } else if (numberOfTeamsInt == 2) {
                    intent.putExtra("1", team1Name);
                    intent.putExtra("2", team2Name);
                    intent.putExtra("p1", players1);
                    intent.putExtra("p2", players2);

                } else if (numberOfTeamsInt == 3) {
                    intent.putExtra("1", team1Name);
                    intent.putExtra("2", team2Name);
                    intent.putExtra("3", team3Name);

                    intent.putExtra("p1", players1);
                    intent.putExtra("p2", players2);
                    intent.putExtra("p3", players3);

                } else if (numberOfTeamsInt == 4) {
                    intent.putExtra("1", team1Name);
                    intent.putExtra("2", team2Name);
                    intent.putExtra("3", team3Name);
                    intent.putExtra("4", team4Name);

                    intent.putExtra("p1", players1);
                    intent.putExtra("p2", players2);
                    intent.putExtra("p3", players3);
                    intent.putExtra("p4", players4);

                } else if (numberOfTeamsInt == 5) {
                    intent.putExtra("1", team1Name);
                    intent.putExtra("2", team2Name);
                    intent.putExtra("3", team3Name);
                    intent.putExtra("4", team4Name);
                    intent.putExtra("5", team5Name);

                    intent.putExtra("p1", players1);
                    intent.putExtra("p2", players2);
                    intent.putExtra("p3", players3);
                    intent.putExtra("p4", players4);
                    intent.putExtra("p5", players5);
                } else {
                    numberOfTeams.setError("Maximum number of teams is 5");
                }

                intent.putExtra("NUMBER_OF_TEAMS", numberOfTeamsInt);
                intent.putExtra("BOARD_SIZE", boardSize);

                if (players1 > 15) {
                    numPlayers1.setError("Restricted to 15 players Max");

                } else if (players2 > 15) {
                    numPlayers2.setError("Restricted to 15 players Max");

                } else if (players3 > 15) {
                    numPlayers3.setError("Restricted to 15 players Max");

                } else if (players4 > 15) {
                    numPlayers4.setError("Restricted to 15 players Max");

                } else if (players5 > 15) {
                    numPlayers5.setError("Restricted to 15 players Max");

                } else if (numberOfTeams.getText().toString().length() != 0 && (Integer.parseInt(numberOfTeams.getText().toString()) > 0 && Integer.parseInt(numberOfTeams.getText().toString()) < 6)) {
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);

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
