package cn.edu.jmu.cec.dao;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface ArticleDAO extends BaseDAO<Article> {
    public List<Article> findNotifyArt(boolean isTop);

    public List<Article> findColTopArt(int colId,boolean isTop);

    public List<Article> findIndexArticle();

    public List<Article> findTopArticle();

    public List<Article> findArticleWithImage();

    public Page<Article> findIndexArticleByPage(int page,int pageSize);

    public Page<Article> findByKeyAndPage(int page,String key, String type);

    public Page<Article> findByTypeAndKey(String type, String key, int page, ArtColumn artColumn, int pageSize);

    public Page<Article> findArticleByPage(int page, ArtColumn artColumn, int pageSize);

    public Page<Article> getTopDataByPage(boolean isSelectTop, String type, int page, int pageSize, ArtColumn artColumn);
}
