package com.example.kevpoker.model;

import com.example.kevpoker.logic.PokerBetLogic;
import com.example.kevpoker.logic.PokerScoreCountLogic;

import java.lang.reflect.Array;
import java.util.List;

public class Table {
    public int pot = 0;
    public int minblind = 0;
    public int call = 0;
   // public List<Integer> sidepots;        // not needed! at final call, just do :
                                    // winner gets (winner.callpaid) from each player.
                                    // early folds will only return whatevers in there;
                                    // meanwhile higher players can sort out the rest

    public int dealer;           //  ??
    public int playerTurn;
    public List<Player> players;
    public int raiseCounter = 0;

    public List<Card> tablecards;
    PokerBetLogic pokerBetLogic;

    public Table(List<Card> cards, int blind, List<Player> players, int roundDealer){
        tablecards = cards;
        minblind = blind;
        call = blind;
        dealer = roundDealer;
        playerTurn = roundDealer;       // ??
        players = players;


        pokerBetLogic = new PokerBetLogic();
    }

    public void AddToPot(int chips){        // actually plan to have a virtual pot
        pot+=chips;
    }

    public void Call(Player p){
        int chipsCommitted = pokerBetLogic.Call(p, call);
                                    // table not concerned with logic BUT needs the call amount?
                                            // EDIT: not in new algo
        nextplayer();
    }

    public void Raise(Player p, int raiseBy){
        call+=raiseBy;
        Call(p);
    }
    public void Raise(Player p){        // default 10chips raise if none specified
        Raise(p, 10);
    }

    public void Fold(Player p){
        p.Fold();
        nextplayer();
    }

    public void nextplayer(){
        playerTurn++;
        if (playerTurn>=players.size()){playerTurn=0;}

    }

    public boolean CalcWinningPoints(){
        PokerScoreCountLogic pokerScoreCountLogic = new PokerScoreCountLogic();
        for(Player p: players) {
            pokerScoreCountLogic.checkPoints(tablecards, p.cards);
        }
        return true;
    }

    public boolean PayoutWinnings(Player winner){
        int winner_callpaid = winner.callpaid;      // need temp value, as winner will also "payout"
        boolean isSidepot_remaining = false;
        for(Player p:players){
            winner.chips+= p.Payout(winner_callpaid);       // winners call OR whatever player paid in

            if(p.callpaid > 0) {         // after winner takes their share; is anything still in pot
                isSidepot_remaining = true;
            }
        }
        return isSidepot_remaining;
    }

    public Player getCurrentPlayer(){
        return players.get(playerTurn);
    }


}
