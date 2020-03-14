package com.example.kevpoker.logic;

public class PokerGameLogic {

    PokerBetLogic pokerBetLogic;
    PokerScoreCountLogic pokerScoreCountLogic;


    public PokerGameLogic(){
        System.out.println("PokerGameLogic initialized");
        this.pokerBetLogic = new PokerBetLogic();
        this.pokerScoreCountLogic = new PokerScoreCountLogic();

    }

    public void signOfLife(){
        System.out.println("----------pokerGamelogic accessible");
    }

}
