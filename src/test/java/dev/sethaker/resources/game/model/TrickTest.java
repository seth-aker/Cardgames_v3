package dev.sethaker.resources.game.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrickTest {

    @Test
    public void getWinnerOfTrickPlayerIndex_test_all_same_suit() {
        Card twoOfClubs = new Card(2, 'C');
        Card nineOfClubs = new Card(9, 'C');
        Card jackOfClubs = new Card(11, 'C');
        Card sixOfClubs = new Card(6, 'C');
        Trick trick = new Trick();
        trick.addCard(twoOfClubs, 1);
        trick.addCard(nineOfClubs, 2);
        trick.addCard(jackOfClubs, 3);
        trick.addCard(sixOfClubs, 4);

        trick.setFirstCardSuit('C');
        int playerIndex = trick.getWinnerOfTrickPlayerIndex();

        assertEquals("Player 3 (index 2) played the highest card in the trick", 2, playerIndex);

    }

    @Test
    public void getWinnerOfTrickPlayerIndex_test_with_QS(){
        Card sevenOfClubs = new Card(7, 'C');
        Card eightOfSpades = new Card(8, 'S');
        Card QueenOfSpades = new Card(12, 'S');
        Card fiveOfClubs = new Card(5, 'C');
        Trick trick = new Trick();
        trick.addCard(sevenOfClubs, 1);
        trick.addCard(eightOfSpades, 2);
        trick.addCard(QueenOfSpades, 3);
        trick.addCard(fiveOfClubs, 4);

        trick.setFirstCardSuit('C');
        int playerIndex = trick.getWinnerOfTrickPlayerIndex();
        assertEquals("Player 1 (index 0) should with the trick because they have the highest in-suit card", 0, playerIndex);
    }

    @Test
    public void getWinnerOfTrickPlayerIndex_test_with_hearts(){
        Card aceOfHearts = new Card(1, 'H');
        aceOfHearts.setCardPip(14);
        Card fiveOfClubs = new Card(5, 'C');
        Card tenOfHearts = new Card(10, 'H');
        Card queenOfHearts = new Card(12, 'H');
        Trick trick = new Trick();
        trick.addCard(aceOfHearts, 1);
        trick.addCard(fiveOfClubs, 2);
        trick.addCard(tenOfHearts, 3);
        trick.addCard(queenOfHearts, 4);
        trick.setFirstCardSuit('H');
        int playerIndex = trick.getWinnerOfTrickPlayerIndex();

        assertEquals("Player one should win with the Ace of Hearts", 0,playerIndex );

    }


}