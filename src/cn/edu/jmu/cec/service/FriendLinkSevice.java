package cn.edu.jmu.cec.service;

import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.FriendLink;

import java.util.List;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public interface FriendLinkSevice {
    public void save(FriendLink friendLink);

    public List<FriendLink> getAll();

    public List<FriendLink> getAllIndex();

    public void delete(int id);

    public FriendLink getById(int id);

    public void update(FriendLink friendLink);
}