package cn.edu.jmu.cec.service.impl;

import cn.edu.jmu.cec.dao.ArtColumnDAO;
import cn.edu.jmu.cec.dao.ArticleDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.service.ArtColumnSevice;

import java.util.List;
import java.util.Set;

/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public class ArtColumnServiceImpl implements ArtColumnSevice {
    private ArtColumnDAO artColumnDAO;
    private ArticleDAO articleDAO;

    public ArtColumnDAO getArtColumnDAO() {
        return artColumnDAO;
    }

    public void setArtColumnDAO(ArtColumnDAO artColumnDAO) {
        this.artColumnDAO = artColumnDAO;
    }

    public ArticleDAO getArticleDAO() {

        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<ArtColumn> findAll() {
        return artColumnDAO.findColumn();
    }

    public void delete(int columnId) {
        ArtColumn artColumn = artColumnDAO.findById(columnId);
        if (artColumn.getStatus() == 0) {
            return;
        }
        //鍒犻櫎瀛愭爮鐩拰鎵�湁鐨勬枃绔�
        Set<ArtColumn> artColumns = artColumn.getArtColumns();
        if (artColumns != null) {
            for (ArtColumn child : artColumns) {
                artColumnDAO.delete(child);
            }
        }
        Set<Article> articles = artColumn.getArticles();
        if (articles != null) {
            for (Article child : articles) {
                articleDAO.delete(child);
            }
        }
        artColumnDAO.delete(artColumn);
    }

    public void deleteAll(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            delete(ids[i]);
        }
    }

    public ArtColumn getById(int colId) {
        return artColumnDAO.findById(colId);
    }


    public ArtColumn findByName(String parentName) {
        List<ArtColumn> artColumns = artColumnDAO.findByName(parentName);
        if (artColumns != null && artColumns.size() > 0) {
            return artColumns.get(0);
        }
        return null;
    }

    public void modify(ArtColumn artColumn) {
        artColumnDAO.merge(artColumn);

    }

    //閫夋嫨鏄惁鏈夊湪瀵艰埅鏍忔樉绀虹殑绗竴绾у垪琛�
    public List<ArtColumn> findIsParent(boolean isNav) {
        return artColumnDAO.findOsParent(isNav);
    }

    //閫夋嫨鍏ㄩ儴绗竴绾у垪琛�
    public List<ArtColumn> findIsParentWithAll() {
        List<ArtColumn> all = findIsParent(true);
        all.addAll(findIsParent(false));
        return all;
    }

    public void add(ArtColumn artColumn) {

        artColumnDAO.save(artColumn);
    }

    public List<ArtColumn> findIndexColumn(boolean isIndex) {
        return artColumnDAO.findIndexColumn(isIndex);
    }


}