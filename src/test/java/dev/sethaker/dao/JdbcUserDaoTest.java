package dev.sethaker.dao;

import dev.sethaker.resources.dbmodel.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUserDaoTest extends BaseDaoTest{
    private static final User USER_1 = new User(1, "USER1", "password");
    private static final User USER_2 = new User(2, "USER2", "password");

    private JdbcUserDao sut;
    private JdbcUserDao invalidConnectionDao;

    @Before
    public void setUp(){
        sut = new JdbcUserDao(dataSource);
        invalidConnectionDao = new JdbcUserDao(invalidDataSource);
    }

    @Test
    public void getUserById() {

    }

    @Test
    public void createUser_has_expected_values_when_retrieved() {
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setPasswordHash("password");

        User createdUser = sut.createUser(newUser);
        Assert.assertNotNull("createUser should return the newly created user", createdUser);
        Assert.assertTrue("createUser did not return an object with ID set", createdUser.getUserId() > 0);
        Assert.assertEquals("createUser did not return a user with the correct username", newUser.getUsername(), createdUser.getUsername());
        Assert.assertEquals("createUser did not return a user with the correct passwordHash", newUser.getPasswordHash(), createdUser.getPasswordHash());
        Assert.assertEquals("createUser did not return a user with the correct display name", newUser.getDisplayName(), createdUser.getDisplayName());
    }

    @Test
    public void updateUser() {
    }
}