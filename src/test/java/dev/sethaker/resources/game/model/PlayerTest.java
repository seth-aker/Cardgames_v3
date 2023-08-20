package dev.sethaker.resources.game.model;


import dev.sethaker.services.Input;
import dev.sethaker.services.TesterInput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player testPlayer;
    Input testInput = new TesterInput();
    @Before
    public void setUp(){
        testPlayer = new Player("TEST");
        testPlayer.addNewHand(new PlayerHand(0));
        testPlayer.getPlayerHands().get(0).addCardToHand(new Card(2, 'C'));
        testPlayer.getPlayerHands().get(0).addCardToHand(new Card(3, 'S'));
    }
    @Test
    public void getCardChoice() {
        Card result = testPlayer.getCardChoice( testInput);
        Card expected = new Card(2, 'C');

        assertEquals("Card names should be the same.", expected.toString(), result.toString());

    }
}