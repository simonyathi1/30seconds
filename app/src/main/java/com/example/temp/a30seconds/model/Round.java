package com.example.temp.a30seconds.model;

/**
 * Created by temp on 21/09/2017.
 */

public class Round {
    private int points;
    private int roundNumber;
    public Round(int points, int roundNumber) {
        this.points = points;
        this.roundNumber = roundNumber;

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }



}
