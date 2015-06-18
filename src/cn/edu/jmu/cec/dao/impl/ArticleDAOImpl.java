package cn.edu.jmu.cec.dao.impl;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.dao.ArticleDAO;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yangjb on 2015/1/25.
 */
public class ArticleDAOImpl extends BaseDAOImpl<Article> implements ArticleDAO {

    //重写删除方法
    public void deleteMany(int[] ids) {
        Article article = null;
        for (int i = 0; i < ids.length; i++) {
            article = findById(Article.class, ids[i]);
            article.setStatus((short) 0);
            merge(article);
        }
    }

    public void delete(Article article) {
        article.setStatus((short) 0);
        merge(article);
    }

    
    public List<Article> findNotifyArt(boolean isTop) {
        return hibernateTemplate.find("from Article art where status=1 and artColumn.columnId=58 and isColmunTop=" + isTop + " order by publicTime desc ");
    }

    
    public List<Article> findColTopArt(int colId, boolean isTop) {
        return hibernateTemplate.find("from Article art where status=1 and artColumn.columnId=" + colId + " and isColmunTop=" + isTop + " order by publicTime desc ");
    }

    
    public List<Article> findIndexArticle() {
        return hibernateTemplate.find("from Article art where status=1 and isIndexTop=true order by publicTime desc ");
    }

    
    public List<Article> findTopArticle() {
        return hibernateTemplate.find("from Article art where status=1 and isTop=true order by publicTime desc ");
    }

    
    public List<Article> findArticleWithImage() {
        return hibernateTemplate.find("from Article art where status=1 and titlePic!=null and titlePic!='' order by publicTime desc ");
    }

    
    public Page<Article> findIndexArticleByPage(int page,int pageSize) {
        String hql = "from Article art where status=1 and isIndexTop=true order by publicTime desc";
        Page<Article> byPage = findByPage(hql, page, pageSize, null);
        return byPage;
    }

    
    public Page<Article> findByKeyAndPage(int page, String key, String type) {
        String hql = "from Article art where status=1 and " + type + " like ? order by publicTime desc";
        key = "%" + key + "%";
        Object[] param = {key};

        Page<Article> byPage = findByPage(hql, page, 10, param);
        return byPage;
    }

    
    public Page<Article> findByTypeAndKey(String type, String key, int page, ArtColumn artColumn, int pageSize) {
        List<Object> paramList = new ArrayList<Object>();

        String hql = "from Article where status=1 ";
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
        return findByPage(hql, page, 10, params);
    }

    
    public Page<Article> findArticleByPage(int page, ArtColumn artColumn, int pageSize) {
        List<Object> paramList = new ArrayList<Object>();

        String hql = "from Article where status=1 ";
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
        return findByPage(hql, page, 10, params);
    }

    
    public Page<Article> getTopDataByPage(boolean isSelectTop, String type, int page, int pageSize, ArtColumn artColumn) {
        String hql = "from Article where status=1 ";
        hql += "and " + type + "=" + isSelectTop;
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
        Page<Article> pages = findByPage(hql, page, pageSize, null);
        return pages;
    }

    public Page<Article> findByPage(final String hql, final Integer offset, final Integer length,
                              final Object... values) {
        try {
            return (Page<Article>) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session s)
                                throws HibernateException, SQLException {
                            if(offset<=0){
                                return null;
                            }

                            Query query = createQuery(s, hql, values);
                            query.setFirstResult(length*(offset-1)).setMaxResults(length);
                            Page p=new Page(query.list());
//                            logger.info("查找指定HQL分页数据成功," + hql);
                            return p;
                        }
                    });
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
