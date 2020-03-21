package com.example.kevpoker.model;

import java.util.Comparator;
import java.util.List;


public class PokerHandScore {
    public int handType;
    public int primaryRank;         // cardrank, EXCEPT flush, will be suit
    public int secondaryRank;    // for 2pairs, fullhouse

    public PokerHandScore(int type, int primary, int secondary){
        handType = type;
        primaryRank = primary;
        secondaryRank = secondary;
    }
}
