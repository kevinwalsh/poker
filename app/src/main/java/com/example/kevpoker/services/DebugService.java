package com.example.kevpoker.services;

import com.example.kevpoker.model.Game;

public class DebugService {

    public void DebugReplaceCards(int[] chosenCards, Game mygame){
        int targetPlayer = 0;
        mygame.CollectCardsFromTable();
        mygame.mydeck.CreateNewDeck();
        mygame.mydeck.Debug_RearrangeDeck(chosenCards,targetPlayer,mygame.table.GetRemainingPlayerCount());
        mygame.table.dealCardsToPlayers();

        int tblCards = 0;
        switch ( mygame.table.roundcounter){
            case 2: tblCards = 3;break;
            case 3: tblCards = 4;break;
            case 4:
            case 5:
                tblCards = 5; break;
        }
        for (int i=0;i<tblCards; i++){
            mygame.table.tablecards.add(mygame.mydeck.dealnext());
        }



    }

}
