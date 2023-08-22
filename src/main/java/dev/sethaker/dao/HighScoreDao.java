package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.HighScore;


import java.util.List;

public interface HighScoreDao {

    List<HighScore> getTopTenHighScores();
    List<HighScore> getHighScoresByUser(int userId);
    HighScore getHighScoreById(int highScoreId);
    HighScore createHighScore(HighScore highScore);



}
