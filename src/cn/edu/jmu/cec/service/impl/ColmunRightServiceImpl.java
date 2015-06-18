package cn.edu.jmu.cec.service.impl;

import cn.edu.jmu.cec.dao.ColmunRightDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;
import cn.edu.jmu.cec.service.ColumnRightSevice;

import java.util.List;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public class ColmunRightServiceImpl implements ColumnRightSevice {
    private ColmunRightDAO columnRightDAO;

    public ColmunRightDAO getColumnRightDAO() {
        return columnRightDAO;
    }

    public void setColumnRightDAO(ColmunRightDAO columnRightDAO) {
        this.columnRightDAO = columnRightDAO;
    }

    public void add(Users user, ArtColumn column) {
        ColmunRight colmunRight = findByUserIdAndColumnId(user.getUserId(), column.getColumnId());
        if (colmunRight == null) {
            colmunRight = new ColmunRight();
            colmunRight.setUsers(user);
            colmunRight.setArtColumn(column);
            columnRightDAO.saveOrUpdate(colmunRight);
        }

    }

    public ColmunRight findByUserIdAndColumnId(int userId, int ColumnId) {
        String hql = "from ColmunRight r where r.artColumn.columnId=" + ColumnId + " and r.users.userId=" + userId;
        return (ColmunRight) columnRightDAO.findUnique(hql, null);
    }

    public void delete(Users user, ArtColumn column) {
        ColmunRight colmunRight = findByUserIdAndColumnId(user.getUserId(), column.getColumnId());
        if (colmunRight != null) {
            columnRightDAO.delete(colmunRight);
        }
    }

    public List<ColmunRight> findByColumn(ArtColumn column) {
        return columnRightDAO.findByColumn(column);
    }

    public List<ColmunRight> findByUsers(Users user) {
        return columnRightDAO.findByUser(user);
    }
}