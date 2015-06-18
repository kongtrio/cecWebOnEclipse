package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.json.ArticleJson;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

/**
 * Created by yangjb on 2015/4/9.
 */
public class ColumnAction extends BaseAction<ArtColumn> implements
        ServletContextAware {

    private int colId;
    private int page;
    private int articleId;
    //为栏目展现和文章展现准备数据
    public String execute() {
        ArtColumn column = artColumnSevice.getById(colId);
        if (column == null) {
            return "index";
        }
        List<ArtColumn> colList = new ArrayList<ArtColumn>();
        //如果查询的栏目是第一级栏目并且没有子栏目
        if (column.getArtColumn() == null) {
            if (column.getArtColumns().size() == 0) {
                Page<Article> articlByCidWithPage = articleService.getArticlByCidWithPage(column.getColumnId() + "", 1, 13);
                int totalCountByColId = articleService.getTotalCountByColId(column.getColumnId());
                articlByCidWithPage.setTotalCount(totalCountByColId);
                articlByCidWithPage.setPageNum(totalCountByColId / 13 + 1);
                markRecentNew(articlByCidWithPage);
                colList.add(column);
                request.setAttribute("articles", articlByCidWithPage);
                request.setAttribute("colId", column.getColumnId());
            } else if (column.getArtColumns().size() > 0) {
                //如果查询的栏目是第一级栏目并且有两个以上的子栏目
                Set<ArtColumn> children = column.getArtColumns();
                List<ArtColumn> listChild = new ArrayList<ArtColumn>();
                for (ArtColumn artColumn : children) {
                    listChild.add(artColumn);
                }
                //取出等级最高的3个子栏目
                Collections.sort(listChild, new Comparator<ArtColumn>() {
                    public int compare(ArtColumn o1, ArtColumn o2) {
                        return o1.getColLevel() - o2.getColLevel();
                    }
                });
                List<ArtColumn> childs = subList(listChild, 3);
                List<ArtColumn> artColumns = formatNavNew(childs);
                colList.add(column);
                request.setAttribute("children", artColumns);
                request.setAttribute("style", 0);
            }
        } else {
            //如果是子栏目
            colList.add(column.getArtColumn());
            colList.add(column);

            //如果该栏目下只有一个文章，那就直接跳转到该文章
            Page<Article> articlByCidWithPage = articleService.getArticlByCidWithPage(column.getColumnId() + "", 1, 13);
            if (articlByCidWithPage.size()==1){
                articleId = articlByCidWithPage.get(0).getArtId();
                return "toArt";
            }

            int totalCountByColId = articleService.getTotalCountByColId(column.getColumnId());
            articlByCidWithPage.setTotalCount(totalCountByColId);
            articlByCidWithPage.setPageNum(totalCountByColId / 13 + 1);

            markRecentNew(articlByCidWithPage);
            request.setAttribute("articles", articlByCidWithPage);
            request.setAttribute("colId", column.getColumnId());
        }

        //搜索为第一级栏目的栏目列表,在导航栏显示(是第一级栏目并且isNav的)
        List<ArtColumn> navColumn = artColumnSevice.findIsParent(true);
        navColumn = subList(navColumn, 5);
        request.setAttribute("colLists", colList);
        request.setAttribute("navColumn", navColumn);
        return "column";
    }


    //分页获取
    public void ArticleByPage() {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        Page<Article> pages = articleService.getArticlByCidWithPage(colId + "", page, 13);
        markRecentNew(pages);
        sendArticleData("", json, pages);
    }


    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
