package dev.sethaker.resources.game.model;

public class Card {

    //carPip is the number or face of the card. cardValue is used to determine card sums against 21 or the dealer.
    private int cardPip;
    private int cardValue;
    private final char suit;
    private String cardName;
    private static final char CLUBS = 'C';
    private static final char DIAMONDS = 'D';
    private static final char SPADES = 'S';
    private static final char HEARTS = 'H';


    public Card(int cardPip, char suit) {
        this.cardPip = cardPip;
        this.suit = suit;

        if (cardPip > 1 && cardPip < 11) {
            this.cardName = Integer.toString(cardPip);
        } else if (cardPip == 1) {
            this.cardName = "A";
        } else if (cardPip == 11) {
            this.cardName = "J";
        } else if (cardPip == 12) {
            this.cardName = "Q";
        } else if (cardPip == 13) {
            this.cardName = "K";
        }

        if (suit == CLUBS) {
            this.cardName += "C";
        } else if (suit == DIAMONDS) {
            this.cardName += "D";
        } else if (suit == SPADES) {
            this.cardName += "S";
        } else if (suit == HEARTS) {
            this.cardName += "H";
        }

        //sets card point values to be added to determine if a hand goes over or not
    }

    public void setCardValue(String cardGameName) {
        if (cardGameName.equals("Blackjack")) {
            if (cardPip > 1 && cardPip < 11) {
                this.cardValue = cardPip;
            } else if (cardPip == 1) {
                this.cardValue = 1;
            } else if (cardPip == 11) {
                this.cardValue = 10;
            } else if (cardPip == 12) {
                this.cardValue = 10;
            } else if (cardPip == 13) {
                this.cardValue = 10;
            }
        }


        if (cardGameName.equals("Hearts")) {
            if (this.suit == HEARTS) {
                this.cardValue = 1;
            } else if (this.suit == SPADES && this.cardPip == 12) {
                this.cardValue = 13;
            } else {
                this.cardValue = 0;
            }
        }
    }


    public int getCardPip() {
        return cardPip;
    }

    public void setCardPip(int pip){
        this.cardPip = pip;
    }

    public int getCardValue() {
        return cardValue;
    }

    public char getSuit() {
        return suit;
    }


    @Override
    public String toString() {
        return cardName;
    }
}
