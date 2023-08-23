package dev.sethaker.resources.db.model;



public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String displayName;

    public User(){};

    public User(int userId, String username, String displayName, String passwordHash ){
        this.userId = userId;
        this.username= username;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
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
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
