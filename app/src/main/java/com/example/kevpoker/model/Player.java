package com.example.kevpoker.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
    public List<Card> cards;
    public String playerstatus;
    public int chips;
    public int callpaid;           // so we can just do addtopot(game.call - player.callpaid), prob easiest method
    public String name;
    public PokerHandScore handScore;

    static Comparator HandPointComparator = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
                        // Chaining multiple parameters: handpoints, if(equal) {check primary highcard etc}
           boolean samehandtype = o1.handScore.handType == o2.handScore.handType ? true : false;
            if(samehandtype){
                boolean sameprimary = o1.handScore.primaryRank == o2.handScore.primaryRank ? true: false;
                if (sameprimary) {
                    return o1.handScore.handType<o2.handScore.secondaryRank ?
                    1 :o1.handScore.secondaryRank== o2.handScore.secondaryRank ?
                            0 : -1;
                }
                else {
                    return o1.handScore.handType < o2.handScore.handType ?
                            1 : o1.handScore.handType == o2.handScore.handType ?
                            0 : -1;
                }
            }
            return o1.handScore.handType < o2.handScore.handType ?
                    1 : o1.handScore.handType == o2.handScore.handType ?
                    0 : -1;
        }
    };

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

    public int Payout(int winnerCall){
         int payout=0;
         if(winnerCall >= callpaid){        // only take what player has IF lessthan winner
             int temp = callpaid;
             chips -= temp;
             callpaid-= temp;
             payout = temp;
         }
         else {
             chips-=winnerCall;
             callpaid-= winnerCall;
             payout = winnerCall;
         }
         if(chips<=0){
             this.playerstatus = "BUST";
             System.out.println("Player "+ this.name + " gone BUST");
                    // TODO change to onscreen alert later
         }
         return payout;
    }

    public static Comparator<Player> getPointsComparator(){
        return HandPointComparator;
    }

}
