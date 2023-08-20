package dev.sethaker.services;

import dev.sethaker.resources.game.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConsoleServiceTest {

    ConsoleService consoleService = new ConsoleService();

    @Test
    public void consoleService_Test_Print_Player_Scores(){
        List<Player> players = new ArrayList<>();
        Player testPlayer1 = new Player("TestPlayer1");
        Player testPlayer2 = new Player("TestPlayer2");
        Player testPlayer3 = new Player("TestPlayer3");

        testPlayer1.addToPlayerPoints(10);
        testPlayer2.addToPlayerPoints(50);
        players.add(testPlayer1);
        players.add(testPlayer2);
        players.add(testPlayer3);
        int highestScore = consoleService.printPlayerScores(players);
        int expectedHighestScore = 50;

        assertEquals("Highest should be 50", expectedHighestScore, highestScore);
    }
}