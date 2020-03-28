package com.example.kevpokertest;

import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DebugUnitTest {

    int debug_straight[] = new int[]{4, 33, 18, 50, 51, 19, 34};
    int debug_flush []= new int[]{2, 12, 18, 50, 7, 5, 11};
    int debug_flush2[]= new int[]{2, 14, 18, 6, 7, 5, 11};
    int debug_fullhouse[]= new int[]{2, 9, 18, 50, 28, 22, 15};
    int debug_fourkind []= new int[]{0, 13, 18, 50, 51, 39, 26};

    public void AssertDealtCards(int players, int[] debugCards, List<Card> dealtCards){
        assertEquals(debugCards.length, 7);
        assertEquals(dealtCards.size(), players*2 + 5);

        assertEquals((dealtCards.get(0).suit*13 + dealtCards.get(0).rank), debugCards[0]);
        assertEquals((dealtCards.get(1).suit*13 + dealtCards.get(1).rank), debugCards[1]);
        assertEquals((dealtCards.get(players*2+0).suit*13 + dealtCards.get(players*2+0).rank), debugCards[2]);
        assertEquals((dealtCards.get(players*2+1).suit*13 + dealtCards.get(players*2+1).rank), debugCards[3]);
        assertEquals((dealtCards.get(players*2+2).suit*13 + dealtCards.get(players*2+2).rank), debugCards[4]);
        assertEquals((dealtCards.get(players*2+3).suit*13 + dealtCards.get(players*2+3).rank), debugCards[5]);
        assertEquals((dealtCards.get(players*2+4).suit*13 + dealtCards.get(players*2+4).rank), debugCards[6]);

    }

    @Test
    public void Debug_Game_RearrangeDeck_Simple_1player_straight() throws Exception {
        Game game = new Game(null,1,
                new String[]{"player1"},new int[]{100});
        game.CreateNewTable();

        List<Card> dealtCards = new ArrayList<>();
        game.RearrangeDeck(debug_straight);

        for (int i=0;i<game.players.size()*2+5;i++){
            dealtCards.add(game.mydeck.dealnext());
        }

        AssertDealtCards(game.players.size(), debug_straight, dealtCards);
    }


    @Test
    public void Debug_Game_RearrangeDeck_2player_straight() throws Exception {
        Game game = new Game(null,2,
                new String[]{"player1","player2"},new int[]{100,100});
        game.CreateNewTable();

        List<Card> dealtCards = new ArrayList<>();
        game.RearrangeDeck(debug_straight);

        for (int i=0;i<game.players.size()*2+5;i++){
            dealtCards.add(game.mydeck.dealnext());
        }

        AssertDealtCards(game.players.size(), debug_straight, dealtCards);
    }

    @Test
    public void Debug_Game_RearrangeDeck_flush() throws Exception {
        Game game = new Game(null,2,
                new String[]{"player1","player2"},new int[]{100,100});
        game.CreateNewTable();

        List<Card> dealtCards = new ArrayList<>();
        game.RearrangeDeck(debug_flush);

        for (int i=0;i<game.players.size()*2+5;i++){
            dealtCards.add(game.mydeck.dealnext());
        }

        AssertDealtCards(game.players.size(), debug_flush, dealtCards);
    }

    @Test
    public void Debug_Game_RearrangeDeck_fullhouse() throws Exception {
        Game game = new Game(null,2,
                new String[]{"player1","player2"},new int[]{100,100});
        game.CreateNewTable();

        List<Card> dealtCards = new ArrayList<>();
        game.RearrangeDeck(debug_fullhouse);

        for (int i=0;i<game.players.size()*2+5;i++){
            dealtCards.add(game.mydeck.dealnext());
        }

        AssertDealtCards(game.players.size(), debug_fullhouse, dealtCards);
    }


    @Test
    public void Debug_Game_RearrangeDeck_fourKind() throws Exception {
        Game game = new Game(null,2,
                new String[]{"player1","player2"},new int[]{100,100});
        game.CreateNewTable();

        List<Card> dealtCards = new ArrayList<>();
        game.RearrangeDeck(debug_fourkind);

        for (int i=0;i<game.players.size()*2+5;i++){
            dealtCards.add(game.mydeck.dealnext());
        }

        AssertDealtCards(game.players.size(), debug_fourkind, dealtCards);
    }

}