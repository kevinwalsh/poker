package com.example.kevpoker.model;

import android.content.Context;

import com.example.kevpoker.logic.PokerScoreCountLogic;
import com.example.kevpoker.services.ConsolePrintService;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public List<Player> players;
    public Deck mydeck;
    public Table table;
    int gameCounter = 0;
    int blind = 10;
    public int gameDealer = 0;
    Context activityContext;
    ConsolePrintService cps;

    public  Game (Context context, int numplayers, String names[], int chips[]){
                        // need context for toast msgs
        mydeck = new Deck();
        activityContext = context;
        cps= new ConsolePrintService();              //breaking debug unit tests

        players = new ArrayList<Player>();
        for (int i=0;i<numplayers;i++){
            players.add(new Player(names[i], chips[i]));
        }
    }

    public void CreateNewTable(){
        EnsureDeckIsFull(mydeck);
        gameCounter++;
        List<Player> activePlayers = new ArrayList<Player>();
        for (Player p : players){
            if(p.playerstatus == "ACTIVE"){activePlayers.add(p);}
        }
        gameDealer++;
        gameDealer= gameDealer%activePlayers.size();    // need to account for looping

        table = new Table(mydeck, activePlayers,blind,gameDealer, this);
   }

    public void StartFirstRound(){
        ShuffleCards();
        table.nextRound();      //  Deal cards to players
    }

    public void ShuffleCards(){
        table.deck.shuffle();
    }

    public void EnsureDeckIsFull(Deck deck){
        if(deck.cards.size() != 52){
            throw new RuntimeException("KW_DeckMissingCardsException");
        }
    }

    public void EndOldGame(){
        System.out.println("ending old game");
        cps.toastmessage("End of round " + gameCounter, activityContext);
        cps.AlertDialogBox("Game Over", "end of old game",
                "ok","cool",activityContext);

        ResetActivePlayers();
        CollectCardsFromTable();
    }

    public void CollectCardsFromTable(){
        mydeck.cards.addAll(table.tablecards);      //table deleted anyway, dont need to clear list
        table.tablecards.clear();
        for(Player p : table.players){
            mydeck.cards.addAll(p.cards);
            p.cards.clear();
        }
        System.out.println("cleaned up table");
    }

    public void ResetActivePlayers() {
                        //TODO doublecheck
        for(Player p: players){
            if(p.playerstatus != "BUST"){
                p.playerstatus = "ACTIVE";
            }
        }
    }

    public boolean CalcWinningPoints(List<Card> tablecards, List<Player> activePlayers){
        PokerScoreCountLogic pokerScoreCountLogic = new PokerScoreCountLogic();
        for(Player p: activePlayers) {
            p.handScore= pokerScoreCountLogic.checkPoints(tablecards, p.cards);
        }
        return true;
    }
}
