package dev.sethaker.dao;

import dev.sethaker.exceptions.DaoException;
import dev.sethaker.resources.db.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcUserDao implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT user_id, username, display_name, password_hash FROM users WHERE user_id = ?; ";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                user = mapRowSetToUser(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        User newUser = null;
        String sql = "INSERT INTO users (username, display_name, password_hash) VALUES (?, ?, ?) RETURNING user_id;";

        try{
            int userId = jdbcTemplate.queryForObject(sql, int.class, user.getUsername(), user.getDisplayName(), user.getPasswordHash());
            newUser = getUserById(userId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data Integrity Violation", e);
        }
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = null;
        String sql = "UPDATE users SET username = ?, display_name = ?, password_hash = ? WHERE user_id = ?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, user.getUsername(), user.getDisplayName(), user.getPasswordHash(), user.getUserId());
            if(rowsAffected == 0){
                throw new DaoException("Error, user \"" + user.getUsername() + "\" does not exist");
            } else {
                updatedUser = getUserById(user.getUserId());
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedUser;
    }

    private User mapRowSetToUser(SqlRowSet rowSet){
        User user = new User();
        user.setUserId(rowSet.getInt("user_id"));
        user.setUsername(rowSet.getString("username"));
        user.setPasswordHash(rowSet.getString("password_hash"));
        user.setDisplayName(rowSet.getString("display_name"));
        return user;
    }
}
