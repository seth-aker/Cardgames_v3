package dev.sethaker.resources.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck = new Stack<>();
    private List<Card> discardPile = new ArrayList<>();

    public Deck (int numberOfDeckSets) {
        int CARDS_PER_SUIT = 13;
        int setCount = 0;
        do {
            for (int i = 0; i < CARDS_PER_SUIT; i++) {
                deck.add(new Card(i + 1, 'C'));
                deck.add(new Card(i + 1, 'S'));
                deck.add(new Card(i + 1, 'D'));
                deck.add(new Card(i + 1, 'H'));
            }
            setCount++;
        } while (setCount < numberOfDeckSets);
    }

    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    public Card drawCard() {
        return deck.pop();
    }

    public Stack<Card> getCards(){
        return deck;
    }

    public void discard(Card card){
        this.discardPile.add(card);
    }

    public void discardCards(List<Card> cards){
        this.discardPile.addAll(cards);
    }

    public void resetDeck() {
        this.deck.addAll(discardPile);
        discardPile.clear();

        Collections.shuffle(this.deck);
    }

    public boolean isTimeToResetDeck(){
        return this.deck.size() < this.discardPile.size();
    }
}
