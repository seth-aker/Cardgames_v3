package dev.sethaker.dao;

import dev.sethaker.resources.db.model.HighScore;


import java.util.List;

public interface HighScoreDao {

    List<HighScore> getTopTenHighScores();
    List<HighScore> getHighScoresByUser(int userId);
    HighScore getHighScoreById(int highScoreId);
    HighScore createHighScore(HighScore highScore);



}
