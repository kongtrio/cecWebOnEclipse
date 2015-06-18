package cn.edu.jmu.cec.dao;

import java.util.List;

import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface ArtColumnDAO extends BaseDAO<ArtColumn> {

    public ArtColumn findById(int columnId);

    public List<ArtColumn> findColumn();

    public List<ArtColumn> findByName(String parentName);

    public List<ArtColumn> findOsParent(boolean isIndex);

    public List<ArtColumn> findIndexColumn(boolean isIndex);

}
