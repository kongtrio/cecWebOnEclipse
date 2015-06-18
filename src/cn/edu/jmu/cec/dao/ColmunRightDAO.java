package cn.edu.jmu.cec.dao;

import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface ColmunRightDAO extends BaseDAO<ColmunRight> {
    public List<ColmunRight> findByColumn(ArtColumn column) ;

    public List<ColmunRight> findByUser(Users user);
}
