package com.example.kevpoker.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public List<Card> cards;
    public String playerstatus;
    public int chips;
    public int callpaid;           // so we can just do addtopot(game.call - player.callpaid), prob easiest method
    public String name;
    public PokerHandScore handScore;


     public Player (String playername, int playerchips){
         name = playername;
         cards = new ArrayList<Card>();
         playerstatus="ACTIVE";
         chips=playerchips;
         callpaid=0;
     }

    public int Call(int callToPay){
         if(chips > callToPay){
             callpaid = callToPay;
         }
         else{
             callpaid = chips;
             this.playerstatus = "ALLIN";

         }
         return 1;      //dummy
    }

    public void Fold(){
        this.playerstatus = "FOLD";
    }

    /*
    public boolean Bust(){
        if(chips>0){
            return false;
        }
        else{
            this.playerstatus = "BUST";
            return true;
        }
     }
*/



}
