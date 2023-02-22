package com.increff.pos.api;

import com.increff.pos.config.AbstractUnitTest;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.UserPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.pos.util.DummyPojoUtil.GetUserPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserApiTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Autowired
    private UserApi userApi;

    @Test
    public void testAddUser() throws ApiException {
        UserPojo userPojo = GetUserPojo();
        userApi.addUser(userPojo);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("User with email already exists, login instead");
        userApi.addUser(userPojo);
    }

    @Test
    public void testDeleteUser() throws ApiException {
        userApi.deleteUser(1);
    }

    @Test
    public void testGetUser() throws ApiException {
        UserPojo userPojo = GetUserPojo();
        userApi.addUser(userPojo);

        String email = userApi.getAllUsers().get(0).getEmail();
        UserPojo userPojoAdded = userApi.getUser(email);

        assertEquals(userPojo.getEmail(), userPojoAdded.getEmail());
        assertEquals(userPojo.getPassword(), userPojoAdded.getPassword());
        assertEquals(userPojo.getRole(), userPojoAdded.getRole());
    }

    @Test
    public void testGetCheck() throws ApiException {
        userApi.getCheckUserByEmail("p");
    }
}