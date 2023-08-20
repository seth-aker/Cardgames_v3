package dev.sethaker.services;

import dev.sethaker.resources.game.model.PlayerHand;

import java.util.List;

public interface Input {
    public String requestCardSelection(PlayerHand playerHand);

    public String requestCardSelection(String previousCardsPlayed, String query);
}