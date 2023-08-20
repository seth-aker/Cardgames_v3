package dev.sethaker.services;



import dev.sethaker.resources.game.model.Player;
import dev.sethaker.resources.game.model.PlayerHand;

import java.util.List;
import java.util.Scanner;

public class ConsoleService implements Input {
    private final Scanner scanner = new Scanner(System.in);

    public void printMainMenu() {
        System.out.println("-------------Main Menu-------------");
        System.out.println("Welcome to Console Card Games!");
        System.out.println("Please select a game to play:");
        System.out.println("(1) Blackjack");
        System.out.println("(2) Hearts");
        System.out.println("(0) Exit");
    }

    public boolean printHeartsSetupWarningAndGetUserResponse(){
        System.out.println("\n--------------STOP--------------\n" +
                "This part of the application uses an Openai api to make decisions for the computer players. \n" +
                "You must have an openai API key environment variable set to \"OPENAI_API_KEY\" in order to run this program. \n" +
                "Please visit https://platform.openai.com/ to request a API key." +
                "\n\nHave you set up your system with a valid key? (Y)/(N)");
        String userInput;
        userInput = scanner.nextLine();
        if(userInput.equalsIgnoreCase("Y")){
            return true;
        } else {
            return false;
        }

    }

    public int getIntFromUser() {
        int userInput;
        try {
            userInput = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number");
            userInput = -1;
        }
        return userInput;
    }

    public String getPlayerName() {
        System.out.println("Please enter your name: ");
        return scanner.nextLine();
    }

    public boolean getPlayAgain() {
        int userChoice;
        boolean playAgain;
        while (true) {
            try {
                userChoice = Integer.parseInt(scanner.nextLine());
                if (userChoice == 1) {
                    playAgain = true;
                    break;
                } else if (userChoice == 2) {
                    playAgain = false;
                    break;
                }
                System.out.println("Invalid input, please enter either 1 or 2");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
        return playAgain;
    }

    public int printPlayerScores(List<Player> playerList){
        int highestPoint = 0;
        System.out.println("------------Current player scores------------");
        for (Player eachPlayer: playerList){
            highestPoint = Math.max(eachPlayer.getPlayerPoints(), highestPoint);
            System.out.println(eachPlayer.getPlayerName() + ": " + eachPlayer.getPlayerPoints());
        }
        return highestPoint;
    }

    public String requestCardSelection(PlayerHand playerHand){
        System.out.println("What card would you like to play?");
        System.out.println(playerHand.getCardsInHand().toString());
        String selection = scanner.nextLine();
        return selection;
    }

    public String requestCardSelection(String x, String y){
        return null;
    }
}
