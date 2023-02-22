package com.increff.pos.dto;

import com.increff.pos.api.UserApi;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.helper.UserHelper.convertUserFormtoUserPojo;
import static com.increff.pos.helper.UserHelper.convertUserPojotoUserData;
import static com.increff.pos.util.NormalizeFormUtil.normalizeUserForm;
import static com.increff.pos.util.ValidateFormUtil.validateUserForm;

@Service
public class UserDto {

    @Autowired
    private UserApi userApi;

    public void addUser(UserForm userForm) throws ApiException {
        validateUserForm(userForm);
        normalizeUserForm(userForm);
        userApi.addUser(convertUserFormtoUserPojo(userForm));
    }

    public void deleteUser(int id) throws ApiException {
        userApi.deleteUser(id);
    }

    public UserPojo getUser(String email) throws ApiException {
        return userApi.getUser(email);
    }

    public List<UserData> getAllUsers() throws ApiException {
        List<UserPojo> userPojoList = userApi.getAllUsers();
        List<UserData> userDataList = new ArrayList<>();
        for (UserPojo userPojo : userPojoList) {
            userDataList.add(convertUserPojotoUserData(userPojo));
        }
        return userDataList;
    }

}
