package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.json.ArticleJson;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/26.
 */
public class SearchArticleAction extends BaseAction<ArtColumn> implements
        ServletContextAware {
    private String key;
    private int page;
    private String type = "title";

    //展示查询的结果
    public String execute() {
        Page<Article> byKeyWithPage = articleService.getByKeyWithPage(1, key, type,13);
        int totalCountByColId = articleService.getTotalCountByKey(key, type);
        byKeyWithPage.setTotalCount(totalCountByColId);
        byKeyWithPage.setPageNum(totalCountByColId / 13 + 1);

        markRecentNew(byKeyWithPage);
        List<Article> articles = formatTitle(key, byKeyWithPage);
        //搜索为第一级栏目的栏目列表,在导航栏显示(是第一级栏目并且isNav的)
        List<ArtColumn> navColumn = artColumnSevice.findIsParent(true);
        navColumn = subList(navColumn, 5);

        request.setAttribute("navColumn", navColumn);
        request.setAttribute("key", key);
        request.setAttribute("type", type);
        request.setAttribute("articles", articles);
        request.setAttribute("pageNum", byKeyWithPage.getPageNum());
        return "articles";
    }

    //分页查询
    public void byPage() {
        try {
            key = URLDecoder.decode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Page<Article> pages = articleService.getByKeyWithPage(page, key, type,13);
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        markRecentNew(pages);
        sendArticleData(key, json, pages);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
