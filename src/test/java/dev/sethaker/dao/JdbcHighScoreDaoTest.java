package dev.sethaker.dao;

import dev.sethaker.resources.db.model.HighScore;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JdbcHighScoreDaoTest extends BaseDaoTest {


    private JdbcHighScoreDao sut;


    @Before
    public void setUp() {
        sut = new JdbcHighScoreDao(dataSource);
    }

    @Test
    public void createHighScore_returns_created_high_score(){
        HighScore highScore = new HighScore();
        highScore.setScore(new BigDecimal("100"));
        highScore.setUserId("SSS");

        assertTrue(sut.createHighScore(highScore));;


    }


}