package com.example.kevpoker.model;

import com.example.kevpoker.services.ObjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    List<Card> cards;
    String[] suits={"♣","♦","❤","♠"};      //found handy ascii symbols
    String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

    public Deck (){             //PROBLEM was that this was private!
        CreateNewDeck();
    }

    public void CreateNewDeck(){
        cards= new ArrayList<Card>();
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                cards.add(new Card(j,i,ranks[j]+" "+suits[i]));
            }
        }
    }

    public Card dealnext(){
        return cards.remove(0);
    }


    public void shuffle(){
        Random random = new Random();
        int index;
        Card temp;
        for (int i=cards.size()-1; i>0;i--){
            index=random.nextInt(i+1);
            if (index!=i){
                Collections.swap(cards,i,index);
            }
        }
    }

    public void Debug_RearrangeDeck(int[] cardindexes, int targetPlayer, int activePlayers){
        /*
        * TASKS: Reset deck, find specified cards, move to front, pad with random cards for other players
        */

        // step 0: re-order deck. (just create new)
        CreateNewDeck();
        //  1) copy desired cards in predefined order (too messy to remove 1 by 1 while keeping order)
        List<Card> chosenCards = new ArrayList<>();
        for(int i=cardindexes.length-1;i>=0;i--){        // copy cards in desired order
             chosenCards.add(0, cards.get(cardindexes[i]));
        }
        // 2) COPY, THEN sort the indexes list, and remove last to first to maintain correct order
        int[] temp =cardindexes.clone();
        Arrays.sort(temp);
        for(int i = temp.length -1; i>=0; i--){
            cards.remove(temp[i]);
        }
        // 3) shuffle remaining
        shuffle();
        // 4) add dummy cards for other players, skipping 1st
        for(int i=1;i<activePlayers;i++){
            chosenCards.add(2,dealnext());
            chosenCards.add(2,dealnext());
        }
        // 5) swap "chosen playercard" to targetPlayer position
        ObjectService.swap(chosenCards,0,targetPlayer*2);
        ObjectService.swap(chosenCards,1,targetPlayer*2 + 1);

        // 6) add the previously removed cards to start of deck
        cards.addAll(0,chosenCards);
    }
}
