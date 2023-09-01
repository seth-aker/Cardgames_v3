package dev.sethaker.services;

import dev.sethaker.resources.game.model.PlayerHand;

import java.math.BigDecimal;


public interface Input {
    public String requestCardSelection(PlayerHand playerHand);

    public String requestCardSelection(String previousCardsPlayed, String query);

    void postToLeaderBoard(BigDecimal money);
}