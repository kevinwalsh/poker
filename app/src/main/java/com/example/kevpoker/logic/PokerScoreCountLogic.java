package com.example.kevpoker.logic;

import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.PokerHandScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokerScoreCountLogic {

    PokerHandCalculatorLogic phcl;
    public PokerScoreCountLogic(){

        phcl = new PokerHandCalculatorLogic();
        System.out.println("PokerScoreCountLogic initialized");
    }


    //public PokerHandScore checkPoints(Card[] tablecards, Card[] playercards){
    public PokerHandScore checkPoints(List<Card> tablecards, List<Card> playercards){
        //List<Card> allcards = convertCardArrayToList(tablecards,playercards);
        List<Card> allcards = new ArrayList<>(tablecards); allcards.addAll(playercards);  //shallow copy

        //sortCardsByRank(allcards);


        PokerHandScore pairsEtc= phcl.CheckPairsEtc(allcards);
        PokerHandScore straightCheck = phcl.CheckForStraight(allcards);

        if(straightCheck != null){
                        // exit if straightflush;  else if straight then check fullhouse/ 4x/ flush
            if(straightCheck.handType == 5){
                if(pairsEtc != null && pairsEtc.handType > 5){
                    return pairsEtc;
                }
                else{
                    PokerHandScore flushCheck = phcl.CheckForFlush(allcards);
                    return flushCheck == null ? straightCheck : flushCheck;
                }
            }
            else {
                return straightCheck;               // if exists && not regularstraight, it is highest
            }
        }
        else if( pairsEtc != null){         // no straights; check for flush if max duplicates is pairs/triples
            if(pairsEtc.handType < 6){
                PokerHandScore flushCheck = phcl.CheckForFlush(allcards);
                return flushCheck == null ? pairsEtc : flushCheck;
            }
            else {
                return pairsEtc;            //  fullhouse or 4x
            }
        }
        else{
                                            // no matches; return highcard
            int highcard = playercards.get(0).rank > playercards.get(1).rank ?
                    playercards.get(0).rank : playercards.get(1).rank;
            return new PokerHandScore(1, highcard, -1);
        }

//        return null;
    }
    /*
    private List<Card> convertCardArrayToList(Card[] tablecards, Card[] playercards){
        List<Card> allcards = new ArrayList<Card>( Arrays.asList(tablecards));
        allcards.add(playercards[0]);
        allcards.add(playercards[1]);
        return allcards;
    }
*/
    /*
    private void sortCardsByRank(List<Card> cards){
        Collections.sort(cards, Card.getCardComparator_Rank());
    }
*/
}
