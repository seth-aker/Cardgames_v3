package dev.sethaker.services;

import dev.sethaker.resources.game.model.PlayerHand;

import java.math.BigDecimal;


public class TesterInput implements Input{
    @Override
    public String requestCardSelection(PlayerHand playerHand) {
        return "2C";
    }

    @Override
    public String requestCardSelection(String x, String y){
        return null;
    }

    @Override
    public void postToLeaderBoard(BigDecimal money) {

    }
}
