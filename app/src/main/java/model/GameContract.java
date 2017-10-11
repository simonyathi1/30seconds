package model;

import java.util.ArrayList;

/**
 * Created by temp on 02/10/2017.
 */

public interface GameContract {
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

    interface Actions{
        void onNextButtonClicked();
        void onDone();
        void onViewLoad();
        void onYesClickedOnBeforeNextDialog();
        void onCancelClickedOnBeforeNextDialog();
    }
}
