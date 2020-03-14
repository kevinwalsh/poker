package com.example.kevpoker.model;

public class Card {
    public String value;
    public int rank;
    public int suit;
    //protected void onCreate (){
    //}

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

}
