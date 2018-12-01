package com.example.temp.a30seconds.presentation;

import com.example.temp.a30seconds.model.Card;
import com.example.temp.a30seconds.model.DataProvider;

import java.util.ArrayList;

/**
 * Created by temp on 02/10/2017.
 */

public interface GamePlayerPresenterInterface {
    interface View{
        void beforeNextTurn();
        void startTimer(int i);
        void stopTimer();
        void pauseTimer();
        void resumeTimer();
        void showButtons();
        void showCheckBoxes();
        void hideDoneButton();
        void loadCardData(ArrayList<String> arrayList);
        void initViews();
        void initProgressBar();
        void teamsLoader(DataProvider dataProvider);
        void hideButtons();
        void hideCheckBoxes();
        void displayInitialCard(ArrayList<Card>arrayList);
        void initialTeam();
        void yesClickedOnBeforeNextDialog(ArrayList<Card>arrayList);
        void cancelClickedOnBeforeNextDialog();
        void scoreDisplayer();
    }

        void onNextButtonClicked();
        void onDone();
        void onViewLoad();
        void onYesClickedOnBeforeNextDialog();
        void onCancelClickedOnBeforeNextDialog();

        int nextTurn(int currentTeam, int numberOfTeams);
}
