package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.HighScore;

import java.util.List;

public interface HighScoreDao {

    List<HighScore> getTopTenHighScores();
    HighScore getHighScoreById(int highScoreId);
    HighScore createHighScore(HighScore highScore);
    HighScore updateHighScore(HighScore highScore);
    int deleteHighScore(int highScoreId);

}
