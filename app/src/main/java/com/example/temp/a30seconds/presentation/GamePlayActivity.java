package com.example.temp.a30seconds.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import com.example.temp.a30seconds.R;
import com.example.temp.a30seconds.model.Card;

import com.example.temp.a30seconds.model.DataProvider;

import com.example.temp.a30seconds.model.Team;

import static com.example.temp.a30seconds.utils.GameUtils.customUpper;


public class GamePlayActivity extends SuperActivity implements GamePlayerPresenterInterface.View {


    public static ArrayList<String> questionsList;
    public TextView q1, q2, q3, q4, q5, questionNumber, timerBox, team1Score, team2Score, team3Score, team4Score, team5Score, currentTeam;
    public Button buttonNext, buttonPrevious, buttonDone;
    public CheckBox cb1, cb2, cb3, cb4, cb5;
    public static CountDownTimer cTimer = null;
    public ProgressBar progress;
    private ArrayList<Team> teams;
    private ArrayList<String> players;
    public static int currentTeamPlaying;
    public static int count = 0;
    public Context ctx = GamePlayActivity.this;
    private int seconds;
    public Activity mainActivity;
    private Presenter mPresenter;
    private String[] defaultTeam1Names = {"Team 1", "Team 2", "Team 3", "Team4", "Team 5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        mainActivity = this;
        mPresenter = new Presenter(this, questionsFileReader());
        mPresenter.onViewLoad();

        //startTimer(30000);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onNextButtonClicked();
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
    public void loadCardData(ArrayList<String> questionsList) {
        currentTeamPlaying = 0;
        GamePlayActivity.questionsList = questionsList;
    }

    @Override
    public void initViews() {
        q1 = findViewById(R.id.tv_card_item_1);
        q2 = findViewById(R.id.tv_card_item_2);
        q3 = findViewById(R.id.tv_card_item_3);
        q4 = findViewById(R.id.tv_card_item_4);
        q5 = findViewById(R.id.tv_card_item_5);

        cb1 = findViewById(R.id.cb_checkbox_1);
        cb2 = findViewById(R.id.cb_checkbox_2);
        cb3 = findViewById(R.id.cb_checkbox_3);
        cb4 = findViewById(R.id.cb_checkbox_4);
        cb5 = findViewById(R.id.cb_checkbox_5);

        questionNumber = findViewById(R.id.tv_question_card_number);

        timerBox = findViewById(R.id.tv_timer);
        team1Score = findViewById(R.id.tv_team_1);
        team2Score = findViewById(R.id.tv_team_2);
        team3Score = findViewById(R.id.tv_team_3);
        team4Score = findViewById(R.id.tv_team_4);
        team5Score = findViewById(R.id.tv_team_5);
        currentTeam = findViewById(R.id.tv_board_size);
        buttonNext = findViewById(R.id.btn_next);
        buttonDone = findViewById(R.id.btn_done);
        buttonPrevious = findViewById(R.id.btn_previous);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("30 seconds");
        questionNumber.setText(String.valueOf(getIntent().getIntExtra("BOARD_SIZE", 0)));
    }

    @Override
    public void initProgressBar() {
        progress = findViewById(R.id.pb_progress_bar);
        progress.setMax(30);
        progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this.ctx, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void initialTeam() {
        teams.get(currentTeamPlaying).getRound().setRoundNumber(teams.get(currentTeamPlaying).getRound().getRoundNumber() + 1);
        setToolbarTeamPlaying(teams.get(currentTeamPlaying));
    }

    @Override
    public void yesClickedOnBeforeNextDialog(final ArrayList<Card> cards) {
        if (!hasWinner(teams, getIntent().getIntExtra("BOARD_SIZE", 0))) {
            currentTeamPlaying = mPresenter.nextTurn(currentTeamPlaying, getIntent().getIntExtra("NUMBER_OF_TEAMS", 0));
            teams.get(currentTeamPlaying).getRound().setRoundNumber(teams.get(currentTeamPlaying).getRound().getRoundNumber() + 1);
            setToolbarTeamPlaying(teams.get(currentTeamPlaying));
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
        int numberOfTeams = getIntent().getIntExtra("NUMBER_OF_TEAMS", 0);

            String[] teamNames = new String[numberOfTeams];
            int[] numOfPlayers = new int[numberOfTeams];
            for(int i = 0; i < numberOfTeams;i++){
                teamNames[i] = defaultTeam1Names[i];
                numOfPlayers[i] = -1;
            }
            hideTeamScoreViews(numberOfTeams);
            players = new ArrayList<>();
            for (int i = 0; i < numOfPlayers.length; i++) {
                if (numOfPlayers[i] == -1) {
                    players.add("Player");
                }
                teams.add(new Team(players, teamNames[i], 0));
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

    public void setToolbarTeamPlaying(Team team) {
        ActionBar ab = this.getSupportActionBar();

        assert ab != null;
        ab.setTitle(team.getName());
        ab.setSubtitle(team.getPlayerNames().get((team.getRound().getRoundNumber() % team.getPlayerNames().size())));
        currentTeam.setText(team.getName());
    }

    public void displayCard(Card card) {
        q1.setText(card.getCrypt1());
        q2.setText(card.getCrypt2());
        q3.setText(card.getCrypt3());
        q4.setText(card.getCrypt4());
        q5.setText(card.getCrypt5());
    }

    public void nextCard(ArrayList<Card> cards) {
        if ((count + 1) < cards.size()) {
            count++;
            displayCard(cards.get(count));
        } else {
            count = 0;
            displayCard(cards.get(count));
        }
    }

    @Override
    public void onBackPressed() {
        closeGameDialog(mPresenter);
    }

    @Override
    public void beforeNextTurn() {
        nextPlayerDialog(mPresenter, scoreCounter());
    }

    @Override
    public void startTimer(int x) {
        cTimer = new CountDownTimer(x, 1000) {

            public void onTick(long millisUntilFinished) {
                timerBox.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000));
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
        timerBox.setText("--");
        progress.setProgress((0 / 1000));
    }

    private ArrayList<String> questionsFileReader() {
        ArrayList<String> questionsList = new ArrayList<>();

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.questions_text);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null)
                        questionsList.add(customUpper(line));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            String error = "";
            error = e.getMessage();
        }
        return questionsList;
    }

    @Override
    public void pauseTimer() {
        cTimer.cancel();
    }

    @Override
    public void resumeTimer() {
        startTimer(seconds * 1000);
    }

    public boolean hasWinner(ArrayList<Team> teams, int target) {
        Intent winnerIntent = new Intent(GamePlayActivity.this, GameWinnerActivity.class);
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
}



