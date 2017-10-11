package presenter;

import com.example.temp.a30seconds.MainActivity;
import com.example.temp.a30seconds.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Card;
import model.DataProvider;
import model.GameContract;
import model.MyApp;

/**
 * Created by temp on 02/10/2017.
 */

public class Presenter implements GameContract.Actions {

    private final GameContract.View mView;
    private DataProvider dataProvider;
    private ArrayList<String> questionsList;
    private ArrayList<Card> cards;

    public Presenter(GameContract.View mView) {
        this.mView = mView;
        questionsList = questionsFileReader();
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

    public void onResume(){
        mView.resumeTimer();
    }
    public void onPause(){
        mView.pauseTimer();
    }
    private ArrayList<String> questionsFileReader() {
        ArrayList<String> questionsList = new ArrayList<>();


        try {
            InputStream inputStream = MainActivity.mainActivity.getResources().openRawResource(R.raw.questions_text);

            if (inputStream != null) {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                try {
                    while ((line = buffreader.readLine()) != null)
                        questionsList.add(line.toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            String error = "";
            error = e.getMessage();
        }
        return questionsList;
    }//done
}
