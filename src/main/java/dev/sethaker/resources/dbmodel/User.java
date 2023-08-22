package dev.sethaker.resources.dbmodel;

import java.util.Locale;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String displayName;

    public User(){};

    public User(int userId, String username, String passwordHash){
        this.userId = userId;
        this.username= username;
        this.passwordHash = passwordHash;
        this.displayName = username.toUpperCase().substring(0,3);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        if (username.length() <= 3) {
            this.displayName = username.toUpperCase();
        } else {
            this.displayName = username.substring(0, 3).toUpperCase();
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
