package dev.sethaker.cardgames;



import dev.sethaker.resources.game.model.*;



import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Blackjack extends CardGame {
    private static final BigDecimal MIN_WAGER_AMOUNT = new BigDecimal("5");
    private final Deck deck = new Deck(6);
    public final int BLACKJACK = 21;
    protected ComputerPlayer dealer;

    public Blackjack(String playerName) {
        super();
        this.player = new Player(playerName);
        for (Card eachCard : deck.getCards()) {
            eachCard.setCardValue("Blackjack");
        }
        deck.shuffleDeck();

        dealer = new ComputerPlayer("Dealer");


    }

    public void run() {
        while (!isGameOver) {
            dealer.addNewHand(new PlayerHand(0));
            player.addNewHand(new PlayerHand(0));

            if (deck.isTimeToResetDeck()) {
                deck.resetDeck();
            }

            roundCount++;
            System.out.println("Round " + roundCount);
            setWager(player.getPlayerHands().get(0));
            player.getPlayerHands().get(0).addCardToHand(deck.drawCard());
            dealer.getPlayerHands().get(0).addCardToHand(deck.drawCard());
            player.getPlayerHands().get(0).addCardToHand(deck.drawCard());

            System.out.println(player.getPlayerName() + "'s " + player.getPlayerHands().get(0).printHand());
            System.out.println(dealer.getPlayerName() + "'s " + dealer.getPlayerHands().get(0).printHand());
            dealer.getPlayerHands().get(0).addCardToHand(deck.drawCard());

            boolean hasNaturals = checkNaturals();
            if (!hasNaturals) {
                playRound();
                playDealer();
                compareHands();
            }


            if (player.getPlayerMoney().compareTo(MIN_WAGER_AMOUNT) < 0) {
                System.out.println("You don't have enough money to continue. Try again next time.");
                isGameOver = true;
            } else {
                System.out.println("Would you like to play again?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                isGameOver = !consoleService.getChoice();
            }
            if (!isGameOver) {
                deck.discardCards(dealer.getPlayerHands().get(0).getCardsInHand());
                dealer.getPlayerHands().clear();
                for (PlayerHand eachHand : player.getPlayerHands()) {
                    deck.discardCards(eachHand.getCardsInHand());
                }
                player.getPlayerHands().clear();
            } else {
                consoleService.postToLeaderBoard(player.getPlayerMoney());
                consoleService.displayLeaderboard();
                System.out.println("Thank you for playing! Come again soon!");

            }
        }
    }

    public void setWager(PlayerHand playerHand) {
        int intFromUser;
        while (true) {
            System.out.println(player.getPlayerName() + ", your current wallet is: $" + player.getPlayerMoney() + ". Please enter your wager: ");
            intFromUser = consoleService.getIntFromUser();
            BigDecimal wager = new BigDecimal(intFromUser);
            if (wager.compareTo(MIN_WAGER_AMOUNT) >= 0 && wager.compareTo(player.getPlayerMoney()) <= 0) {
                playerHand.setWager(wager);
                break;
            } else {
                System.out.println("Invalid wager amount");
            }
        }
    }

    public boolean checkNaturals() {
        boolean hasNatural = false;
        int dealerHandValue = dealer.getPlayerHands().get(0).calculateHandValue();
        for (PlayerHand eachHand : player.getPlayerHands()) {
            int playerHandValue = eachHand.calculateHandValue();
            if (dealerHandValue == BLACKJACK) {
                System.out.println("Dealer got a blackjack!");
                System.out.println("Dealer's cards: " + dealer.getPlayerHands().get(0).getCardsInHand());
                hasNatural = true;
                if (playerHandValue == BLACKJACK) {
                    System.out.println(player.getPlayerName() + " also got a blackjack, it's a stand-off.");
                } else {
                    System.out.println("Time to pay out");
                    player.subtractFromPlayerMoney(eachHand.getWager());
                    System.out.println(player.getPlayerName() + "'s money left: $" + player.getPlayerMoney());
                }
            } else if (playerHandValue == BLACKJACK) {
                System.out.println(player.getPlayerName() + " got a blackjack!");
                System.out.println("Dealer paid out: $" + eachHand.getWager().multiply(new BigDecimal(2)));
                player.addToPlayerMoney(eachHand.getWager());
                hasNatural = true;
            }
        }
        return hasNatural;
    }

    public void playRound() {
        final int STAY = 1;
        final int HIT = 2;
        final int SURRENDER = 3;
        final int DOUBLE_DOWN = 4;
        final int SPLIT = 5;


        for (int i = 0; i < getPlayerHands().size(); i++) {
            boolean isHandOver = false;
            int card1Pip = player.getPlayerHands().get(i).getCardsInHand().get(0).getCardValue();
            int card2Pip = player.getPlayerHands().get(i).getCardsInHand().get(1).getCardValue();
            while (!isHandOver) {
                System.out.println(player.getPlayerName() + " Please choose an option for " + player.getPlayerHands().get(i).getName() + ": ");
                System.out.println("Current cards: " + player.getPlayerHands().get(i).getCardsInHand());
                System.out.println("(1) Stay");
                System.out.println("(2) Hit");
                System.out.println("(3) Surrender");
                if (player.getPlayerMoney().subtract(player.getPlayerHands().get(i).getWager()).compareTo(player.getPlayerHands().get(i).getWager()) >= 0) {
                    System.out.println("(4) Double Down");
                }
                if (card1Pip == card2Pip && player.getPlayerHands().get(i).getCardsInHand().size() == 2) {
                    System.out.println("(5) Split");
                }
                int userChoice = consoleService.getIntFromUser();
                if (userChoice == STAY) {
                    isHandOver = true;
                } else if (userChoice == HIT) {
                    isHandOver = hit(player.getPlayerHands().get(i));
                } else if (userChoice == DOUBLE_DOWN) {
                    doubleDown(player.getPlayerHands().get(i));
                    isHandOver = true;
                } else if (userChoice == SURRENDER) {
                    isHandOver = true;
                    player.getPlayerHands().get(i).setIsSurrendered(true);
                    MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
                    player.subtractFromPlayerMoney(player.getPlayerHands().get(i).getWager().divide(new BigDecimal(2), mc));
                } else if (card1Pip == card2Pip && userChoice == SPLIT && player.getPlayerHands().get(i).getCardsInHand().size() == 2) {
                    split(player.getPlayerHands().get(i));
                } else {
                    System.out.println("Invalid input");
                }

            }
        }
    }

    private boolean hit(PlayerHand playerHand) {
        playerHand.addCardToHand(deck.drawCard());
        System.out.println("Current Cards: " + playerHand.getCardsInHand());
        if (playerHand.calculateHandValue() > BLACKJACK) {
            System.out.println("Bust!");
            System.out.println("Time to pay out");
            player.subtractFromPlayerMoney(playerHand.getWager());
            deck.discardCards(playerHand.getCardsInHand());
            playerHand.getCardsInHand().clear();
            return true;
        } else if (playerHand.calculateHandValue() == BLACKJACK) {
            System.out.println("Blackjack!");
            return true;
        } else {
            return false;
        }
    }

    private void doubleDown(PlayerHand playerHand) {
        playerHand.addCardToHand(deck.drawCard());
        System.out.println("Current cards: " + playerHand.getCardsInHand());
        int handValue = playerHand.calculateHandValue();
        if (handValue > BLACKJACK) {
            System.out.println("Bust!");
            System.out.println("Time to pay out");
            player.subtractFromPlayerMoney(playerHand.getWager().multiply(new BigDecimal(2)));
            deck.discardCards(playerHand.getCardsInHand());
            playerHand.getCardsInHand().clear();
        } else if (handValue == BLACKJACK) {
            System.out.println("Blackjack!");
            System.out.println("You won $" + playerHand.getWager().multiply(new BigDecimal(3)));
            player.addToPlayerMoney(playerHand.getWager().multiply(new BigDecimal(3)));
        }
    }

    private void split(PlayerHand playerHand) {
        if (player.getPlayerMoney().compareTo((MIN_WAGER_AMOUNT.add(playerHand.getWager()))) < 0) {
            System.out.println("Player does not have enough money to wager on a new hand. Please choose another option.");

        } else {
            int numberOfPlayerHands = player.getPlayerHands().size();
            player.addNewHand(new PlayerHand(numberOfPlayerHands));
            Card splitCard = playerHand.getCardsInHand().remove(1);
            playerHand.addCardToHand(deck.drawCard());

            getPlayerHands().get(numberOfPlayerHands).addCardToHand(splitCard);
            getPlayerHands().get(numberOfPlayerHands).addCardToHand(deck.drawCard());

            setWager(getPlayerHands().get(numberOfPlayerHands));
        }
    }

    private void playDealer() {
        boolean shouldPlayDealer = false;
        for (PlayerHand eachHand : getPlayerHands()) {
            if (!eachHand.isSurrendered()) {
                shouldPlayDealer = true;
                break;
            }
        }
        if (shouldPlayDealer) {
            PlayerHand dealerHand = dealer.getPlayerHands().get(0);
            System.out.println("Dealer's Hand: " + dealerHand.getCardsInHand().toString());
            int dealerHandValue = dealerHand.calculateHandValue();
            while (dealerHandValue < 17) {
                System.out.println("Dealer hits.");
                dealerHand.addCardToHand(deck.drawCard());
                System.out.println("Dealer's hand: " + dealerHand.getCardsInHand().toString());
                dealerHandValue = dealerHand.calculateHandValue();
            }
            if (dealerHandValue == BLACKJACK) {
                System.out.println("Dealer got blackjack!");

            } else if (dealerHandValue > BLACKJACK) {
                System.out.println("Dealer busts!");
            }

        }
    }

    private void compareHands() {
        int dealerHandValue = dealer.getPlayerHands().get(0).calculateHandValue();
        for (PlayerHand eachHand : getPlayerHands()) {
            if (eachHand.isSurrendered()) {
                continue;
            }
            int handValue = eachHand.calculateHandValue();
            if (handValue < dealerHandValue && handValue != 0 && dealerHandValue <= BLACKJACK) {
                System.out.println("The dealer's hand (" + dealerHandValue + ") beat " + player.getPlayerName() + "'s " + eachHand.getName() + " (" + handValue + ")");
                System.out.println(player.getPlayerName() + " paid out $" + eachHand.getWager());
                player.subtractFromPlayerMoney(eachHand.getWager());
            } else if (handValue == dealerHandValue && dealerHandValue <= BLACKJACK) {
                System.out.println(player.getPlayerName() + "'s " + eachHand.getName() + " (" + handValue + ")" + " tied with the dealer's hand (" + dealerHandValue + "), no payout.");
            } else if (handValue != 0 && handValue <= BLACKJACK) {
                System.out.println(player.getPlayerName() + "'s " + eachHand.getName() + " (" + handValue + ")" + " beat the dealer's hand (" + dealerHandValue + ")");
                System.out.println(player.getPlayerName() + " won $" + eachHand.getWager());
                player.addToPlayerMoney(eachHand.getWager());

            }
            System.out.println(player.getPlayerName() + "'s current wallet: $" + player.getPlayerMoney() + "\n");
        }
    }
}
