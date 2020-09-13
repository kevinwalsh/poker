package com.example.kevpokertest;

import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UnitTestDataHelper {

    public List<Card> getHandScenario(String handtype) {
        String[] suits={"♣","♦","❤","♠"};      //found handy ascii symbols
        String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

        int deckindexes[] = new int[7];
        Deck deck = new Deck();
        switch (handtype){

            case"straight":
                deckindexes = new int[] {4,33,18,50,51,19,34};
                break;
            case"flush":
                deckindexes = new int[] {2,12,18,50,7,5,11};
                break;
            case"flush2":
                deckindexes = new int[] {2,14,18,6,7,5,11};
                break;
            case"fullhouse":
                deckindexes = new int[] {2,9,18,50,28,22,15};
                break;
           case"twotriple":
                deckindexes = new int[] {0,1,10,13,26,23,36};
                break;
            case"onetriple":
                deckindexes = new int[] {0,1,10,13,26,29,9};
                break;
            case"twopair":
                deckindexes = new int[] {0,1,10,13,14,28,30};
                break;
            case"onepair":
                deckindexes = new int[]  {3,9,10,13,16,28,30};
                break;

                //  MORE COMPLEX SCENARIOS
            case"straightflush":
                deckindexes = new int[]  {2,3,4,6,10,5,20};
                break;
            case"royalstraight":
                deckindexes = new int[]  {51,50,48,49,33,1,47};
                break;
            case"straightflush pair":
                deckindexes = new int[]  {2,3,4,6,10,5,18};
                break;

            default:
                deckindexes = new int[] {7,14,21,28,35,42,49};      // shrug, havent checked
        }

        List<Card> cards = new ArrayList<Card>();
       for(int i = 0; i < deckindexes.length; i++){
           String temp = ranks[deckindexes[i]%13] + suits[deckindexes[i]/13];
           cards.add(new Card (deckindexes[i]%13,
                   deckindexes[i]/13,
           //        "test"
                   temp
           ));
       }
       return cards;

    }
}