package dev.sethaker.resources.game.model;


public class Trick {
    private Card[] cards = new Card[4];
    //card[0] = player1
    //card[1] = player2
    //card[2] = player3
    //card[3] = player4
    char firstCardSuit;


    public Trick() {
    }

    public void addCard(Card card, int playerNumber) {
        this.cards[playerNumber - 1] = card;
    }

    public Card[] getCards() {
        return cards;
    }

    public void clearCards() {
        cards[0] = null;
        cards[1] = null;
        cards[2] = null;
        cards[3] = null;
    }

    public int getWinnerOfTrickPlayerIndex() {
        int winner = -1;
        int highestCardInSuitPip = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getSuit() == firstCardSuit && cards[i].getCardPip() > highestCardInSuitPip) {
                highestCardInSuitPip = cards[i].getCardPip();
                winner = i;
            }
        }
        return winner;
    }

    public void setFirstCardSuit(char firstCardSuit) {
        this.firstCardSuit = firstCardSuit;
    }

    public char getFirstCardSuit(){
        return this.firstCardSuit;
    }

}
