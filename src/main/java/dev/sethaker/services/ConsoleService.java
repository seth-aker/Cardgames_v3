package dev.sethaker.services;



import dev.sethaker.dao.HighScoreDao;

import dev.sethaker.dao.JdbcHighScoreDao;
import dev.sethaker.resources.db.model.HighScore;
import dev.sethaker.resources.game.model.Player;
import dev.sethaker.resources.game.model.PlayerHand;
import org.apache.commons.dbcp2.BasicDataSource;



import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService implements Input {
    private final Scanner scanner = new Scanner(System.in);
    private final String NEON_DB_URL = System.getenv("NEON_DB_URL");
    private final BasicDataSource dataSource = new BasicDataSource();
    private final HighScoreDao highScoreDao;
    public ConsoleService(){
        dataSource.setUrl(NEON_DB_URL);
        this.highScoreDao = new JdbcHighScoreDao(dataSource);
    }
    public void printMainMenu() {
        System.out.println("\n" +
                "   ______                        __   ______                                         _    __   _____\n" +
                "  / ____/  ____ _   _____   ____/ /  / ____/  ____ _   ____ ___     ___     _____   | |  / /  |__  /\n" +
                " / /      / __ `/  / ___/  / __  /  / / __   / __ `/  / __ `__ \\   / _ \\   / ___/   | | / /    /_ < \n" +
                "/ /___   / /_/ /  / /     / /_/ /  / /_/ /  / /_/ /  / / / / / /  /  __/  (__  )    | |/ /   ___/ / \n" +
                "\\____/   \\__,_/  /_/      \\__,_/   \\____/   \\__,_/  /_/ /_/ /_/   \\___/  /____/     |___/   /____/  \n");

        System.out.println("-------------Main Menu-------------");
        System.out.println("Welcome to Console Card Games!");
        System.out.println("Please select a game to play:");
        System.out.println("(1) Blackjack");
        System.out.println("(2) Hearts");
        System.out.println("(3) BlackJack Leaderboard");
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

    private String getHighScoreName() {
        while (true) {
            String name = scanner.nextLine();
            if(name.length() != 3){
                System.out.println("Name can only be 3 characters long.\nPlease enter a name: ");
                continue;
            }
            return name.toUpperCase();
        }
    }

    public boolean getChoice() {
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

    @Override
    public void postToLeaderBoard(BigDecimal money) {
        System.out.println("Ending money amount: $" +money);
        System.out.println("Would you like to post your winnings to the Leaderboard? \n(1) Yes\n(2) No");
        boolean post = getChoice();
        if(post){
            HighScore newScore = new HighScore();
            System.out.println("Please enter a 3 character name: ");
            newScore.setUserId(getHighScoreName());
            newScore.setScore(money);
            highScoreDao.createHighScore(newScore);
        }

    }
    public void displayLeaderboard(){
        List<HighScore> leaderBoard = highScoreDao.getTopTenHighScores();

        System.out.println("#########################################");
        System.out.println("###############Leaderboard###############");
        System.out.println("##                                     ##");
        for(HighScore eachScore: leaderBoard){
            System.out.println("##   " + eachScore.toString() + "   ##");
        }
        System.out.println("##                                     ##");
        System.out.println("#########################################");


    }
}
