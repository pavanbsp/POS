package com.increff.pos.helper;

import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;

public class UserHelper {
    public static UserPojo convertUserFormtoUserPojo(UserForm userForm) {
        UserPojo userPojo = new UserPojo();
        userPojo.setPassword(userForm.getPassword());
        userPojo.setEmail(userForm.getEmail());
        userPojo.setRole(userForm.getRole());
        return userPojo;
    }

    public static UserData convertUserPojotoUserData(UserPojo userPojo) {
        UserData UserData = new UserData();
        UserData.setEmail(userPojo.getEmail());
        UserData.setRole(userPojo.getRole());
        UserData.setId(userPojo.getId());
        return UserData;
    }
}
