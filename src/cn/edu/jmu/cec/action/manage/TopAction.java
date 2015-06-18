package cn.edu.jmu.cec.action.manage;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.Users;
import cn.edu.jmu.cec.json.ArticleJson;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjb on 2015/4/10.
 */
public class TopAction extends BaseAction<Article> implements
        ServletContextAware {
    private String type;
    private int page;
    private int colId;
    private boolean is2Top;
    private String ids;
    private boolean topChange;

    //查看置顶列表
    public String toTopPage() {
        if (!isNotBlank(type)) {
            type = "isTop";
        }
        ArtColumn artColumn = null;
        if (colId != 0) {
            artColumn = artColumnSevice.getById(colId);
        }
        Page<Article> pages = articleService.getTopDataByPage(is2Top, type, 1, 13, artColumn);

        int totalCountByColId = articleService.getTotalCountByTop(is2Top,artColumn,type);
        pages.setTotalCount(totalCountByColId);
        pages.setPageNum(totalCountByColId / 13 + 1);

        List<ArtColumn> columns = artColumnSevice.findIsParentWithAll();
        request.setAttribute("news", pages);
        request.setAttribute("type", type);
        request.setAttribute("colId", colId);
        request.setAttribute("is2Top", is2Top);
        request.setAttribute("columns", columns);
        request.setAttribute("path", "top");

        return "topPage";
    }

    //显示置顶的新闻
    public void showTopNewByPage() {
        response.setContentType("text/html;charset=UTF-8");
        if (!isNotBlank(page)) {
            page = 1;
        }
        ArtColumn artColumn = null;
        if (colId != 0) {
            artColumn = artColumnSevice.getById(colId);
        }

        JSONObject json = new JSONObject();
        Page<Article> pages = articleService.getTopDataByPage(is2Top, type, page, 13, artColumn);

        if (pages == null) {
            json.put("result", "fail");
        } else {
            logger.info("获取置顶分页数据");
            List<ArticleJson> articles = new ArrayList<ArticleJson>();
            for (Article art : pages) {
                ArticleJson atrjson = new ArticleJson();
                atrjson.setArtColumnId(art.getArtColumn().getColumnId());
                atrjson.setArtColumnName(art.getArtColumn().getColName());
                atrjson.setArtId(art.getArtId());
                atrjson.setPublicTime(art.getPublicTime());
                atrjson.setTitle(art.getTitle());
                articles.add(atrjson);
            }

            json.put("result", "suc");
            json.put("news", articles);
        }

        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //改变置顶状态
    public String changeTop() {
        logger.info("is2Top=" + is2Top);
        if (!isNotBlank(type)) {
            type = "isTop";
        }
        if (isNotBlank(ids)) {
            String[] idStr = ids.split(",");
            int[] ids = new int[idStr.length];
            for (int i = 0; i < idStr.length; i++) {
                ids[i] = Integer.parseInt(idStr[i]);
            }
            articleService.changeTops(ids, type, topChange);
            request.setAttribute("result", "suc2top");
            return toTopPage();
        }
        request.setAttribute("result", "fail2top");
        return toTopPage();
    }

    public boolean isTopChange() {
        return topChange;
    }

    public void setTopChange(boolean topChange) {
        this.topChange = topChange;
    }

    public boolean isIs2Top() {
        return is2Top;
    }

    public void setIs2Top(boolean is2Top) {
        this.is2Top = is2Top;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
