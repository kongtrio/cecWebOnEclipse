package cn.edu.jmu.cec.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.dao.ArticleDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.service.ArticleService;
import org.apache.log4j.Logger;

/**
 * Created by yangjb on 2015/1/25.
 */
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDAO;
    private Logger logger = Logger.getLogger(this.getClass());

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public Article getById(Integer id) {
        // TODO Auto-generated method stub
        if (id == null) {
            return null;
        }
        String hql = "from Article where status=1 and artId=?";
        Object param[] = {id};
        return (Article) articleDAO.findUnique(hql, param);
    }

    public void save(Article article) {
        if (article.getIsAddress() && article.getOutAddress() != null) {
            article.setContent("");
            String outAddress = article.getOutAddress();
            if(!outAddress.contains("http")){
                outAddress = "http://" + outAddress;
                article.setOutAddress(outAddress);
            }
        } else {
            article.setOutAddress("");
        }

        if(article.getContent().contains("<table ")){
            String content = article.getContent();
            content.replace("<table ","<table border=\"1\"");
            article.setContent(content);
        }

        article.setPublicTime(new Timestamp(new Date().getTime()));
        article.setReadCount(0);
        articleDAO.save(article);
        logger.info("save article success;article name=" + article.getTitle());
    }

    public Page<Article> getAllByPage(int page, ArtColumn artColumn, int pageSize) {
        return articleDAO.findArticleByPage(page, artColumn, pageSize);
    }

    public Page<Article> getDataByPage(String type, String key, int page, ArtColumn artColumn, int pageSize) throws NumberFormatException {
        return articleDAO.findByTypeAndKey(type, key, page, artColumn, pageSize);
    }

    public int getTitlePicCount(int columnId) {
        String hql = "from Article art where status=1 art.artColumn.columnId=? and art.titlePic is not NULL  order by publicTime desc";
        List<Object> articles = articleDAO.find(hql, columnId);
        if (articles != null && articles.size() > 0) return articles.size();
        else return 0;
    }

    public void deleteByIds(int[] ids) {
        articleDAO.deleteMany(ids);
    }


    public Page<Article> getTopDataByPage(boolean isSelectTop, String type, int page, int pageSize, ArtColumn artColumn) {
        return articleDAO.getTopDataByPage(isSelectTop,type,page,pageSize,artColumn);
    }

    public Page<Article> getArticlByCidWithPage(String colId, int page, int pageSize) {
        String hql = "from Article where status=1 ";
        hql += " and artColumn.columnId=? order by publicTime desc";
        int columnId = Integer.parseInt(colId);
        Object param[] = {columnId};
        Page<Article> pages = articleDAO.findByPage(hql, page, pageSize, param);
        return pages;
    }

    public List<Article> getIndexArticle() {
        return articleDAO.findIndexArticle();
    }

    public Page<Article> getIndexArticleByPage(int page, int pageSize) {
        return articleDAO.findIndexArticleByPage(page, pageSize);
    }

    public Article getTopArticle() {
        List<Article> topArticles = articleDAO.findTopArticle();
        Article article = null;
        if (topArticles != null && !topArticles.isEmpty()) {
            article = topArticles.get(0);
        }
        return article;
    }

    public List<Article> getArticleWithImage() {
        return articleDAO.findArticleWithImage();
    }

    public Page<Article> getByKeyWithPage(int page, String key, String type, int pageSize) {
        return articleDAO.findByKeyAndPage(page, key, type);
    }


    public int getTotalCountByKey(String key, String type){
        String hql = "select count(*) from Article where status=1 and " + type + " like ?";
        key = "%" + key + "%";
        Object[] param = {key};
        return articleDAO.getTotalCount(hql, param);
    }

    public int getTotalCountByKey(String key, String type, ArtColumn artColumn) {
        List<Object> paramList = new ArrayList<Object>();

        String hql = "select count(*) from Article where status=1 ";
        if (artColumn != null) {
            if (artColumn.getArtColumns().size() > 0) {
                String ids = "";
                for (ArtColumn col : (Set<ArtColumn>) artColumn.getArtColumns()) {
                    ids = col.getColumnId() + "," + ids;
                }
                ids = ids.substring(0, ids.length() - 1);
                hql += " and (artColumn.columnId=? or artColumn.columnId in ("+ids+"))";
            } else {
                hql += " and artColumn.columnId=?";
            }
            paramList.add(artColumn.getColumnId());
        }

        if (type.equals("artId")) {
            hql += " and artId=?";
            paramList.add(Integer.parseInt(key));
        } else {
            hql += " and " + type + " like ?";
            key = "%" + key + "%";
            paramList.add(key);
        }

        hql += " order by publicTime desc";
        Object[] params = new Object[paramList.size()];
        for (int i = 0; i < paramList.size(); i++) {
            params[i] = paramList.get(i);
        }

        return articleDAO.getTotalCount(hql,params);
    }

    public int getTotalCountByTop(boolean isTop, ArtColumn artColumn, String type) {
        String hql = "select count(*) from Article where status=1 ";
        hql += "and " + type + "=" + isTop;
        if (artColumn != null) {
            if (artColumn.getArtColumns().size() > 0) {
                String ids = "";
                for (ArtColumn col : (Set<ArtColumn>) artColumn.getArtColumns()) {
                    ids = col.getColumnId() + "," + ids;
                }
                ids = ids.substring(0, ids.length() - 1);
                hql += " and (artColumn.columnId=" + artColumn.getColumnId()+" or artColumn.columnId in ("+ids+"))";
            } else {
                hql += " and artColumn.columnId=" + artColumn.getColumnId();
            }
        }
        hql += " order by publicTime desc";

        return articleDAO.getTotalCount(hql,null);
    }

    public int getTotalCountByColId(int colId) {
        String hql = "select count(*) from Article where status=1 ";
        hql += " and artColumn.columnId=?";
        Object param[] = {colId};
        int totalCount = articleDAO.getTotalCount(hql, param);
        return totalCount;
    }

    public int getTotalCountByCol(ArtColumn artColumn) {
        List<Object> paramList = new ArrayList<Object>();

        String hql = "select count(*) from Article where status=1 ";
        if (artColumn != null) {
            if (artColumn.getArtColumns().size() > 0) {
                String ids = "";
                for (ArtColumn col : (Set<ArtColumn>) artColumn.getArtColumns()) {
                    ids = col.getColumnId() + "," + ids;
                }
                ids = ids.substring(0, ids.length() - 1);
                hql += " and (artColumn.columnId=? or artColumn.columnId in ("+ids+"))";
            } else {
                hql += " and artColumn.columnId=?";
            }
            paramList.add(artColumn.getColumnId());
        }

        hql += " order by publicTime desc";

        Object[] params = new Object[paramList.size()];
        for (int i = 0; i < paramList.size(); i++) {
            params[i] = paramList.get(i);
        }
        return articleDAO.getTotalCount(hql,params);
    }

    public int getTotalCountByIndex() {
        String hql = "select count(*) from Article where status=1 and is_index_top=?";
        Object param[] = {true};
        return articleDAO.getTotalCount(hql,param);
    }

    public void changeTops(int[] ids, String type, boolean topChange) {
        for (int i = 0; i < ids.length; i++) {
            logger.info("删除" + ids[i]);
            changeTop(ids[i], type, topChange);
        }
    }

    public List<Article> findArticleByCid(Integer columnId) {
        List<Article> colTopArt = articleDAO.findColTopArt(columnId, true);
        List<Article> colTopArt1 = articleDAO.findColTopArt(columnId, false);
        colTopArt.addAll(colTopArt1);
        return colTopArt;
    }

    //返回学院公告的所有新闻，学院公告的colId=58
    public List<Article> findNotifyArt() {
        List<Article> notifyTop = articleDAO.findNotifyArt(true);
        if (notifyTop != null && notifyTop.size() < 6) {
            List<Article> notify = articleDAO.findNotifyArt(false);
            notifyTop.addAll(notify);
        }
        return notifyTop;
    }

    private void changeTop(int id, String type, boolean topChange) {
        Article art = getById(id);
        type = type.trim();
        if ("isTop".equals(type)) {
            art.setIsTop(topChange);
        } else if ("isColmunTop".equals(type)) {
            art.setIsColmunTop(topChange);
        } else if ("isIndexTop".equals(type)) {
            art.setIsIndexTop(topChange);
        }
        update(art);
    }

    public List<Integer> getChildrenIdList(ArtColumn artColumn) {
        List<Integer> ids = new ArrayList<Integer>();
        if (artColumn == null) {
            return null;
        }
        for (ArtColumn col : (Set<ArtColumn>) artColumn.getArtColumns()) {
            ids.add(col.getColumnId());
        }
        return ids;
    }

    public void update(Article article) {
        if(article.getContent().contains("<table ")){
            String content = article.getContent();
            content.replace("<table ","<table border=\"1\"");
            article.setContent(content);
        }

        articleDAO.merge(article);
    }

//    public List<Article> findArticleByCid(Integer columnId) {
//        String hql="from Article a where a.artColumn.columnId=? and status=1";
//        return articleDAO.findArticle(hql,columnId);
//    }
}
