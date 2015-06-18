package cn.edu.jmu.cec.service.impl;

import cn.edu.jmu.cec.dao.FriendLinkDAO;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.FriendLink;
import cn.edu.jmu.cec.service.FriendLinkSevice;

import java.util.List;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public class FriendLinkServiceImpl implements FriendLinkSevice {
    private FriendLinkDAO friendLinkDAO;

    public FriendLinkDAO getFriendLinkDAO() {
        return friendLinkDAO;
    }

    public void setFriendLinkDAO(FriendLinkDAO friendLinkDAO) {
        this.friendLinkDAO = friendLinkDAO;
    }

    public void save(FriendLink friendLink) {
        friendLinkDAO.save(friendLink);
    }

    public List<FriendLink> getAll() {
        return friendLinkDAO.findAll(FriendLink.class);
    }

    public List<FriendLink> getAllIndex() {
        return friendLinkDAO.getAllIndex();
    }

    public void delete(int id) {
        FriendLink friendLink = friendLinkDAO.findById(FriendLink.class, id);
        if (friendLink != null) {
            friendLinkDAO.delete(friendLink);
        }
    }

    public FriendLink getById(int id) {
        FriendLink friendLink = friendLinkDAO.findById(FriendLink.class, id);
        return friendLink;
    }

    public void update(FriendLink friendLink) {
        friendLinkDAO.saveOrUpdate(friendLink);
    }
}