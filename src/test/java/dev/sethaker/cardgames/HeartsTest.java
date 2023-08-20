package dev.sethaker.cardgames;

import dev.sethaker.exceptions.CardInvalidException;
import dev.sethaker.resources.game.model.Card;
import dev.sethaker.resources.game.model.PlayerHand;
import dev.sethaker.resources.game.model.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeartsTest {
    Hearts testGame = new Hearts("TESTPLAYER");
    Card twoOfClubs = new Card(2, 'C');
    Card threeOfClubs = new Card(3, 'C');
    Card twoOfHearts = new Card(2, 'H');
    Card queenOfSpades = new Card(12, 'S');
    Card twoOfDiamonds = new Card(2, 'D');
    Card fourOfClubs = new Card(4, 'C');

    @Before
    public void setUp(){
        testGame.player.addNewHand(new PlayerHand(0));
        testGame.player.getPlayerHands().get(0).addCardToHand(threeOfClubs);
        testGame.player.getPlayerHands().get(0).addCardToHand(twoOfDiamonds);
        testGame.player.getPlayerHands().get(0).addCardToHand(fourOfClubs);
        testGame.player.getPlayerHands().get(0).addCardToHand(twoOfHearts);
        testGame.setTable(new Table());

    }
    @Test
    public void checkIsValidCardSuit_with_valid_card() {
        testGame.getTable().playCard(twoOfClubs, 3);
        testGame.getTable().getTrick().setFirstCardSuit('C');

        try {
            testGame.checkIsValidCardSuit(threeOfClubs, this.testGame.player.getPlayerHands().get(0));
            assertTrue("Test should pass as three of clubs is a valid card this round" , true);
        } catch (CardInvalidException e) {
                assertTrue("Test should pass as three of clubs is a valid card this round",false);
        }

    }

    @Test
    public void checkIsValidCardSuit_with_invalid_card_due_to_having_valid_suit_in_hand() {
        testGame.getTable().playCard(twoOfClubs, 3);
        testGame.getTable().getTrick().setFirstCardSuit('C');

        try {
            testGame.checkIsValidCardSuit(twoOfDiamonds, this.testGame.player.getPlayerHands().get(0));
            assertTrue("checkIsValidCardSuit should throw exception if two of diamonds is played after 2C" , false);
        } catch (CardInvalidException e) {
            assertTrue("checkIsValidCardSuit should throw exception if two of diamonds is played after 2C",true);
        }
    }
    @Test
    public void checkIsValidCardSuit_should_make_hearts_playable_when_valid_heart_is_played(){
        testGame.getTable().playCard(queenOfSpades, 2);
        try {
            testGame.checkIsValidCardSuit(twoOfHearts, this.testGame.player.getPlayerHands().get(0));
        } catch (CardInvalidException e) {
            assertTrue("Two of hearts should not have thrown an exception, is a valid move.", false);
        }

        assertTrue("Hearts should be set to playable", testGame.getHeartsPlayable());
    }
}