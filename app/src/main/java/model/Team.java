package model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by temp on 21/09/2017.
 */

public class Team {
    private ArrayList<String> playerNames;
    private String name;
    private String color;
    private int totalPoints;
    private Round round;

    public Team(ArrayList<String> playerNames, String name,int totalPoints) {
        this.playerNames = playerNames;
        this.name = name;
        this.totalPoints = totalPoints;
        round = new Round(0,-1);
    }

    public Round getRound() {
        return round;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
