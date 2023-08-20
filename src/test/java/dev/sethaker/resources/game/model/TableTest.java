package dev.sethaker.resources.game.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TableTest {
    Table testTable;
    @Before
    public void setUp() {
       testTable = new Table();

    }

    @Test
    public void calculateTrickValue_no_heart_cards(){
        Card twoOfClubs = new Card(2, 'C');
        twoOfClubs.setCardValue("Hearts");
        Card nineOfClubs = new Card(9, 'C');
        nineOfClubs.setCardValue("Hearts");
        Card jackOfClubs = new Card(11, 'C');
        jackOfClubs.setCardValue("Hearts");
        Card sixOfClubs = new Card(6, 'C');
        sixOfClubs.setCardValue("Hearts");

        testTable.playCard(twoOfClubs, 1);
        testTable.playCard(nineOfClubs, 2);
        testTable.playCard(jackOfClubs, 3);
        testTable.playCard(sixOfClubs, 4);

        int trickValue = testTable.calculateTrickValue();

        Assert.assertEquals("Trick value should be 0",0, trickValue);
    }

    @Test
    public void calculateTrickValue_with_queen_of_spades(){
        Card sevenOfClubs = new Card(7, 'C');
        sevenOfClubs.setCardValue("Hearts");
        Card eightOfSpades = new Card(8, 'S');
        eightOfSpades.setCardValue("Hearts");
        Card queenOfSpades = new Card(12, 'S');
        queenOfSpades.setCardValue("Hearts");
        Card fiveOfClubs = new Card(5, 'C');
        fiveOfClubs.setCardValue("Hearts");
        testTable.playCard(sevenOfClubs, 1);
        testTable.playCard(eightOfSpades, 2);
        testTable.playCard(queenOfSpades, 3);
        testTable.playCard(fiveOfClubs, 4);

        int trickValue = testTable.calculateTrickValue();

        Assert.assertEquals("Trick value should equal 13 because of the queen of spades", 13, trickValue );
    }

    @Test
    public void calculateTrickValue_test_with_heart_cards(){
        Card aceOfHearts = new Card(1, 'H');
        aceOfHearts.setCardValue("Hearts");
        Card fiveOfClubs = new Card(5, 'C');
        fiveOfClubs.setCardValue("Hearts");
        Card tenOfHearts = new Card(10, 'H');
        tenOfHearts.setCardValue("Hearts");
        Card queenOfHearts = new Card(12, 'H');
        queenOfHearts.setCardValue("Hearts");
        testTable.playCard(aceOfHearts, 1);
        testTable.playCard(fiveOfClubs, 2);
        testTable.playCard(tenOfHearts, 3);
        testTable.playCard(queenOfHearts, 4);

        int trickValue = testTable.calculateTrickValue();

        Assert.assertEquals("Three hearts in trick should equal 3 points", 3, trickValue);
    }



}