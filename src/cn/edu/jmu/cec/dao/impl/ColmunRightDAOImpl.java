package cn.edu.jmu.cec.dao.impl;

import cn.edu.jmu.cec.dao.BaseDAO;
import cn.edu.jmu.cec.dao.ColmunRightDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public class ColmunRightDAOImpl extends BaseDAOImpl<ColmunRight> implements ColmunRightDAO {
    public List<ColmunRight> findByColumn(ArtColumn column) {
        return  findByProperty(ColmunRight.class, "artColumn", column);
    }

    
    public List<ColmunRight> findByUser(Users user) {
        return  findByProperty(ColmunRight.class, "users", user);
    }
}
