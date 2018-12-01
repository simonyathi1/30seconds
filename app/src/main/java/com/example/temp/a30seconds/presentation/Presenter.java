package com.example.temp.a30seconds.presentation;

import java.util.ArrayList;

import com.example.temp.a30seconds.model.Card;
import com.example.temp.a30seconds.model.DataProvider;


/**
 * Created by temp on 02/10/2017.
 */

public class Presenter implements GamePlayerPresenterInterface {

    private final GamePlayerPresenterInterface.View mView;
    private DataProvider dataProvider;
    private ArrayList<String> questionsList;
    private ArrayList<Card> cards;

    Presenter(GamePlayerPresenterInterface.View mView, ArrayList<String> questions) {
        this.mView = mView;
        questionsList = questions;
        dataProvider = new DataProvider(questionsList);
        cards = dataProvider.getCards();
    }

    @Override
    public void onNextButtonClicked() {
        mView.beforeNextTurn();

    }

    @Override
    public void onDone() {
        mView.showButtons();
        mView.showCheckBoxes();
        mView.stopTimer();
        mView.hideDoneButton();
    }

    @Override
    public void onViewLoad() {
        mView.initViews();
        mView.loadCardData(questionsList);
        mView.initProgressBar();
        mView.teamsLoader(dataProvider);
        mView.hideButtons();
        mView.hideCheckBoxes();

        mView.startTimer(30000);
        mView.initialTeam();
        mView.displayInitialCard(cards);
    }

    @Override
    public void onYesClickedOnBeforeNextDialog() {
        mView.scoreDisplayer();
        mView.hideButtons();
        mView.hideCheckBoxes();
        mView.yesClickedOnBeforeNextDialog(cards);
    }

    @Override
    public void onCancelClickedOnBeforeNextDialog() {
        mView.cancelClickedOnBeforeNextDialog();
        mView.showButtons();
    }

    public void onResume() {
        mView.resumeTimer();
    }

    public void onPause() {
        mView.pauseTimer();
    }

    @Override
    public int nextTurn(int currentTeam, int numberOfTeams) {

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
}
