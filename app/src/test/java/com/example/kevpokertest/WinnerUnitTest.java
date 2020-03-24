package com.example.kevpokertest;

import com.example.kevpoker.logic.PokerScoreCountLogic;
import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.Game;
import com.example.kevpoker.model.Player;
import com.example.kevpoker.model.PokerHandScore;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class WinnerUnitTest {
    @Test
    public void SimpleWinner() throws Exception {
        Game game = new Game(null,2,
                new String[]{"player1","player2"},new int[]{100,100});
        game.players.get(0).handScore = new PokerHandScore(2,5,-1);
        game.players.get(1).handScore = new PokerHandScore(7,5,-1);
        game.players.get(0).callpaid=10;
        game.players.get(1).callpaid=10;
        game.PayoutWinningsAll(game.players);

        assertEquals(game.players.get(0).name, "player2");
                                    // "sorting by winner" has sideeffect here of reordering list
        assertEquals(game.players.get(1).name, "player1");
        assertEquals(game.players.get(0).chips, 110);
        assertEquals(game.players.get(1).chips, 90);
    }

    @Test
    public void SimpleWinner_4players() throws Exception {
        Game game = new Game(null,4,
                new String[]{"player1","player2","p3","p4"},new int[]{100,100,100,100});
        game.players.get(0).handScore = new PokerHandScore(5,5,-1);
        game.players.get(1).handScore = new PokerHandScore(7,5,-1);
        game.players.get(2).handScore = new PokerHandScore(4,5,-1);
        game.players.get(3).handScore = new PokerHandScore(3,5,-1);
        game.players.get(0).callpaid=10;
        game.players.get(1).callpaid=10;
        game.players.get(2).callpaid=10;
        game.players.get(3).callpaid=10;
        game.PayoutWinningsAll(game.players);

        assertEquals(game.players.get(0).name, "player2");
        assertEquals(game.players.get(1).name, "player1");
        assertEquals(game.players.get(0).chips, 130);
        assertEquals(game.players.get(1).chips, 90);
        assertEquals(game.players.get(2).chips, 90);
        assertEquals(game.players.get(3).chips, 90);
    }

    @Test
    public void SidepotWinner() throws Exception {
        Game game = new Game(null,4,
                new String[]{"player1","player2","p3","p4"},new int[]{100,25,100,100});
        game.players.get(0).handScore = new PokerHandScore(5,5,-1);
        game.players.get(1).handScore = new PokerHandScore(7,5,-1);
        game.players.get(2).handScore = new PokerHandScore(4,5,-1);
        game.players.get(3).handScore = new PokerHandScore(3,5,-1);
        game.players.get(0).callpaid=50;
        game.players.get(1).callpaid=25;
        game.players.get(2).callpaid=50;
        game.players.get(3).callpaid=50;
        game.PayoutWinningsAll(game.players);

        assertEquals(game.players.get(0).name, "player2");
        assertEquals(game.players.get(1).name, "player1");
        assertEquals(game.players.get(0).chips, 100);       // only paid in 25, payout = 100
        assertEquals(game.players.get(1).chips, 125);       // lost main x25, got 2x 25 from others
        assertEquals(game.players.get(2).chips, 50);
        assertEquals(game.players.get(3).chips, 50);
    }

    @Test
    public void FoldedPlayer() throws Exception {Game game = new Game(null,4,
            new String[]{"player1","player2","p3","p4"},new int[]{100,100,100,100});
        game.players.get(0).handScore = new PokerHandScore(5,5,-1);
        game.players.get(1).handScore = new PokerHandScore(7,5,-1);
        game.players.get(2).handScore = new PokerHandScore(4,5,-1);
        game.players.get(3).handScore = new PokerHandScore(3,5,-1);
        game.players.get(0).callpaid=50;
        game.players.get(1).callpaid=50;
        game.players.get(2).callpaid=10;
        game.players.get(2).playerstatus="FOLD";
        game.players.get(3).callpaid=50;
        game.PayoutWinningsAll(game.players);

        assertEquals(game.players.get(0).name, "player2");
        assertEquals(game.players.get(1).name, "player1");
        assertEquals(game.players.get(0).chips, 210);
        assertEquals(game.players.get(1).chips, 50);
        assertEquals(game.players.get(2).chips, 90);        // p3 folded after 10chips
        assertEquals(game.players.get(3).chips, 50);
    }


}