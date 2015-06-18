package cn.edu.jmu.cec.dao.impl;

import java.util.List;
import java.util.Set;

import cn.edu.jmu.cec.dao.ArtColumnDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;

public class ArtColumnDAOImpl extends BaseDAOImpl<ArtColumn> implements ArtColumnDAO {

    public List<ArtColumn> findColumn() {
        return hibernateTemplate.find("from ArtColumn artColumn where status=1");
    }

    public void delete(ArtColumn artColumn) {
        artColumn.setStatus((short) 0);
        merge(artColumn);
    }

    public ArtColumn findById(int columnId) {
        List<ArtColumn> artColumns = hibernateTemplate.find("from ArtColumn col where status=1 and col.columnId=?", columnId);
        if (artColumns != null && artColumns.size() > 0) {
            return artColumns.get(0);
        }
        return null;
    }

    public List<ArtColumn> findByName(String parentName) {
        return hibernateTemplate.find("from ArtColumn col where status=1 and col.colName=?", parentName);
    }

    public List<ArtColumn> findOsParent(boolean isNav) {
        return hibernateTemplate.find("from ArtColumn col where status=1 and artColumn=null and isNav=" + isNav + " order by colLevel desc ");
    }

    
    public List<ArtColumn> findIndexColumn(boolean isIndex) {
        return hibernateTemplate.find("from ArtColumn col where status=1 and isIndex=" + isIndex + " order by colLevel desc ");
    }


}
