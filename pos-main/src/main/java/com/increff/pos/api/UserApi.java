package com.increff.pos.api;

import com.increff.pos.dao.UserDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class UserApi {
    @Autowired
    private UserDao userDao;

    public void addUser(UserPojo userPojo) throws ApiException {
        if (getCheckUserByEmail(userPojo.getEmail()) != null) {
            throw new ApiException("User with email already exists, login instead");
        }
        userDao.insertUser(userPojo);
    }

    public void deleteUser(int id) throws ApiException {
        userDao.deleteUser(id);
    }

    public UserPojo getUser(String email) throws ApiException {
        UserPojo userPojo = userDao.selectUserByEmail(email);
        return userPojo;
    }

    public List<UserPojo> getAllUsers() throws ApiException {
        return userDao.selectAllUsers();
    }

    public UserPojo getCheckUserByEmail(String email) throws ApiException {
        UserPojo p = userDao.selectUserByEmail(email);
        return p;
    }
}
