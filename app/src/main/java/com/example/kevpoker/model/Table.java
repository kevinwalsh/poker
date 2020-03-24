package com.example.kevpoker.model;

import com.example.kevpoker.logic.PokerBetLogic;
import com.example.kevpoker.services.ConsolePrintService;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public Deck deck;
    public int pot = 0;
    public int minblind = 0;
    public int call = 0;
    public int callcounter = 0;
    public int roundcounter=0;      // make 1= dealcards/blinds/preflop, 2 = flop, etc?

    public int dealer;           //  ??
    public int playerTurn;
    int remainingPlayers;
    public List<Player> players;
    public List<Card> tablecards;
    PokerBetLogic pokerBetLogic;
    Game game;

    public Table(Deck mydeck,List<Player> playerslist, int blind,  int roundDealer, Game parentgame){
        pokerBetLogic = new PokerBetLogic();
        tablecards = new ArrayList<Card>();     // dont populate yet ( until 1st round bets) ?
        deck = mydeck;
        players = playerslist;
        remainingPlayers = players.size();
        minblind = blind;
        call = blind;
        dealer = roundDealer;
        playerTurn = roundDealer;
        game = parentgame;

            // dont "init round" from here, let game do it via nextRound when ready
    }

    public boolean nextRound(){
        boolean endGame = false;
        roundcounter++;
        switch (roundcounter){
            case 1:
                nextplayer();               //player to left of dealer
                initBlinds();
                dealCardsToPlayers();
                break;
            case 2:
                playerTurn = dealer;        // reset playerturns, since raises will put this out of order
                nextplayer();
                tablecards.add(deck.dealnext());
                tablecards.add(deck.dealnext());
                tablecards.add(deck.dealnext());
                break;
            case 3:         // same actions for turn & river
            case 4:
                playerTurn = dealer;
                nextplayer();
                tablecards.add(deck.dealnext());
                break;
            case 5:
                // calculate winner
                game.CalcWinningPoints(tablecards,players);
                ConsolePrintService cps = new ConsolePrintService();
                cps.PrintScores(players,tablecards);

                game.PayoutWinningsAll(players);
                game.EndOldGame();
                endGame= true;        // tell game to calc points and start new table?
                break;
        }
                                    // TODO skip to last round if only 1 active player.
                            //  But beware "ALLIN" where player active but can no longer call
        return endGame;
    }

    public void initBlinds(){
        getCurrentPlayer().Call(minblind/2);        // DONT increase callcounter here (?)
        nextplayer();
        getCurrentPlayer().Call(minblind);
        nextplayer();
    }

    public void dealCardsToPlayers(){
        for(Player p : players){
            p.cards.add(deck.dealnext());
            p.cards.add(deck.dealnext());
        }
    }

    public void Call(Player p){
        int chipsCommitted = pokerBetLogic.Call(p, call);
                                    // table not concerned with logic BUT needs the call amount?
                                            // EDIT: not in new algo
        callcounter++;
        nextplayer();
        if(callcounter>= remainingPlayers){       // to catch folds
            callcounter = 0;
            nextRound();
        }
    }

    public void Raise(Player p, int raiseBy){
        call+=raiseBy;
        callcounter=0;
        Call(p);
    }
    public void Raise(Player p){        // default 10chips raise if none specified
        Raise(p, 10);
    }

    public void Fold(Player p){
        p.Fold();
        remainingPlayers--;
        nextplayer();
    }

    public void nextplayer(){
        playerTurn++;
        if (playerTurn>=players.size()){playerTurn=0;}
        if(players.get(playerTurn).playerstatus!= "ACTIVE"){nextplayer();}  // skip folds/allin
    }

    public Player getCurrentPlayer(){
        return players.get(playerTurn);
    }

}
