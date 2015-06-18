package cn.edu.jmu.cec.dao.impl;

import cn.edu.jmu.cec.dao.BaseDAO;
import cn.edu.jmu.cec.dao.FriendLinkDAO;
import cn.edu.jmu.cec.domain.FriendLink;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public class FriendLinkDAOImpl extends BaseDAOImpl<FriendLink> implements FriendLinkDAO{
    
    public List<FriendLink> getAllIndex() {
        List<FriendLink> isIndex = super.findByProperty(FriendLink.class, "isIndex", true);
        return isIndex;
    }
}
