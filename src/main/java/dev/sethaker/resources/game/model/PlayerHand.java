package dev.sethaker.resources.game.model;

import dev.sethaker.exceptions.CardInvalidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private BigDecimal wager;
    private final String name;

    private List<Card> cardsInHand = new ArrayList<>();
    private boolean isSurrendered = false;

    public PlayerHand(int handIndex) {
        this.name = "Hand " + (handIndex + 1);
    }

    public void setWager(BigDecimal wager) {
        this.wager = wager;
    }

    public BigDecimal getWager() {
        return this.wager;
    }

    public String getName() {
        return this.name;
    }

    public void addCardToHand(Card card) {
        cardsInHand.add(card);
    }

    public List<Card> getCardsInHand(){
        return this.cardsInHand;
    }

    public String printHand() {
        return this.name + ": " + cardsInHand;
    }

    public int calculateHandValue() {
        int sumOfHand = 0;
        final int ACE_BIG_VALUE = 11;
        int aceCount = 0;
        for (Card eachCard : cardsInHand) {
            if (eachCard.getCardValue() == 1) {
                sumOfHand += ACE_BIG_VALUE; //(11)
                aceCount++;
            } else {
                sumOfHand += eachCard.getCardValue();
            }
        }
        if (sumOfHand <= 21) {
            return sumOfHand;

        } else {
            for (int i = 0; i < aceCount; i++) {
                sumOfHand -= 10;
                if (sumOfHand <= 21) {
                    break;
                }
            }
        }
        return sumOfHand;
    }

    public void setIsSurrendered(boolean isSurrendered){
        this.isSurrendered = isSurrendered;
    }

    public boolean isSurrendered() {
        return isSurrendered;
    }

    public void removeCardFromHand(String cardName) throws CardInvalidException {
        boolean isCardFound = false;
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).toString().equalsIgnoreCase(cardName)) {
                cardsInHand.remove(i);
                isCardFound = true;
                break;
            }
        }
        if (!isCardFound) {
            throw new CardInvalidException("Error: Your card selection was not found in your hand.");
        }
    }
}