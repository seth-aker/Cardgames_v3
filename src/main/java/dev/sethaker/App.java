package dev.sethaker;


import dev.sethaker.cardgames.*;
import dev.sethaker.services.ConsoleService;

public class App {
    private final ConsoleService consoleService = new ConsoleService();
    private CardGame cardGame;

    public static void main(String[] args) {
        App app = new App();
        app.runMainMenu();
    }

    private void runMainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.getIntFromUser();
            if (menuSelection == 1) {
                System.out.println("Initializing Blackjack...");
                cardGame = new Blackjack(consoleService.getPlayerName());
                cardGame.run();

            } else if (menuSelection == 2) {

                boolean hasKey = consoleService.printHeartsSetupWarningAndGetUserResponse();
                if(!hasKey){
                    break;
                }
                System.out.println("Initializing Hearts...");
                cardGame = new Hearts(consoleService.getPlayerName());
                cardGame.run();
            } else if (menuSelection != 0) {
                System.out.println("Invalid input, please enter 0, 1, or 2");
            }
        }
    }


}
