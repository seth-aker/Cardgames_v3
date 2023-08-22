package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcUserDao implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        User newUser = null;
        String sql = "INSERT INTO users (username, display_name, password_hash) VALUES (?, ?, ?) RETURNING user_id;";

        try{
            int userId = jdbcTemplate.queryForObject(sql, int.class, user.getUsername(), user.getDisplayName(), user.getPassword_hash())
        }
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public int deleteUserById(int userId) {
        return 0;
    }
}
