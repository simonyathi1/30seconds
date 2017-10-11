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
    }//done

    private String customUpper(String line){
        //this method leaves letters like c in McDONALDS small when changing to uppercase

        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < line.length();i++){
            if (!String.valueOf(line.charAt(i)).equals("<")){
                newLine.append(String.valueOf(line.charAt(i)).toUpperCase());
            }
            else {
                newLine.append(String.valueOf(line.charAt(i+1)).toLowerCase());
                i++;
                continue;

            }
        }
        return String.valueOf(newLine);
    }
}
