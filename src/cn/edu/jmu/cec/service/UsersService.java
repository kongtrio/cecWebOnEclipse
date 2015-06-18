package cn.edu.jmu.cec.service;

import cn.edu.jmu.cec.domain.Users;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface UsersService {
    public Users getById(int id);

    public List<Users> findAll();

    public void update(Users user);

    public void delete(Users user);

    public Users checkUser(String userName, String pwd);

    public void save(Users user);
}
