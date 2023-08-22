package dev.sethaker.resources.dbmodel;

public class User {
    private int userId;
    private String username;
    private String password_hash;
    private String displayName;

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

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
