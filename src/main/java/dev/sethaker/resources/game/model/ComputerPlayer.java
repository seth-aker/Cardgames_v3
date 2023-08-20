package dev.sethaker.resources.game.model;

import dev.sethaker.exceptions.CardInvalidException;
import dev.sethaker.services.ChatGPTService;


public class ComputerPlayer extends Player {
    ChatGPTService chatGPTService = new ChatGPTService();

    public ComputerPlayer(String playerName) {
        super(playerName);
    }

    public Card getCardChoice(boolean heartsPlayable, String cardsAlreadyPlayed, char firstCardSuit) {

        boolean isValidCardChoice = false;
        String cardsInHand = this.getPlayerHands().get(0).printHand();
        String heartsStatus = "";
        String cardChoiceString = null;
        Card returnCard = null;
        if(heartsPlayable){
            heartsStatus = "The hearts have been played already in a previous round.";
        }
        String prompt =  heartsStatus + "Please select a card from the following:" + "\nCards in your hand: " + cardsInHand;
        if(firstCardSuit != '0'){
            prompt += " If you have a card whose suit matches " + firstCardSuit + " then that card must be played";
        }
        while(!isValidCardChoice) {
            try {
                cardChoiceString = chatGPTService.requestCardSelection(cardsAlreadyPlayed, prompt);
                cardChoiceString = checkForValidSelection(cardChoiceString);
                returnCard = createCard(cardChoiceString);
                isValidCardChoice = true;
            } catch (CardInvalidException e){
                prompt += e.getMessage();
            }
        }

        return returnCard;
    }



}
