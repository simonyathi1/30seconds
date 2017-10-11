package com.example.temp.a30seconds;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import model.Card;

import model.DataProvider;

import model.GameContract;
import model.Team;
import presenter.Presenter;


public class MainActivity extends AppCompatActivity implements GameContract.View {


    public static ArrayList<String> questionsList;
    public static TextView q1, q2, q3, q4, q5, questionNumber, timerBox, team1Score, team2Score, team3Score, team4Score, team5Score, currentTeam;
    public static Button buttonNext, buttonPrevious, buttonDone;
    public static CheckBox cb1, cb2, cb3, cb4, cb5;
    public static CountDownTimer cTimer = null;
    public static ProgressBar progress;
    private ArrayList<Team> teams;
    private ArrayList<String> players;
    public static int currentTeamPlaying;
    public static int count = 0;
    public Context ctx = MainActivity.this;
    private int seconds;
    public static Activity mainActivity;
    private Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        mPresenter = new Presenter(this);
        mPresenter.onViewLoad();

        //startTimer(30000);
        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mPresenter.onNextButtonClicked();

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //previousCard(teams.get(currentTeamPlaying), cards);
                //hideButtons();
                //hideCheckBoxes();
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onDone();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void loadCardData(ArrayList<String> questionsList) {
        currentTeamPlaying = 0;
        this.questionsList = questionsList;
    }

    @Override
    public void initViews() {
        q1 = (TextView) findViewById(R.id.tv_card_item_1);
        q2 = (TextView) findViewById(R.id.tv_card_item_2);
        q3 = (TextView) findViewById(R.id.tv_card_item_3);
        q4 = (TextView) findViewById(R.id.tv_card_item_4);
        q5 = (TextView) findViewById(R.id.tv_card_item_5);

        cb1 = (CheckBox) findViewById(R.id.cb_checkbox_1);
        cb2 = (CheckBox) findViewById(R.id.cb_checkbox_2);
        cb3 = (CheckBox) findViewById(R.id.cb_checkbox_3);
        cb4 = (CheckBox) findViewById(R.id.cb_checkbox_4);
        cb5 = (CheckBox) findViewById(R.id.cb_checkbox_5);
        questionNumber = (TextView) findViewById(R.id.tv_question_card_number);
        timerBox = (TextView) findViewById(R.id.tv_timer);
        team1Score = (TextView) findViewById(R.id.tv_team_1);
        team2Score = (TextView) findViewById(R.id.tv_team_2);
        team3Score = (TextView) findViewById(R.id.tv_team_3);
        team4Score = (TextView) findViewById(R.id.tv_team_4);
        team5Score = (TextView) findViewById(R.id.tv_team_5);
        currentTeam = (TextView) findViewById(R.id.tv_board_size);
        buttonNext = (Button) findViewById(R.id.btn_next);
        buttonDone = (Button) findViewById(R.id.btn_done);
        buttonPrevious = (Button) findViewById(R.id.btn_previous);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("30 seconds");
        questionNumber.setText(String.valueOf(getIntent().getIntExtra("BOARD_SIZE", 0)));


    }

    @Override
    public void initProgressBar() {
        progress = (ProgressBar) findViewById(R.id.pb_progress_bar);
        progress.setMax(30);
        progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this.ctx, R.color.colorPrimary),android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void initialTeam() {
        teams.get(currentTeamPlaying).getRound().setRoundNumber(teams.get(currentTeamPlaying).getRound().getRoundNumber() + 1);
        setColors(teams.get(currentTeamPlaying));
    }

    @Override
    public void yesClickedOnBeforeNextDialog(final ArrayList<Card> cards) {

        if(hasWinner(teams, getIntent().getIntExtra("BOARD_SIZE", 0))==false) {
            currentTeamPlaying = nextTurn(currentTeamPlaying, getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));
            teams.get(currentTeamPlaying).getRound().setRoundNumber(teams.get(currentTeamPlaying).getRound().getRoundNumber() + 1);
            setColors(teams.get(currentTeamPlaying));
            nextCard(cards);
            startTimer(30000);
            buttonDone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void cancelClickedOnBeforeNextDialog() {
        cb1.setVisibility(View.VISIBLE);
        cb2.setVisibility(View.VISIBLE);
        cb3.setVisibility(View.VISIBLE);
        cb4.setVisibility(View.VISIBLE);
        cb5.setVisibility(View.VISIBLE);
    }

    @Override
    public void scoreDisplayer() {
        displayScore(currentTeamPlaying, teams.get(currentTeamPlaying));
    }

    @Override
    public void displayInitialCard(ArrayList<Card> cards) {
        displayCard(cards.get(count));
    }

    @Override
    public void teamsLoader(DataProvider dataProvider) {

        teams = dataProvider.getTeams();
        if (getIntent().getIntExtra("NUMBER_OF_TEAMS", 0) == 1) {
            String[] teamNames = {getIntent().getStringExtra("1")};
            int[] numOfPlayers = {getIntent().getIntExtra("p1", 0)};
            hideTeamScoreViews(getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));

            players = new ArrayList<>();

            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                } else {
                    for (int j = 0; j < numOfPlayers[i]; j++) {
                        players.add("Player " + (j + 1));
                    }
                }

                teams.add(new Team(players, teamNames[i], 0));

            }

        } else if (getIntent().getIntExtra("NUMBER_OF_TEAMS", 0) == 2) {
            String[] teamNames = {getIntent().getStringExtra("1"), getIntent().getStringExtra("2")};
            int[] numOfPlayers = {getIntent().getIntExtra("p1", 0), getIntent().getIntExtra("p2", 0)};
            hideTeamScoreViews(getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));
            players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                } else {
                    for (int j = 0; j < numOfPlayers[i]; j++) {
                        players.add("Player " + (j + 1));
                    }
                }

                teams.add(new Team(players, teamNames[i], 0));
                players = new ArrayList<>();

            }
        } else if (getIntent().getIntExtra("NUMBER_OF_TEAMS", 0) == 3) {
            String[] teamNames = {getIntent().getStringExtra("1"), getIntent().getStringExtra("2"), getIntent().getStringExtra("3")};
            int[] numOfPlayers = {getIntent().getIntExtra("p1", 0), getIntent().getIntExtra("p2", 0), getIntent().getIntExtra("p3", 0)};
            hideTeamScoreViews(getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));

            players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                } else {
                    for (int j = 0; j < numOfPlayers[i]; j++) {
                        players.add("Player " + (j + 1));
                    }
                }

                teams.add(new Team(players, teamNames[i], 0));

            }
        } else if (getIntent().getIntExtra("NUMBER_OF_TEAMS", 0) == 4) {
            String[] teamNames = {getIntent().getStringExtra("1"), getIntent().getStringExtra("2"), getIntent().getStringExtra("3"), getIntent().getStringExtra("4")};
            int[] numOfPlayers = {getIntent().getIntExtra("p1", 0), getIntent().getIntExtra("p2", 0), getIntent().getIntExtra("p3", 0), getIntent().getIntExtra("p4", 0)};
            hideTeamScoreViews(getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));
            players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                } else {
                    for (int j = 0; j < numOfPlayers[i]; j++) {
                        players.add("Player " + (j + 1));
                    }
                }

                teams.add(new Team(players, teamNames[i], 0));

            }
        } else if (getIntent().getIntExtra("NUMBER_OF_TEAMS", 0) == 5) {
            String[] teamNames = {getIntent().getStringExtra("1"), getIntent().getStringExtra("2"), getIntent().getStringExtra("3"), getIntent().getStringExtra("4"), getIntent().getStringExtra("5")};
            int[] numOfPlayers = {getIntent().getIntExtra("p1", 0), getIntent().getIntExtra("p2", 0), getIntent().getIntExtra("p3", 0), getIntent().getIntExtra("p4", 0), getIntent().getIntExtra("p5", 0)};
            players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                } else {
                    for (int j = 0; j < numOfPlayers[i]; j++) {
                        players.add("Player " + (j + 1));
                    }
                }

                teams.add(new Team(players, teamNames[i], 0));

            }
        }

        currentTeam.setText(teams.get(currentTeamPlaying).getName());

    }

    public void hideTeamScoreViews(int i) {
        switch (i) {
            case 1:
                team2Score.setVisibility(View.GONE);
                team3Score.setVisibility(View.GONE);
                team4Score.setVisibility(View.GONE);
                team5Score.setVisibility(View.GONE);
                break;
            case 2:
                team3Score.setVisibility(View.GONE);
                team4Score.setVisibility(View.GONE);
                team5Score.setVisibility(View.GONE);
                break;
            case 3:
                team4Score.setVisibility(View.GONE);
                team5Score.setVisibility(View.GONE);
                break;
            case 4:
                team5Score.setVisibility(View.GONE);
                break;


        }
    }

    @Override
    public void hideButtons() {
        buttonPrevious.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showButtons() {
        //buttonPrevious.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideCheckBoxes() {

        cb1.setVisibility(View.INVISIBLE);
        cb2.setVisibility(View.INVISIBLE);
        cb3.setVisibility(View.INVISIBLE);
        cb4.setVisibility(View.INVISIBLE);
        cb5.setVisibility(View.INVISIBLE);


    }

    @Override
    public void showCheckBoxes() {

        cb1.setVisibility(View.VISIBLE);
        cb2.setVisibility(View.VISIBLE);
        cb3.setVisibility(View.VISIBLE);
        cb4.setVisibility(View.VISIBLE);
        cb5.setVisibility(View.VISIBLE);

        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
    }

    public void displayScore(int turn, Team team) {

        team.getRound().setPoints(scoreCounter());
        team.setTotalPoints(team.getTotalPoints() + team.getRound().getPoints());

        String totalPoints = "" + team.getTotalPoints();
        if (turn == 0) {
            team1Score.setText(totalPoints);


        } else if (turn == 1) {
            team2Score.setText(totalPoints);

        } else if (turn == 2) {
            team3Score.setText(totalPoints);

        } else if (turn == 3) {
            team4Score.setText(totalPoints);

        } else if (turn == 4) {
            team5Score.setText(totalPoints);

        }
        //currentTeam.setText(team.getName());

    }

    public int scoreCounter() {
        int newScore = 0;
        if (cb1.isChecked())
            newScore = newScore + 1;
        if (cb2.isChecked())
            newScore = newScore + 1;
        if (cb3.isChecked())
            newScore = newScore + 1;
        if (cb4.isChecked())
            newScore = newScore + 1;
        if (cb5.isChecked())
            newScore = newScore + 1;

        return newScore;
    }

    public void hideDoneButton() {
        buttonDone.setVisibility(View.INVISIBLE);
    }

    public void setColors(Team team) {
        ActionBar ab = this.getSupportActionBar();
        View top, middle, bottom, middleTop;
        top = (View) findViewById(R.id.ll_team_details);
        middle = (View) findViewById(R.id.rl_card_items);
        bottom = (View) findViewById(R.id.rl_btn_view);
        middleTop = (View) findViewById(R.id.rl_middle);

        ab.setTitle(team.getName());

        ab.setSubtitle(team.getPlayerNames().get((team.getRound().getRoundNumber() % team.getPlayerNames().size())));
        currentTeam.setText(team.getName());

        if (team.getTotalPoints() % 2 == 0) {
            //ab.setBackgroundDrawable(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_blue));
            //ctx.setTheme(R.style.AppTheme);
            //top.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_blue));

            //middle.setBackgroundColor(ContextCompat.getColor(this.ctx, R.color.blue_card_color));
            //middleTop.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_blue));
            //bottom.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_blue));


        } else {
            //ab.setBackgroundDrawable(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_yellow));
            //top.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_yellow));
            //ctx.setTheme(R.style.AppTheme2);
            //middleTop.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_yellow));
            //middle.setBackgroundColor(ContextCompat.getColor(this.ctx, R.color.yellow_card_color));
            //bottom.setBackground(ContextCompat.getDrawable(this.ctx, R.drawable.action_bar_yellow));
        }
    }

    public static void displayCard(Card card) {

        q1.setText(card.getCrypt1());
        q2.setText(card.getCrypt2());
        q3.setText(card.getCrypt3());
        q4.setText(card.getCrypt4());
        q5.setText(card.getCrypt5());

    }

    public void previousCard(Team team, ArrayList<Card> cards) {

        stopTimer();
        startTimer(30000);
        if ((count - 1) >= 0) {
            count--;
            displayCard(cards.get(count));
            //questionNumber.setText("" + (count + 1));

        } else {
            count = cards.size() - 1;
            displayCard(cards.get(count));
            //questionNumber.setText("" + (count + 1));
        }
    }

    public static void nextCard(ArrayList<Card> cards) {

        if ((count + 1) < cards.size()) {
            count++;
            displayCard(cards.get(count));
            //questionNumber.setText("" + (count + 1));
        } else {
            count = 0;
            displayCard(cards.get(count));
            //questionNumber.setText("" + (count + 1));
        }
    }

    public static int nextTurn(int currentTeam, int numberOfTeams) {

        switch (numberOfTeams) {
            case 1:
                if (currentTeam == 0)
                    return 0;
                break;
            case 2:
                if (currentTeam == 0)
                    return 1;
                else if (currentTeam == 1)
                    return 0;
                break;
            case 3:
                if (currentTeam == 0)
                    return 1;
                else if (currentTeam == 1)
                    return 2;
                else if (currentTeam == 2)
                    return 0;
                break;
            case 4:
                if (currentTeam == 0)
                    return 1;
                else if (currentTeam == 1)
                    return 2;
                else if (currentTeam == 2)
                    return 3;
                else if (currentTeam == 3)
                    return 0;
                break;
            case 5:
                if (currentTeam == 0)
                    return 1;
                else if (currentTeam == 1)
                    return 2;
                else if (currentTeam == 2)
                    return 3;
                else if (currentTeam == 3)
                    return 4;
                else if (currentTeam == 4)
                    return 0;
                break;
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        mPresenter.onPause();
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("This action closes the game. Are you sure you want to exit?")
                .setTitle("Close Game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                //Main2Activity.activity.finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.onResume();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void beforeNextTurn() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Current team got " + scoreCounter() + "/5 correct.\n \nPress YES for next team.\ntimer will start instantly")
                .setTitle("Next Player Ready?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.onYesClickedOnBeforeNextDialog();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.onCancelClickedOnBeforeNextDialog();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void startTimer(int x) {
        cTimer = new CountDownTimer(x, 1000) {

            public void onTick(long millisUntilFinished) {
                timerBox.setText("" + millisUntilFinished / 1000);
                progress.setProgress((int) (millisUntilFinished / 1000));
                seconds = (int) (millisUntilFinished / 1000);
            }
            public void onFinish() {
                mPresenter.onDone();
            }
        };
        cTimer.start();
    }

    @Override
    public void stopTimer() {

        cTimer.cancel();
        MediaPlayer mp = MediaPlayer.create(ctx, R.raw.bell_ring);
        mp.start();
        MainActivity.timerBox.setText("--");
        MainActivity.progress.setProgress((0 / 1000));
    }

    @Override
    public void pauseTimer() {
        cTimer.cancel();
        //seconds = progress.getProgress();
    }

    @Override
    public void resumeTimer() {
        startTimer(seconds * 1000);
    }

    public boolean hasWinner(ArrayList<Team> teams, int target) {
        Intent winnerIntent = new Intent(MainActivity.this, Winner.class);
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTotalPoints() >= target) {
                winnerIntent.putExtra("WINNER", teams.get(i).getName());
                startActivity(winnerIntent);
                finish();
                return true;
            }
        }
        return false;
    }
}



