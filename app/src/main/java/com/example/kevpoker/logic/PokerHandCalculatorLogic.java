package com.example.kevpoker.logic;

import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Player;
import com.example.kevpoker.model.PokerHandScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerHandCalculatorLogic {

    // ------------------------------- Player Hand Comparator
    public static Comparator<Player> getPointsComparator(){
        return HandPointComparator;
    }

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

    //-------------------------------------------- Main

    public PokerHandScore CheckForFlush(List<Card> cards) {
        List<Card> temp = cards;
        Collections.sort(temp, Card.getCardComparator_Suit());
        int count = 1;
        for(int i =1; i <= temp.size()-1;i++){
            if (temp.get(i).suit == temp.get(i-1).suit) {
                count++;
            }
            else{
                count = 1;
            }
            if(count==5){
                return new PokerHandScore(6,temp.get(i).suit,0);
                                                // flush "primary" = suit
            }
        }
    return null;
    }

    public PokerHandScore CheckPairsEtc(List<Card> cards) {
        int[] rankcounter = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(Card c : cards) {
            rankcounter[c.rank]++;
        }
        int[] pairsAt = {-1,-1};
        int[] triplesAt = {-1,-1};
        for(int i=rankcounter.length-1;i>=0;i--) {
            switch (rankcounter[i]) {       // (n) cards of rankindex [i]
                case 0:
                case 1:
                    break;      // ignore..... cant even set highcard from here. dont know playercards
                case 2:
                    if (pairsAt[0] == -1) {
                        pairsAt[0] = i;
                    } else if (pairsAt[1] == -1) {
                        pairsAt[1] = i;
                    }
                    //  else ignore, take 2x highest pairs. Could have 3x in a set of 7 but we've taken highest
                    break;
                case 3:
                    if (triplesAt[0] == -1) {
                        triplesAt[0] = i;
                    } else {
                        triplesAt[1] = i;
                    }            // as earlier, can only have 2x triples
                    break;
                case 4:
                    //  4 of a kind: return immediately
                    return new PokerHandScore(8, i, 0);
            }
        }
            // 3 of a kind better than twopair
            if(triplesAt[0]!=-1){
                int primary = triplesAt[0];
                int secondary = triplesAt[1]> pairsAt[0]? triplesAt[1] : pairsAt[0];
                                // IF 2nd triple AND/OR pair -> Fullhouse; take whichever higher rank
                if (secondary >=0){
                    return new PokerHandScore(7,primary,secondary);
                }
                else {
                    return new PokerHandScore(4,primary,secondary);
                }
            }
            else if (pairsAt[0] != -1){
                            // 1 OR 2 pairs
                if (pairsAt[1] != -1){
                    return new PokerHandScore(3,pairsAt[0], pairsAt[1]);
                }
                return new PokerHandScore(2,pairsAt[0], -1);
            }
        return null;
    }
/*
    public PokerHandScore CheckForStraight_old(List<Card> cards){
        //List<Card> temp = new ArrayList<>(cards);
                        //  not needed, reordering is fine for copies (though edits unconfirmed)
        List<Card> temp = cards;
        sortCardsByRank(temp);

        int highcard = temp.get(temp.size()-1).rank;     // get highest card, count backward
        int count =1;
        for (int i = temp.size()-2; i>=0; i--){        // start at 2nd highest
            if (temp.get(i).rank== temp.get(i+1).rank){
                //continue;
            }
            else if (temp.get(i).rank + 1 == temp.get(i+1).rank){
                count++;
            }
            else {
                count = 1;
                highcard = temp.get(i).rank;
            }
            if(count==5){
                // Straight
                return new PokerHandScore(5,highcard,0);
            }
        }
        return null;        // no straight found

    }
*/

/*
    public PokerHandScore CheckForStraight_orSF_old(List<Card> cards){
        PokerHandScore straight = null;
        List<Card> temp = cards;
        sortCardsByRank(temp);
        //List<Card> duplicates = new ArrayList<Card>();
        Stack<Card> duplicates = new Stack<Card>();

        Card highcard = temp.get(temp.size()-1);     // get highest card, count backward
        int count =1;
        for (int i = temp.size()-2; i>=0; i--){        // start at 2nd highest
            if (temp.get(i).rank== temp.get(i+1).rank){
                //Card t = temp.get(i);
                Card y = temp.remove(i);        // remove "pair", keep incase it helps a straightfluch
                duplicates.push(y);
            }
            else if (temp.get(i).rank + 1 == temp.get(i+1).rank){
                count++;
            }
            else {
                temp.remove(i+1);
                count = 1;
                highcard = temp.get(i);
            }
            if(count==5){
                // Straight
                straight = new PokerHandScore(5,highcard.rank,0);
            }
        }

        return straight;        // no straight found
    }
*/
    //public PokerHandScore CheckForStraight_orSF_att2(List<Card> cards) {      // new algo works better
    public PokerHandScore CheckForStraight(List<Card> cards) {
        //List <Card> temp = cards;     // shallow copy! remove() having bad effects
        List <Card> temp = new ArrayList<Card>(cards);
        Collections.sort(temp, Card.getCardComparator_Rank());
        List<Card> straightcards = new ArrayList<Card>();
        List<Card> duplicates = new ArrayList<Card>();

        straightcards.add(temp.remove(temp.size()-1));
        for (int i = temp.size() - 1; i >= 0; i--) {
            if (temp.get(i).rank == straightcards.get(straightcards.size()-1).rank) {

                duplicates.add(temp.remove(i));
                                // remove duplicate "pair", keep incase it helps a straightflush

            } else if (temp.get(i).rank + 1 == straightcards.get(straightcards.size()-1).rank) {
                straightcards.add(temp.remove(i));
            }
            else if(straightcards.size() < 5) {
                straightcards.clear();
                duplicates.clear();
                straightcards.add(temp.remove(i));
            }
        }
        if(straightcards.size() > 4) {
            PokerHandScore straightflush = CheckStraightFlush(straightcards, duplicates);
            if(straightflush == null) {
                PokerHandScore straight = new PokerHandScore(5, straightcards.get(0).rank, 0);
                return straight;
            }
            else {
                if(straightflush.primaryRank==12){
                    //  Royal Flush! Best hand in game!
                    straightflush.handType=10;
                }
                return straightflush;
            }
        }
        return null;
    }

    public PokerHandScore CheckStraightFlush(List<Card> cards, List<Card> duplicates){
                                // cards already ordered, dont need to sort
        PokerHandScore sf = null;
        int straightflushcounter = 1;
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i).suit == cards.get(i-1).suit){
                straightflushcounter++;
            }
            else {
                boolean duplicate = false;
                for(Card d : duplicates){
                    if(d.rank == cards.get(i).rank && d.suit == cards.get(i-1).suit){
                        duplicate = true;
                        cards.get(i).suit = d.suit;     // cheating but ugh
                    }
                }
                if(duplicate == true){ straightflushcounter++;}
                else{
                    straightflushcounter = 1;
                }
            }
        if(straightflushcounter >=5){
            sf = new PokerHandScore(9,cards.get(0).rank, -1);
        }
        }
        return sf;
    }
/*
    private void sortCardsByRank(List<Card> cards){
        Collections.sort(cards, Card.getCardComparator_Rank());
    }
*/
}
