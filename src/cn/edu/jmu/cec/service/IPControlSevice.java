package cn.edu.jmu.cec.service;

import cn.edu.jmu.cec.domain.FriendLink;
import cn.edu.jmu.cec.domain.IPControl;

import java.util.List;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public interface IPControlSevice {
    public void save(IPControl iPControl);

    public List<IPControl> getAll();

    public void delete(int id);

    public IPControl getById(int id);

    public void update(IPControl iPControl);

    public List<IPControl> getByType(int typeId);
}