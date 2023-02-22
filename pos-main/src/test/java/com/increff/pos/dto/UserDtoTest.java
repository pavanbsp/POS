package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.config.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.DummyFormUtil.GetUserForm;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserDtoTest extends AbstractUnitTest {
    @Autowired
    private UserDto userDto;

    @Test
    public void addUserTest() throws ApiException{
        UserForm userForm = GetUserForm();
        userDto.addUser(userForm);

        UserData userData = userDto.getAllUsers().get(0);
        assertEquals(userForm.getEmail(), userData.getEmail());
        assertEquals((userForm.getRole()), userData.getRole());
    }

    @Test
    public void deleteUserTest() throws ApiException{
        userDto.deleteUser(1);
    }

    @Test
    public void getUserTest() throws ApiException{
        UserForm userForm = GetUserForm();
        userDto.addUser(userForm);

        String email = userDto.getAllUsers().get(0).getEmail();
        UserPojo userPojo = userDto.getUser(email);

        assertEquals(userForm.getEmail(), userPojo.getEmail());
        assertEquals(userForm.getPassword(), userPojo.getPassword());
        assertEquals((userForm.getRole()), userPojo.getRole());
    }

    @Test
    public void getAllUserTest() throws ApiException{
        UserForm userForm = GetUserForm();
        userDto.addUser(userForm);

        userForm.setRole("operator");
        userForm.setEmail("kohli@gmail.com");
        userForm.setPassword("rcb2023");
        userDto.addUser(userForm);

        List<UserData> userDataList = userDto.getAllUsers();
        assertEquals(2, userDataList.size());
    }
}