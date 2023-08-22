package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.User;

public interface UserDao {
    User createUser(User user);
    User updateUser(User user);
    int deleteUserById(int userId);
}
