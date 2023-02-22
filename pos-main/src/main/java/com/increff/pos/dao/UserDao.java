package com.increff.pos.dao;

import com.increff.pos.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao extends AbstractDao {

    @Transactional
    public void insertUser(UserPojo p) {
        em().persist(p);
    }

    public int deleteUser(int id) {
        return delete(UserPojo.class, id, "id");
    }

    public UserPojo selectUserByEmail(String email) {
        return selectSingle(UserPojo.class, email, "email");
    }

    public List<UserPojo> selectAllUsers() {
        return selectAll(UserPojo.class);
    }
}
