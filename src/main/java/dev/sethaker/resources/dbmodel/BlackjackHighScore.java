package dev.sethaker.resources.dbmodel;

import java.math.BigDecimal;
import java.sql.Date;

public class BlackjackHighScore extends HighScore {
    int highScoreId;
    BigDecimal score;
    String userName;
    Date dateCreated;

    public int getHighScoreId() {
        return highScoreId;
    }

    public void setHighScoreId(int highScoreId) {
        this.highScoreId = highScoreId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
