package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.json.ArticleJson;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/26.
 */
public class IndexArticleAction extends BaseAction<ArtColumn> implements
        ServletContextAware {
    private int page;

    //获取首页置顶的新闻(通知公告的新闻,index=true)
    public String execute() {
        Page<Article> indexArticleByPage = articleService.getIndexArticleByPage(1, 13);

        int totalCountByColId = articleService.getTotalCountByIndex();
        indexArticleByPage.setTotalCount(totalCountByColId);
        indexArticleByPage.setPageNum(totalCountByColId / 13 + 1);

        markRecentNew(indexArticleByPage);
        //搜索为第一级栏目的栏目列表,在导航栏显示(是第一级栏目并且isNav的)
        List<ArtColumn> navColumn = artColumnSevice.findIsParent(true);
        navColumn = subList(navColumn, 5);

        request.setAttribute("navColumn", navColumn);
        request.setAttribute("indexArticleByPage", indexArticleByPage);
        request.setAttribute("pageNum", indexArticleByPage.getPageNum());
        return "indexArticle";
    }

    public void byPage() {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        Page<Article> pages = articleService.getIndexArticleByPage(page,13);
        markRecentNew(pages);
        sendArticleData("",json, pages);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
