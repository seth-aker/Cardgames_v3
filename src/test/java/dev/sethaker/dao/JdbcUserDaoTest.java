package dev.sethaker.dao;

import dev.sethaker.resources.db.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JdbcUserDaoTest extends BaseDaoTest{
    private final User USER_1 = new User(1, "USER1", "ONE","PASSWORD");
    private final User USER_2 = new User(2, "USER2", "TWO", "PASSWORD");

    private JdbcUserDao sut;
    private JdbcUserDao invalidConnectionDao;

    @Before
    public void setUp(){
        sut = new JdbcUserDao(dataSource);
        invalidConnectionDao = new JdbcUserDao(invalidDataSource);
    }

    @Test
    public void getUserById_returns_correct_user() {
        User user = sut.getUserById(1);

        assertNotNull("getUserById unexpectedly returned null", user);
        assertUsersMatch("getUserById returned incorrect or partialy incorrect results", USER_1, user);
    }

    @Test
    public void getUserById_returns_null_for_unknown_id(){
        User user = sut.getUserById(0);
        assertNull("User id '0' does not exist, object should return null", user);
    }

    @Test
    public void getUserById_throws_exception_for_

    @Test
    public void createUser_has_expected_values_when_retrieved() {
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setPasswordHash("password");
        newUser.setDisplayName("000");

        User createdUser = sut.createUser(newUser);
        Assert.assertNotNull("createUser should return the newly created user", createdUser);
        Assert.assertTrue("createUser did not return an object with ID set", createdUser.getUserId() > 0);
        Assert.assertEquals("createUser did not return a user with the correct username", newUser.getUsername(), createdUser.getUsername());
        Assert.assertEquals("createUser did not return a user with the correct passwordHash", newUser.getPasswordHash(), createdUser.getPasswordHash());
        Assert.assertEquals("createUser did not return a user with the correct display name", newUser.getDisplayName(), createdUser.getDisplayName());
    }

    @Test
    public void updateUser_returns_user_with_updated_info() {
        User user = USER_1;
        user.setUsername("UPDATE");
        user.setDisplayName("UPD");
        user.setPasswordHash("NEWPASSWORD");

        User updatedUser = sut.updateUser(user);

        assertNotNull("updateEmployee unexpectedly returned null", updatedUser);
        assertUsersMatch("updateUser did not correctly assign updated information to existing user", user, updatedUser);
    }

    private void assertUsersMatch(String message, User expected, User actual){
        assertEquals(message, expected.getUserId(), actual.getUserId());
        assertEquals(message, expected.getUsername(), actual.getUsername());
        assertEquals(message, expected.getDisplayName(), actual.getDisplayName());
        assertEquals(message, expected.getPasswordHash(), actual.getPasswordHash());
    }
}