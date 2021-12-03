package com.example.JE;

public class Utils {

    public static String doublingApostrophe(String single) {
        if(single == null)
            return null;

        StringBuilder doubl = new StringBuilder();
        for (int i = 0; i < single.length(); i++) {
            if(single.charAt(i) == '\'') {
                doubl.append("'");
            }
                doubl.append(single.charAt(i));
        }
        return doubl.toString();
    }
}
