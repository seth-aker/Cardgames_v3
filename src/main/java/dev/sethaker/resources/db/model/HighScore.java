package dev.sethaker.resources.db.model;

import java.math.BigDecimal;
import java.sql.Date;

public class HighScore {
    private int highScoreId;
    private String userId;
    private BigDecimal score;
    private Date dateCreated;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getHighScoreId() {
        return highScoreId;
    }

    public void setHighScoreId(int highScoreId) {
        this.highScoreId = highScoreId;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString(){
        return "Player: " + userId + " || Winnings: $" + score;
    }
}
