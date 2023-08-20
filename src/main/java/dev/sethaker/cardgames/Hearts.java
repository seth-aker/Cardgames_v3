package dev.sethaker.cardgames;

import dev.sethaker.exceptions.CardInvalidException;
import dev.sethaker.resources.game.model.*;


import java.util.ArrayList;
import java.util.List;

public class Hearts extends CardGame {
    private final Deck deck = new Deck(1);
    private final ComputerPlayer COMPUTER_PLAYER_1 = new ComputerPlayer("Computer 1");
    private final ComputerPlayer COMPUTER_PLAYER_2 = new ComputerPlayer("Computer 2");
    private final ComputerPlayer COMPUTER_PLAYER_3 = new ComputerPlayer("Computer 3");
    private final int LOSING_SCORE = 100;
    private final List<Player> players = new ArrayList<>();
    private int[] playerOrder;
    private Table table;
    private boolean heartsPlayable;

    public Hearts(String playerName) {
        this.numberOfPlayers = 1;
        setCardValues();
        deck.shuffleDeck();

        this.player = new Player(playerName);
        this.player.setPlayerNumber(1);
        COMPUTER_PLAYER_1.setPlayerNumber(2);
        COMPUTER_PLAYER_2.setPlayerNumber(3);
        COMPUTER_PLAYER_3.setPlayerNumber(4);

        players.add(player);
        players.add(COMPUTER_PLAYER_1);
        players.add(COMPUTER_PLAYER_2);
        players.add(COMPUTER_PLAYER_3);

        System.out.println("Let's play hearts! The goal is to get the least amount of points. The game ends when one player reaches 100 points.");
    }

    private void setCardValues() {
        for (Card eachCard : deck.getCards()) {
            eachCard.setCardValue("Hearts");
            if(eachCard.getCardPip() == 1){
                eachCard.setCardPip(14);
            }
        }

    }

    public void run() {
        boolean isGameOver = false;
        while (!isGameOver) {
            setCardValues();
            heartsPlayable = false;
            roundCount++;
            System.out.println("Round " + roundCount);
            dealHands();
            System.out.println(player.getPlayerHands().get(0).printHand());
            //TODO: Have players choose two cards to swap.

            //Find player who has the 2C.
            int playerToGoFirst = find2C();
            //create the player order.
            setPlayerOrder(playerToGoFirst);
            table = new Table();

            playRound();

            int highestScore = consoleService.printPlayerScores(players);
            if (highestScore >= LOSING_SCORE) {
                isGameOver = true;
            } else {
                deck.resetDeck();
            }

        }
    }

    private void dealHands() {
        for (Player eachPlayer : players) {
            eachPlayer.addNewHand(new PlayerHand(0));
        }
        while (deck.getCards().size() > 0)
            for (Player eachPlayer : players) {
                eachPlayer.getPlayerHands().get(0).addCardToHand(deck.drawCard());
            }
    }

    private int find2C() {
        int playerWith2C = 0;
        for (Player eachPlayer : players) {
            boolean foundCard = false;
            List<Card> cardsInHand = eachPlayer.getPlayerHands().get(0).getCardsInHand();
            for (Card eachCard : cardsInHand) {
                if (eachCard.getCardPip() == 2 && eachCard.getSuit() == 'C') {
                    foundCard = true;
                    break;
                }
            }
            if (foundCard) {
                playerWith2C = eachPlayer.getPlayerNumber();
                break;
            }

        }
        return playerWith2C;
    }

    private void setPlayerOrder(int playerToGoFirst) {
        if (playerToGoFirst == 1) {
            this.playerOrder = new int[]{0, 1, 2, 3};
        } else if (playerToGoFirst == 2) {
            this.playerOrder = new int[]{1, 2, 3, 0};
        } else if (playerToGoFirst == 3) {
            this.playerOrder = new int[]{2, 3, 0, 1};
        } else {
            this.playerOrder = new int[]{3, 0, 1, 2};
        }
    }

    private void playRound() {
        int trickCounter = 1;
        while (player.getPlayerHands().get(0).getCardsInHand().size() > 0) {
            playTrick(trickCounter);
            trickCounter++;
        }
    }

    private void playTrick(int trickCount) {
        //Loop gets the choices from the players for one trick
        char firstCardSuit = '0';
        String cardsAlreadyPlayed = "";
        for (int i = 0; i < playerOrder.length; i++) {
            int computerThinkingCounter = 0;
            int playerNumber = players.get(playerOrder[i]).getPlayerNumber();
            Card cardChoice;
            boolean isValidCardChoice = false;

            while (!isValidCardChoice) {
                try {
                    //gets card choice{
                    if (playerNumber == 1) {
                        System.out.println(players.get(playerOrder[i]).getPlayerName() + "'s turn:");
                        cardChoice = players.get(playerOrder[i]).getCardChoice(consoleService);
                    } else {
                        cardChoice = players.get(playerOrder[i]).getCardChoice(heartsPlayable, cardsAlreadyPlayed, firstCardSuit);
                    }
                    //}
                    if (i == 0) {
                        if (trickCount == 1 && !cardChoice.toString().equals("2C")) {
                            throw new CardInvalidException(" You must play the 2C if you have it. ");
                        }
                        if(!heartsPlayable && cardChoice.getSuit() == 'H'){
                            throw new CardInvalidException("Illegal play: The hearts have not been played yet this round.");
                        }
                        table.getTrick().setFirstCardSuit(cardChoice.getSuit());
                        firstCardSuit = cardChoice.getSuit();
                    } else {
                        checkIsValidCardSuit(cardChoice, players.get(playerOrder[i]).getPlayerHands().get(0));
                    }


                    //puts the card into the table/trick
                    table.playCard(cardChoice, playerNumber);
                    String cardPlayed = players.get(playerOrder[i]).getPlayerName() + " played the " + cardChoice;
                    System.out.println(cardPlayed);
                    cardsAlreadyPlayed += cardPlayed + "\n";
                    //removes card from the player's hand;
                    players.get(playerOrder[i]).getPlayerHands().get(0).removeCardFromHand(cardChoice.toString());
                    isValidCardChoice = true;

                } catch (CardInvalidException e) {
                    if(playerNumber == 1)
                    System.out.println(e.getMessage());

                    if (playerNumber != 1) {
                        computerThinkingCounter++;
                        if(computerThinkingCounter % 5 == 0){
                            System.out.println(players.get(playerOrder[i]).getPlayerName() + " is thinking...");
                            cardsAlreadyPlayed += " " + e.getMessage();
                        }
                    }
                }
            }
        }

        //after all cards have been played
        int trickPoints = table.calculateTrickValue();
        int winnerIndex = table.getTrick().getWinnerOfTrickPlayerIndex();
        players.get(winnerIndex).addToPlayerPoints(trickPoints);
        System.out.println("The trick went to: " + players.get(winnerIndex).getPlayerName() + "\n");
        setPlayerOrder(winnerIndex + 1);
        deck.discardCards(table.discardTrick());
    }

    public void checkIsValidCardSuit(Card cardChoice, PlayerHand playerHand) throws CardInvalidException {
        if (table.getTrick().getFirstCardSuit() != cardChoice.getSuit()) {
            for (Card eachCard : playerHand.getCardsInHand()) {
                if (eachCard.getSuit() == table.getTrick().getFirstCardSuit()) {
                    throw new CardInvalidException("Please select a card whose suit matches the first card played this round.");
                }
            }
            if(cardChoice.getSuit() == 'H'){
                heartsPlayable = true;
            }
        }
    }


    public Table getTable(){
        return table;
    }
    public void setTable(Table table){
        this.table = table;
    }
    public boolean getHeartsPlayable(){
        return heartsPlayable;
    }
}
