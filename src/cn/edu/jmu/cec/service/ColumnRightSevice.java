package cn.edu.jmu.cec.service;

import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;
import org.hsqldb.rights.User;

import java.util.List;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public interface ColumnRightSevice {
    public void add(Users user, ArtColumn column);

    public void delete(Users user, ArtColumn column);

    public List<ColmunRight> findByColumn(ArtColumn column);

    public List<ColmunRight> findByUsers(Users user);
}