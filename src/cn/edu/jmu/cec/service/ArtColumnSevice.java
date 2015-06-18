package cn.edu.jmu.cec.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.ColmunRight;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public interface ArtColumnSevice {
    public List<ArtColumn> findAll();

    public void delete(int columnId);

    public void deleteAll(int ids[]);

    public ArtColumn getById(int colId);

    public ArtColumn findByName(String parentName);

    public void modify(ArtColumn artColumn);

    public List<ArtColumn> findIsParent(boolean isIndex);

    public List<ArtColumn> findIsParentWithAll();

    public void add(ArtColumn artColumn);

    public List<ArtColumn> findIndexColumn(boolean isIndex);
}