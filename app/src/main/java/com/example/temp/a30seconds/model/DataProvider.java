package com.example.temp.a30seconds.model;

import java.util.ArrayList;
import java.util.Collections;

import static android.text.TextUtils.isEmpty;

/**
 * Created by temp on 18/09/2017.
 */

public class DataProvider {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Team> teams;

    public DataProvider(ArrayList<String> al) {
        Collections.shuffle(al);

        String[] cardQuestions = null;
        for (int i = 0; i < al.size(); i++) {
            if (i % 5 == 0) {
                cardQuestions = new String[5];
                cardQuestions[i % 5] = (al.get(i));
                continue;
            }
            while (i % 5 != 0) {
                if (cardQuestions != null) {
                    cardQuestions[i % 5] = (al.get(i));
                }
                i++;
            }
            if (cardQuestions != null) {
                cards.add(new Card(cardQuestions[0], cardQuestions[1], cardQuestions[2], cardQuestions[3], cardQuestions[4]));
            }
            i--;
        }
        teams = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        Collections.shuffle(cards);
        return cards;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> newTeams) {
        teams = newTeams;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
