package cn.edu.jmu.cec.service;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;

import java.util.List;

/**
 * Created by yangjb on 2015/1/25.
 */
public interface ArticleService {
    public Article getById(Integer id);

    public void save(Article article);

    public Page<Article> getAllByPage(int page, ArtColumn artColumn,int pageSize);

    public Page<Article> getDataByPage(String type, String key, int page, ArtColumn artColumn,int pageSize);

    public int getTitlePicCount(int columnId);

    public void deleteByIds(int[] ids);

    public void update(Article article);

    public Page<Article> getTopDataByPage(boolean isSelectTop, String type, int page,int pageSize, ArtColumn artColumn);

    public void changeTops(int[] ids, String type, boolean topChange);

    public List<Article> findArticleByCid(Integer columnId);

    public List<Article> findNotifyArt();

    public Page<Article> getArticlByCidWithPage(String colId, int page,int pageSize);

    public List<Article> getIndexArticle();

    public Page<Article> getIndexArticleByPage(int page,int pageSize);

    public Article getTopArticle();

    public List<Article> getArticleWithImage();

    public Page<Article> getByKeyWithPage(int page,String key,String type,int pageSize);

    public int getTotalCountByColId(int colId);

    public int getTotalCountByCol(ArtColumn col);

    public int getTotalCountByIndex();

    public int getTotalCountByKey(String key, String type);

    public int getTotalCountByKey(String key, String type,ArtColumn column);

    public int getTotalCountByTop(boolean isTop,ArtColumn artColumn,String type);
}
