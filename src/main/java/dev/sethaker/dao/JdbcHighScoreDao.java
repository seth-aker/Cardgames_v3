package dev.sethaker.dao;

import dev.sethaker.exceptions.DaoException;

import dev.sethaker.resources.dbmodel.HighScore;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcHighScoreDao implements HighScoreDao{
    private final String SQL_BASE_TEXT = "SELECT u.display_name, hs.* FROM highscore AS hs JOIN users AS u ON hs.user_id = hs.user_id ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcHighScoreDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<HighScore> getTopTenHighScores() {
        List<HighScore> highScores = new ArrayList<>();
        String sql = SQL_BASE_TEXT + "ORDER BY hs.score DESC LIMIT 10";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next()){
                highScores.add(mapToHighScore(result));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return highScores;
    }

    @Override
    public HighScore getHighScoreById(int highScoreId) {
        HighScore highScore = null;
        String sql = SQL_BASE_TEXT + "WHERE u.user_id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, highScoreId);
            if(result.next()){
                highScore = mapToHighScore(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return highScore;
    }

    @Override
    public HighScore createHighScore(HighScore highScore) {
        HighScore newHighScore = null;
        String sql = "INSERT INTO highscore (score, user_id) VALUES (?, ?) RETURNING highscore_id;";
        try{
            int newHighScoreId = jdbcTemplate.queryForObject(sql, int.class, highScore.getScore(), highScore.getUserId());
            newHighScore = getHighScoreById(newHighScoreId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }
        return newHighScore;
    }

    @Override
    public HighScore updateHighScore(HighScore highScore) {
        return null;
    }

    @Override
    public int deleteHighScore(int highScoreId) {
        return 0;
    }

    private HighScore mapToHighScore(SqlRowSet rowSet){

        HighScore highScore = new HighScore();
        highScore.setHighScoreId(rowSet.getInt("highscore_id"));
        highScore.setDisplayName(rowSet.getString("display_name"));
        highScore.setUserId(rowSet.getInt("user_id"));
        highScore.setScore(rowSet.getBigDecimal("score"));
        highScore.setDateCreated(rowSet.getDate("date_created"));
        return highScore;
    }
}
