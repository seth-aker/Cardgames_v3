package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.User;

public interface UserDao {
    User getUserById(int id);
    User createUser(User user);
    User updateUser(User user);
}
