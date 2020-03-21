package com.example.kevpoker.model;

import android.graphics.Color;

import com.example.kevpoker.model.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //public Card cardsarray[];
    public List<Card> cards;
    public static int colors[]={Color.RED, Color.BLUE,Color.GREEN,Color.MAGENTA,Color.CYAN,Color.parseColor("#ff8c00")};
    public String playerstatus;
    public int chips;
    public int callpaid;           // so we can just do addtopot(game.call - player.callpaid), prob easiest method
    public String name;
  //  int suitcounter[];            //didnt need! have it declared internally in checkpoints()

    public static String hand[]={"n/a","highcard","pair","twopair","3ofKind","straight","flush","fullhouse","4ofKind","straightflush"};
    public int handpoints=0; // 1=highcard, 2=pair, 3= twopairs, 4=3ofKind, 5=straight, 6=flush, 7=FHouse, 8=4ofKind, 9=strFl,10=RFl
                        //
    public int highcard=-1;       // any extra factors that may affect who wins pot (if 2 players both have pair, straight, etc)
                        // e.g. 8 for pair/triple 8's, 8 as high end of straight, or SUM of flush.
                        // actually, weird workaround, but multiply x10 for first pairs, etc. for FH and 2x pairs, we can workaround to
                        // save 2 numbers together. ie pair 8 = 80, pair 8 and 2 is 82


     public Player (String playername, int playerchips){
         name = playername;
       //  cardsarray= new Card[2];
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
         return 1;
    }

    public void Fold(){
        this.playerstatus = "FOLD";
    }

    public boolean Bust(){
        if(chips>0){
            return false;
        }
        else{
            this.playerstatus = "BUST";
            return true;
        }
     }

    public int Payout(int winnerCall){
         if(winnerCall <= callpaid){        // only take what player has IF lessthan winner
             int temp = callpaid;
             chips -= temp;
             callpaid-= temp;
             return temp;
         }
         else {
             chips-=winnerCall;
             callpaid-= winnerCall;
             return winnerCall;
         }
    }

     // -----------------------------------------

    public void checkpoints(Card temp[]){
        int suitcounter[] ={0,0,0,0};
        int rankcounter[]={0,0,0,0,0,0,0,0,0,0,0,0,0};
        int straightcounter=0;      //temporary straightcounter
        int maxstraight=3;          //max straights found, =3 just to reduce computation (4 marks 5 cards in a row)
        int straighthigh=-1;        //hopefully the "high card" of a straight
        int straightflush=0;
        boolean flush =false;

        for (int z=0;z<temp.length;z++) {
            suitcounter[temp[z].suit]++;            //to quickly count cards, and -> check flushes
            rankcounter[temp[z].rank]++;
        }

        for (int z=0;z<temp.length-1;z++) {
           //took suitcounter and rankcounter outside, because they were only counting to 6 and not 7
            if (temp[z].rank + 1 == temp[z + 1].rank || temp[z].rank == temp[z + 1].rank) {
                if (temp[z].rank + 1 == temp[z + 1].rank) {
                    straightcounter++;  //add 1 to straight, see if we can get to 4
                    if (straightcounter > 3) {        //doublecheck
                        //handpoints=5;     highcard=temp[z+1].rank;
                        maxstraight = straightcounter;
                        straighthigh = temp[z + 1].rank;
                    }      //ensure this is not overwritten by pairs!

                    if (temp[z].suit == temp[z + 1].suit) {
                        straightflush++;
                    } else {
                        straightflush = 0;
                    }
                    // while increasing to mark straight, check for flush
                }

            } else {
                straightcounter = 0;
                straightflush = 0;
            }      // reset straight counter, break in sequence

            // if (straightcounter>maxstraight){maxstraight=straightcounter;straighthigh=temp[z].rank;}

        }       //doublecheck, end of cardchecks?

//check for flushes and pairs, etc
                   //z=10 wont cut it. (EDIT: yes it will)
                        // pair 2 pair 3 (otherwise only 32) MUST beat pair A (26)

//------------- PAIRS, ETC -----------------------------
            boolean threeofakind=false;
            int threeval=-1; int twoval=-1;
            for (int i=0;i<rankcounter.length;i++) {
                switch (rankcounter[i]){
                    case 2: if (threeofakind==false) {twoval=threeval;threeval=i;}
                        else {twoval=i;}    break;
                    case 3: if (threeofakind==false) {twoval=threeval;threeval=i;threeofakind=true;}
                        else threeval=i;break;
                    case 4: //threeval=i;
                        handpoints=8;   highcard=i;
                        threeofakind=false;threeval=-1;twoval=-1; i=rankcounter.length;break;
                }
            }
            if (threeofakind==true) {           //2pt pair, 3pt 2x2, 4pt triple, 7pt FH
                if (twoval !=-1) {  //fullhouse
                    handpoints=7;highcard=(threeval+1)*13+(twoval+1);       //bring up to value >0, in case of 3x2's (=0, not 20!)
                }
                else handpoints=4;highcard=threeval;        //whatever, won't affect it here
            }
            else if (threeval!=-1) {
                    if (twoval!=-1) {handpoints=3;highcard=(threeval+1)*13+(twoval+1);}
                    else {handpoints=2;highcard=threeval;}
                }

//------------- STRAIGHTS AND FLUSHES -----------------------
                    // actually dont need separate royal flush check, just a highcard straight flush
    //    System.out.println(String.format("straightcheck: %d %d %d %d %d %d %d %d %d %d %d %d %d",rankcounter[0],rankcounter[1],rankcounter[2],rankcounter[3],rankcounter[4],rankcounter[5],rankcounter[6],rankcounter[7],rankcounter[8],rankcounter[9],rankcounter[10],rankcounter[11],rankcounter[12]));

        if (maxstraight>3 && straightflush>3) {        //straight flush
                handpoints=12; highcard=straighthigh;
            }
            else flush = flushcheck(suitcounter);
            if (flush==true && handpoints<6){
                    handpoints=6; highcard=0;           //not bothering with highcard here
                }
            else if (maxstraight>3) {
                handpoints=5;highcard=straighthigh;
            }
        else highcard= cards.get(0).rank; if (cards.get(1).rank>cards.get(0).rank) {highcard=cards.get(1).rank;}

        }


    public boolean flushcheck(int suitcounter[]){
        boolean flush=false;
        for (int z=0;z<suitcounter.length;z++){
            if (suitcounter[z]>4) {
                flush =true;
           //     System.out.println("Flush!");
            }
        }
        return flush;
    }


}
