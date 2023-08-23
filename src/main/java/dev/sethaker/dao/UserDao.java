package dev.sethaker.dao;

import dev.sethaker.resources.db.model.User;

public interface UserDao {
    User getUserById(int id);
    User createUser(User user);
    User updateUser(User user);
}
