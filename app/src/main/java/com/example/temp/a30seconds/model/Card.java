package com.example.temp.a30seconds.model;

/**
 * Created by temp on 18/09/2017.
 */

public class Card {
    private String crypt1,crypt2,crypt3,crypt4,crypt5;

    public Card(){}
    public Card(String crypt1, String crypt2, String crypt3, String crypt4, String crypt5) {
        this.crypt1 = crypt1;
        this.crypt2 = crypt2;
        this.crypt3 = crypt3;
        this.crypt4 = crypt4;
        this.crypt5 = crypt5;
    }

    public String getCrypt1() {
        return crypt1;
    }

    public void setCrypt1(String crypt1) {
        this.crypt1 = crypt1;
    }

    public String getCrypt2() {
        return crypt2;
    }

    public void setCrypt2(String crypt2) {
        this.crypt2 = crypt2;
    }

    public String getCrypt3() {
        return crypt3;
    }

    public void setCrypt3(String crypt3) {
        this.crypt3 = crypt3;
    }

    public String getCrypt4() {
        return crypt4;
    }

    public void setCrypt4(String crypt4) {
        this.crypt4 = crypt4;
    }

    public String getCrypt5() {
        return crypt5;
    }

    public void setCrypt5(String crypt5) {
        this.crypt5 = crypt5;
    }
}
