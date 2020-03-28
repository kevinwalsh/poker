package com.example.kevpoker.model;

import android.content.Context;

import com.example.kevpoker.logic.PokerScoreCountLogic;
import com.example.kevpoker.services.ConsolePrintService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void PayoutWinningsAll(List<Player> activePlayers){
        Collections.sort(activePlayers, Player.getPointsComparator());
        boolean sidePotRemaining = true;
        for(Player p : activePlayers){
            if(sidePotRemaining) {
                sidePotRemaining = PayoutWinningsSingle(p, activePlayers);
            }
        }
    }

    public boolean PayoutWinningsSingle(Player winner, List<Player> activePlayers){
        int chipsbefore = winner.chips;
        int winner_callpaid = winner.callpaid;   // need temp value, as for this algo, winner will also "payout"
        boolean isSidepot_remaining = false;
        for(Player p:activePlayers){
            int win = p.Payout(winner_callpaid);       // winners call OR whatever player paid in
            winner.chips+=win;      // cant do in 1 line as it keeps "old" chips val for winner

            if(p.callpaid > 0) {         // after winner takes their share; is anything still in pot
                isSidepot_remaining = true;
            }
        }
        String msg="player "+ winner.name + " won " + (winner.chips-chipsbefore) +" chips";
        System.out.println(msg);
       // cps.toastmessage(msg,activityContext);                // breaks unittests, not mocked
        return isSidepot_remaining;
    }

    public void RearrangeDeck(int[] cardindexes){
            // for simplicity, debug only gives 7 cards (tablecards + player1)
                    // Therefore will need to skip 2x (N) other players
        mydeck = new Deck();
        table.deck = mydeck;        // re-link deck
        List<Card> chosenCards = new ArrayList<>();
        int [] orderedindexes = cardindexes.clone();
        Arrays.sort(orderedindexes);        // sort & remove highest->lowest, to preserve ordering

        for(int i=cardindexes.length-1;i>=0;i--){        // copy cards in desired order
            chosenCards.add(0,mydeck.cards.get(cardindexes[i]));
        }
        for(int i=orderedindexes.length-1;i>=0;i--){     // remove original cards in descending order
            mydeck.cards.remove(orderedindexes[i]);
        }
        mydeck.shuffle();
        mydeck.cards.addAll(0,chosenCards.subList(0,2)); //  re-add players at start of deck
        mydeck.cards.addAll(table.players.size()*2, chosenCards.subList(2,7));  // add tablecards later
    }
    public void ReplaceCards(){
            for(Player p: table.players){
                p.cards.clear();
                p.cards.add(mydeck.dealnext());
                p.cards.add(mydeck.dealnext());
            }
            int t = table.tablecards.size();
            table.tablecards.clear();
            for(int i=0;i<t;i++){
                table.tablecards.add(mydeck.dealnext());
            }
    }

}
