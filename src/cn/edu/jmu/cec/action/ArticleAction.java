package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.IPControl;
import cn.edu.jmu.cec.json.ArticleJson;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yangjb on 2015/4/9.
 */
public class ArticleAction extends BaseAction<Article> implements
        ServletContextAware {
    private int articleId;

    public String execute() {
        if (articleId == 0) {
            return "error";
        }
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path+"/";
        //搜索为第一级栏目的栏目列表,在导航栏显示(是第一级栏目并且isNav的)
        List<ArtColumn> navColumn = artColumnSevice.findIsParent(true);
        navColumn = subList(navColumn, 5);

        request.setAttribute("navColumn", navColumn);
        Article article = articleService.getById(articleId);
        if (article.getIsSchool()) {
            String remortIP = getRemortIP(request);
            List<IPControl> byType = ipControlSevice.getByType(1);
            if (byType == null || byType.size() == 0 || !ipIsContain(byType, remortIP)) {
                return "noPass";
            }
        }
        if (article != null) {
            String content1 = article.getContent();
            String content = "";
            //识别原来的资源路径(原来的资源路径都是upload/.....)，修改成自己的项目名称+upload/....
            if(!content1.contains("http") && content1.contains("/upload")){
                content = article.getContent().replaceAll("/upload", basePath + "upload");
            }else{
                content = article.getContent();
            }

            //这个是给表格加上线框，默认的是不显示线框的
            if(content.contains("table")){
                content = content.replaceAll("table","table border=\"1\"");
            }

            Article art = new Article();
            art.setAuthor(article.getAuthor());
            art.setContent(content);
            art.setPublicTime(article.getPublicTime());
            art.setReadCount(article.getReadCount());
            art.setTitle(article.getTitle());
            art.setIsNewTab(article.getIsNewTab());

            request.setAttribute("article", art);
            //更新阅读量
            article.setReadCount(article.getReadCount() + 1);
            articleService.update(article);
        }
        return "article";

    }


    public boolean ipIsContain(List<IPControl> ipControls, String userIp) {
        userIp = userIp.trim();
        for (IPControl ipControl : ipControls) {
            String ip = ipControl.getIp().trim();
            if (ip.contains("*")) {
                ip = ip.substring(0, ip.length() - 1);
                if (userIp.contains(ip)) {
                    return true;
                }
            } else {
                if (ip.equals(userIp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
