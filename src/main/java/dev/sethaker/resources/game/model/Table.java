package dev.sethaker.resources.game.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private final Trick trick;

    public Table() {
        this.trick = new Trick();
    }

    public void playCard(Card card, int playerNumber){
        trick.addCard(card, playerNumber);
    }

    public List<Card> discardTrick(){
        List<Card> cards = Arrays.stream(trick.getCards()).collect(Collectors.toList());
        trick.clearCards();
        return cards;
    }

    public int calculateTrickValue(){
        int trickPoints = 0;
        for(Card eachCard : trick.getCards()){
            trickPoints += eachCard.getCardValue();
        }
        return trickPoints;
    }

    public Trick getTrick() {
        return trick;
    }
}

