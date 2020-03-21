package com.example.kevpokertest;

import com.example.kevpoker.logic.PokerHandCalculatorLogic;
import com.example.kevpoker.model.Card;
import com.example.kevpoker.model.PokerHandScore;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class PokerHandCheckerUnitTest {
    @Test
    public void CheckStraight() throws Exception {
        //String[] suits={"♣","♦","❤","♠"};      //found handy ascii symbols
        //String[] ranks={"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> straightCards = data.getHandScenario("straight");
        List<Card> nonstraightCards = data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore straightscore = phc.CheckForStraight(straightCards);
        PokerHandScore nonstraightscore = phc.CheckForStraight(nonstraightCards);
        assertEquals(straightCards.size(), 7);
        assertEquals(nonstraightCards.size(), 7);
        assertNotNull(straightscore);
        assertNull(nonstraightscore);
    }
    @Test
    public void CheckCardsListNorReorderedByStraightCheck() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> straightCards= data.getHandScenario("straight");
        assertEquals(straightCards.get(0).rank, 4);

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore straightscore = phc.CheckForStraight(straightCards);
        assertEquals(straightCards.get(0).rank, 4);
    }
    @Test
    public void CheckFlush() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> flushcards= data.getHandScenario("flush");
        List<Card> flushcards2= data.getHandScenario("flush2");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore flushScore= phc.CheckForFlush(flushcards);
        PokerHandScore flushScore2= phc.CheckForFlush(flushcards2);
        PokerHandScore defaultScore= phc.CheckForFlush(defaultcards);

        assertNotNull(flushScore);
        assertNotNull(flushScore2);
        assertNull(defaultScore);
    }

    @Test
    public void CheckFullHouse() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> fullhousecards= data.getHandScenario("fullhouse");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore fullhousescore= phc.CheckPairsEtc(fullhousecards);
        PokerHandScore defaultScore= phc.CheckPairsEtc(defaultcards);
        assertNotNull(fullhousescore);
        assertNull(defaultScore);
    }

    @Test
    public void CheckTwoTriples_FullHouse() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> twotriplecards= data.getHandScenario("twotriple");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore twotriplescore= phc.CheckPairsEtc(twotriplecards);
        PokerHandScore defaultScore= phc.CheckPairsEtc(defaultcards);
        assertNotNull(twotriplescore);
        assertEquals(twotriplescore.handType, 7);       // twotriples is technically fullhouse
        assertEquals(twotriplescore.primaryRank, 10);
        assertEquals(twotriplescore.secondaryRank, 0);

        assertNull(defaultScore);
    }

    @Test
    public void CheckOneTriple() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> onetriplecards= data.getHandScenario("onetriple");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore onetriplescore= phc.CheckPairsEtc(onetriplecards);
        PokerHandScore defaultScore= phc.CheckPairsEtc(defaultcards);
        assertNotNull(onetriplescore);
        assertEquals(onetriplescore.handType, 4);
        assertEquals(onetriplescore.primaryRank, 0);
        assertEquals(onetriplescore.secondaryRank, -1);
        assertNull(defaultScore);
    }

    @Test
    public void CheckTwoPair() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> twopaircards= data.getHandScenario("twopair");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore twopairscore= phc.CheckPairsEtc(twopaircards);
        PokerHandScore defaultScore= phc.CheckPairsEtc(defaultcards);
        assertNotNull(twopairscore);
        assertEquals(twopairscore.handType, 3);
        assertEquals(twopairscore.primaryRank, 1);
        assertEquals(twopairscore.secondaryRank, 0);
        assertNull(defaultScore);
    }

    @Test
    public void CheckOnePair() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> onepaircards= data.getHandScenario("onepair");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore onepairscore= phc.CheckPairsEtc(onepaircards);
        PokerHandScore defaultScore= phc.CheckPairsEtc(defaultcards);
        assertNotNull(onepairscore);
        assertEquals(onepairscore.handType, 2);
        assertEquals(onepairscore.primaryRank, 3);
        assertEquals(onepairscore.secondaryRank, -1);
        assertNull(defaultScore);
    }





    //  ------------------------------------------
    //  ------      COMPLEX TESTS       ----------
    //  ------------------------------------------
    @Test
    public void CheckStraight_Royal_Flush() throws Exception {
        UnitTestDataHelper data = new UnitTestDataHelper();
        List<Card> straightflushcards= data.getHandScenario("straightflush");
        List<Card> royalstraightcards= data.getHandScenario("royalstraight");
        List<Card> defaultcards= data.getHandScenario("e");

        PokerHandCalculatorLogic phc = new PokerHandCalculatorLogic();
        PokerHandScore straightflushscore = phc.CheckForStraight(straightflushcards);
        PokerHandScore royalstraightscore= phc.CheckForStraight(royalstraightcards);
        PokerHandScore defaultScore= phc.CheckForStraight(defaultcards);
        assertNotNull(straightflushscore);
        assertEquals(straightflushscore.handType, 9);
        assertEquals(straightflushscore.primaryRank, 7);
        assertEquals(straightflushscore.secondaryRank, -1);
        assertNotNull(royalstraightscore);
        assertEquals(royalstraightscore.handType, 10);
        assertEquals(royalstraightscore.primaryRank, 12);
        assertEquals(royalstraightscore.secondaryRank, -1);
        assertNull(defaultScore);
    }




}