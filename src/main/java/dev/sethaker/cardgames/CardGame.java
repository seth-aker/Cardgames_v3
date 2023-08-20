package dev.sethaker.cardgames;

import dev.sethaker.resources.game.model.Player;
import dev.sethaker.resources.game.model.PlayerHand;
import dev.sethaker.services.ConsoleService;



import java.util.List;

public abstract class CardGame {
    protected ConsoleService consoleService;
    protected boolean isGameOver = false;
    protected int numberOfPlayers;
    protected int roundCount = 0;
    protected Player player;



    public CardGame(){
        consoleService = new ConsoleService();
    }

    public void run(){
    }

    public void setWager(PlayerHand playerHand){

    }

    public List<PlayerHand> getPlayerHands(){
        return this.player.getPlayerHands();
    }
}
