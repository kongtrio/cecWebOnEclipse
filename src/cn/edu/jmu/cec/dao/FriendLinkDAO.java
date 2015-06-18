package cn.edu.jmu.cec.dao;

import cn.edu.jmu.cec.dao.impl.BaseDAOImpl;
import cn.edu.jmu.cec.domain.FriendLink;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface FriendLinkDAO extends BaseDAO<FriendLink> {
    public List<FriendLink> getAllIndex();
}
