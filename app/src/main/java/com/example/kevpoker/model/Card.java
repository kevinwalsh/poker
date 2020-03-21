package com.example.kevpoker.model;

import java.util.Comparator;

public class Card {
    public String value;
    public int rank;
    public int suit;
    //protected void onCreate (){
    //}

    static Comparator rankComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.rank < o2.rank ? -1 : o1.rank == o2.rank ? 0 : 1;
        }
    };
    static Comparator suitComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.suit < o2.suit ? -1 : o1.suit == o2.suit ? 0 : 1;
        }
    };

    public Card (int extrank, int extsuit, String myval){
        super();
        value=myval;
        rank=extrank;
        suit=extsuit;


    }
    public void setcard(String myinput){
        this.value=myinput;
    }
    public String getcard(){
        return this.value;
    }

    public static Comparator<Card> getCardComparator_Rank(){
        return rankComparator;
    }
    public static Comparator<Card> getCardComparator_Suit(){
        return suitComparator;
    }
}
