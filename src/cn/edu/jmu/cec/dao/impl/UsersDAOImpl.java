package cn.edu.jmu.cec.dao.impl;

import cn.edu.jmu.cec.domain.Users;

import java.util.List;

public class UsersDAOImpl extends BaseDAOImpl<Users> implements cn.edu.jmu.cec.dao.UsersDAO {

    
    public List<Users> findAllWithAdmin() {
        return hibernateTemplate.find("from Users where userId!=1", null);
    }

    
    public Users checkUser(String userName, String pwd) {
        String param[] = {userName, pwd};
        return (Users) findUnique("from Users where userName=? and password=?", param);
    }
}
