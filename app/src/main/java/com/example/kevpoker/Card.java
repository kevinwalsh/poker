package com.example.kevpoker;

public class Card {
    String value;
    int rank;
    int suit;
    protected void onCreate (){
    }

    public Card (int extrank, int extsuit, String myval){
        super();        //not sure, should revise this. got from:
                    //http://stackoverflow.com/questions/14480769/android-new-object-class
        //value="default_from_constructor";
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
