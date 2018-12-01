package com.example.temp.a30seconds.utils;

public class GameUtils {
    public static String customUpper(String line) {
        //this method leaves letters like c in McDONALDS small when changing to uppercase

        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (!String.valueOf(line.charAt(i)).equals("<")) {
                newLine.append(String.valueOf(line.charAt(i)).toUpperCase());
            } else {
                newLine.append(String.valueOf(line.charAt(i + 1)).toLowerCase());
                i++;
            }
        }
        return String.valueOf(newLine);
    }
}
