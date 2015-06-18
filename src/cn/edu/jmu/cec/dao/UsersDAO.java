package cn.edu.jmu.cec.dao;

import cn.edu.jmu.cec.domain.Users;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface UsersDAO extends BaseDAO<Users> {
    public List<Users> findAllWithAdmin();

    public Users checkUser(String userName, String pwd);
}
