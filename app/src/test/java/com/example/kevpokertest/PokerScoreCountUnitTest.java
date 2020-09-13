package com.example.kevpokertest;

import com.example.kevpoker.logic.PokerScoreCountLogic;
import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.PokerHandScore;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class PokerScoreCountUnitTest {
    private void AssertHand(PokerHandScore phs, int expectedScore, int expectedHighCard){
        assertNotNull(phs);
        assertEquals(phs.handType,expectedScore);
        assertEquals(phs.primaryRank,expectedHighCard);
    }


    @Test
    public void CheckStraight() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("straight");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 5, 8);
    }

    @Test
    public void CheckStraightFlush() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("straightflush");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 9, 7);
    }

    @Test
    public void CheckRoyalStraight() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("royalstraight");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 10, 12);
    }

    @Test
    public void CheckStraightFlush_Pair() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("straightflush pair");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 9, 6);
    }


    @Test
    public void CheckFlush() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("flush");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 6, 0);
    }


    @Test
    public void CheckFlush2() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("flush2");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 6, 0);
    }


    @Test
    public void CheckFullHouse() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("fullhouse");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 7, 2);
        assertEquals(score.secondaryRank, 9);
    }


    @Test
    public void CheckTwoTriple() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("twotriple");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 7, 10);
        assertEquals(score.secondaryRank, 0);       // ensure "lower" triple is secondary
    }


    @Test
    public void CheckOneTriple() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("onetriple");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 4, 0);
    }


    @Test
    public void CheckTwoPair() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("twopair");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 3, 1);
    }

    @Test
    public void CheckOnePair() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("onepair");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score, 2, 3);
    }

    @Test
    public void CheckHighCard() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> cards = data.getHandScenario("e");
        PokerScoreCountLogic phc = new PokerScoreCountLogic();
        PokerHandScore score = phc.checkPoints(cards.subList(0,5), cards.subList(5,7));
        assertEquals(cards.size(), 7);
        AssertHand(score,1 , 10);
    }

}