package cn.edu.jmu.cec.service.impl;

import cn.edu.jmu.cec.dao.IPControlDAO;
import cn.edu.jmu.cec.domain.FriendLink;
import cn.edu.jmu.cec.domain.IPControl;
import cn.edu.jmu.cec.service.IPControlSevice;

import java.util.List;

/**
 * Created by Administrator on 2015/6/2.
 */
public class IPControlSeviceImpl implements IPControlSevice {
    private IPControlDAO ipControlDAO;

    public IPControlDAO getIpControlDAO() {
        return ipControlDAO;
    }

    public void setIpControlDAO(IPControlDAO ipControlDAO) {
        this.ipControlDAO = ipControlDAO;
    }

    public void save(IPControl ipControl) {
        ipControlDAO.save(ipControl);
    }

    public List<IPControl> getAll() {
        return ipControlDAO.findAll(IPControl.class);
    }

    public void delete(int id) {
        IPControl byId = getById(id);
        if(byId!=null){
            ipControlDAO.delete(byId);
        }
    }

    public IPControl getById(int id) {
        return ipControlDAO.findById(IPControl.class,id);
    }

    public void update(IPControl iPControl) {
        ipControlDAO.merge(iPControl);
    }

    public List<IPControl> getByType(int typeId) {
        return ipControlDAO.findByProperty(IPControl.class,"type",typeId);
    }
}
