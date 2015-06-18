package cn.edu.jmu.cec.service.impl;

import cn.edu.jmu.cec.dao.UsersDAO;
import cn.edu.jmu.cec.domain.Users;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public class UsersServiceImpl implements cn.edu.jmu.cec.service.UsersService {
    private UsersDAO usersDAO;

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public Users getById(int id) {
        return usersDAO.findById(Users.class, id);
    }

    public List<Users> findAll() {
        return usersDAO.findAllWithAdmin();
    }

    public void update(Users user) {
        usersDAO.merge(user);
    }

    public void delete(Users user) {
        usersDAO.delete(user);
    }

    public Users checkUser(String userName, String pwd) {
        return usersDAO.checkUser(userName, pwd);
    }

    public void save(Users user) {
        usersDAO.save(user);
    }
}
