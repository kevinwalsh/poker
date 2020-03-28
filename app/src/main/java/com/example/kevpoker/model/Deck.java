package com.example.kevpoker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    List<Card> cards;
    String[] suits={"♣","♦","❤","♠"};      //found handy ascii symbols
    String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

    public Deck (){             //PROBLEM was that this was private!

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
}
