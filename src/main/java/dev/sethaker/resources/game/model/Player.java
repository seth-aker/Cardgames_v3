package dev.sethaker.resources.game.model;

import dev.sethaker.exceptions.CardInvalidException;
import dev.sethaker.services.Input;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Player {
    private int playerNumber;
    private int playerPoints = 0;
    private final String playerName;
    private BigDecimal playerMoney = new BigDecimal("100");
    private List<PlayerHand> playerHands = new ArrayList<>();


    public Player(String playerName) {
        this.playerName = playerName;
    }

    public void addNewHand(PlayerHand playerHand) {
        playerHands.add(playerHand);
    }

    public List<PlayerHand> getPlayerHands() {
        return this.playerHands;
    }

    public String getPlayerName() {
        return playerName;
    }

    public BigDecimal getPlayerMoney() {
        return playerMoney;
    }

    public void setBuyIn(BigDecimal buyIn) {
        this.playerMoney = buyIn;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void addToPlayerMoney(BigDecimal moneyToAdd) {
        this.playerMoney = playerMoney.add(moneyToAdd);
    }

    public void subtractFromPlayerMoney(BigDecimal moneyToSubtract) {
        this.playerMoney = playerMoney.subtract(moneyToSubtract);
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Card getCardChoice( Input consoleService) {
        boolean isValidSelection = false;
        Card returnCard = null;
        while (!isValidSelection) {
            try {
                String cardChoiceString = consoleService.requestCardSelection(this.playerHands.get(0));
                cardChoiceString = checkForValidSelection(cardChoiceString);
                if(cardChoiceString != null){
                    returnCard = createCard(cardChoiceString);
                    isValidSelection = true;
                }
            } catch (CardInvalidException e) {
                System.out.println(e.getMessage());
            }
        }
        return returnCard;

    }

    public Card getCardChoice(boolean heartsPlayable, String cardsAlreadyPlayed, char firstCardSuit) {
        return null;
    }


    public void addToPlayerPoints(int pointsToAdd) {
        playerPoints += pointsToAdd;
    }

    public String checkForValidSelection(String cardChoiceString) throws CardInvalidException {
        int indexOfCardChoice = -1;
        String validCardChoiceString = null;
        if (cardChoiceString.length() > 3) {
            for (Card eachCard : playerHands.get(0).getCardsInHand()) {
                indexOfCardChoice = cardChoiceString.indexOf(eachCard.toString());
                if (indexOfCardChoice != -1) {
                    break;
                }
            }
            if(indexOfCardChoice == -1){
                throw new CardInvalidException("You must play a card from your hand.");
            }
            cardChoiceString += " ";
            cardChoiceString = cardChoiceString.substring(indexOfCardChoice, indexOfCardChoice + 3).replaceAll("[^a-zA-Z0-9]", "").trim();

        } else if (cardChoiceString.length() == 3 && !cardChoiceString.startsWith("10")) {
            cardChoiceString = cardChoiceString.replaceAll("[^a-zA-Z0-9]", "").trim();
        }

        for (Card eachCard : playerHands.get(0).getCardsInHand()) {
            if (eachCard.toString().equalsIgnoreCase(cardChoiceString)) {
                validCardChoiceString = eachCard.toString();
                break;
            }
        }

        return validCardChoiceString;
    }

    public Card createCard(String cardChoiceString) throws CardInvalidException {
        Card returnCard = null;

        for (Card eachCard : playerHands.get(0).getCardsInHand()) {
            if (eachCard.toString().equalsIgnoreCase(cardChoiceString)) {
                returnCard = eachCard;
                break;
            }
        }

        if (returnCard == null) {
            throw new CardInvalidException("Invalid card choice, please select a card from your hand.");
        }


        return returnCard;
    }
}
