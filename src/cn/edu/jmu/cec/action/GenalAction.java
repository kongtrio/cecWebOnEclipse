package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.FriendLink;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yangjb on 2015/4/9.
 */
public class GenalAction extends BaseAction<Article> implements
        ServletContextAware {
    private ArtColumn artColumn;

    //跳转到首页
    public String execute() {
        //搜索为第一级栏目的栏目列表,在导航栏显示(是第一级栏目并且isNav的)
        List<ArtColumn> navColumn = artColumnSevice.findIsParent(true);
        navColumn = subList(navColumn, 5);

        //首页第一个栏目放通知公告,取出所有首页置顶的新闻(取10条)
        List<Article> indexArticle = articleService.getIndexArticleByPage(1,8);
//        indexArticle = subList(indexArticle, 8);
//        formatArticle(indexArticle);
        markRecentNew(indexArticle);

        //搜索4个排在前面的栏目，展示在首页
        List<ArtColumn> indexColumn = artColumnSevice.findIndexColumn(true);
        List<ArtColumn> artColumns = formatNavNew(indexColumn);

        //搜索头条新闻(会议通知)
        Article topArticle = articleService.getTopArticle();
        ArrayList<Article> articles = new ArrayList<Article>();
        if(topArticle!=null){
            articles.add(topArticle);
        }
        markRecentNew(articles);

        //搜索5个图片新闻
        List<Article> articleWithImage = articleService.getArticleWithImage();
        articleWithImage = subList(articleWithImage, 5);

        //搜索5个友情链接
        List<FriendLink> all = friendLinkService.getAllIndex();
        List<FriendLink> friendLinks = subList(all, 5);

        request.setAttribute("navColumn", navColumn);
        request.setAttribute("indexArticle", indexArticle);
        request.setAttribute("indexColumn", artColumns);
        request.setAttribute("topArticle", topArticle);
        request.setAttribute("articleWithImage", articleWithImage);
        request.setAttribute("friendLinks", friendLinks);

        return "indexPage";
    }

    public ArtColumn getArtColumn() {
        return artColumn;
    }

    public void setArtColumn(ArtColumn artColumn) {
        this.artColumn = artColumn;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
