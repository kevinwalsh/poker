package com.example.kevpoker;

import java.util.Random;

public class Deck {
    int deckCounter=0;
    Card cards[];
    //String[] suits={"Clubs","Diam","Hearts","Spades "};      //lowest rank to highest
    String[] suits={"♣","♦","❤","♠"};      //found handy ascii symbols
    String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

    public Deck (){             //PROBLEM was that this was private!

        cards= new Card[52];
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                cards[i*13+j]=new Card(j,i,ranks[j]+" "+suits[i]);
            }
        }
    }
    public Card dealnext(){
        //should add check to ensure this counter doesnt go over 52?
        deckCounter++;
        System.out.println(String.format("dealing %s",cards[deckCounter-1].value));
        return cards[deckCounter-1];
    }

    //TODO improve swapcards functionality; I suspect this is optimized for 4 players, & otherwise they dont appear as desired.
    //  ALSO, since playernum determined by activePlayers, game would prob break if anyone was mid fold. might need endOldGame?
    public void swapcards(int chosencards[],int playernum){
        int swaps[]=chosencards;
        for (int i=0; i<swaps.length;i++){
            for (int j=0;j<cards.length;j++){
                if (cards[j].suit*13+cards[j].rank==swaps[i]){
                    Card temp= cards[j];
                    cards[j]=cards[i+2*(playernum-1)];          //poss messed up before cos this and line below ref'd diff things
                    cards[i+2*(playernum-1)]=temp;
                    System.out.println(String.format("chosencard=%d, cardval=%s, dudcard=%s",swaps[i],cards[i+2*(playernum-1)].value,cards[j].value));
                    j=cards.length; //IF found, jump to end of seq
                }
            }
        }
    }

    public void shuffle(){
        Random random = new Random();
        int index;
        Card temp;
        for (int i=cards.length-1; i>0;i--){         //count backwards
            index=random.nextInt(i+1);          //"Fisher-Yates" sorting apparently
            if (index!=i){
                temp = cards[i];
                cards[i] = cards[index];
                cards[index] = temp;
            }
        }
    }
}
