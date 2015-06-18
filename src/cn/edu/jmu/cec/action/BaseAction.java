package cn.edu.jmu.cec.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.IPControl;
import cn.edu.jmu.cec.domain.Users;
import cn.edu.jmu.cec.json.ArticleJson;
import cn.edu.jmu.cec.service.*;
import cn.edu.jmu.cec.service.impl.ColmunRightServiceImpl;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, SessionAware, ServletRequestAware {
    public T model;
    Class<T> clazz;
    public Logger logger = Logger.getLogger(this.getClass());
    public static final long DAY_TIMES = 24 * 60 * 60 * 1000;

    public BaseAction() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) pt.getActualTypeArguments()[0];
        try {
            model = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T getModel() {
        return model;
    }


    /**
     * 拿到已登陆用户对�
     */
    protected Users getUser() {
        Users user = (Users) session.get("user");
        return user;
    }

    //====================Service实例的声� ===================

    protected UsersService usersService;
    protected ArticleService articleService;
    protected ArtColumnSevice artColumnSevice;
    protected ColumnRightSevice colmunRightService;
    protected FriendLinkSevice friendLinkService;
    protected IPControlSevice ipControlSevice;

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ArtColumnSevice getArtColumnSevice() {
        return artColumnSevice;
    }

    public void setArtColumnSevice(ArtColumnSevice artColumnSevice) {
        this.artColumnSevice = artColumnSevice;
    }

    public ColumnRightSevice getColmunRightService() {
        return colmunRightService;
    }

    public IPControlSevice getIpControlSevice() {
        return ipControlSevice;
    }

    public void setIpControlSevice(IPControlSevice ipControlSevice) {
        this.ipControlSevice = ipControlSevice;
    }

    public void setColmunRightService(ColumnRightSevice colmunRightService) {
        this.colmunRightService = colmunRightService;
    }

    public FriendLinkSevice getFriendLinkService() {
        return friendLinkService;
    }

    public void setFriendLinkService(FriendLinkSevice friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    //====================分页========================
    /**
     * 分页相关参数
     */
    private static final long serialVersionUID = 6230751116897773145L;
    public static final Log log = LogFactory.getLog(BaseAction.class);
    protected Map<String, Object> session;
    protected HttpServletRequest request;
    protected HttpServletResponse response = ServletActionContext.getResponse();
    protected int pageNum = 1;
    protected int numPerPage = 4;
    protected int totalCount;
    protected int currentPage;
    protected int totalPage;
    protected Page pages;


    /**
     * 装载当前页数据�
     *
     * @param hql 查询语句
     * @return Page
     */
    protected <T> Page<T> createPage(Page page) {
        page.setNumPerPage(getNumPerPage());
        page.setPageNum(getPageNum());
        page.setTotalPage(gainTotalPage(page.getTotalCount(), getNumPerPage()));
        setPages(page);
        return page;
    }

    public int gainTotalPage(int totalCount, int rowsperpage) {
        int pages = 0;
        if (totalCount == 0) {
            pages = 0;
        } else {
            if (totalCount <= rowsperpage)
                pages = 1;
            if (totalCount > rowsperpage && totalCount % rowsperpage == 0)
                pages = totalCount / rowsperpage;
            if (totalCount > rowsperpage && totalCount % rowsperpage != 0)
                pages = totalCount / rowsperpage + 1;
        }
        return pages;
    }

    protected boolean isNotBlank(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof String && object.toString().trim().equals("")) {
            return false;
        }
        return true;
    }

    //将发布时间为最近24小时的新闻加个new
    protected void markRecentNew(List<Article> articles) {
        for (Article article : articles) {
            long currentTime = System.currentTimeMillis();
            long publishedTime = article.getPublicTime().getTime();
            if ((currentTime - publishedTime) <= DAY_TIMES) {
                article.setIsNew(true);
            }
        }
    }

    protected void sendArticleData(String key, JSONObject json, Page<Article> pages) {
        List<ArticleJson> articles = new ArrayList<ArticleJson>();
        for (Article art : pages) {
            ArticleJson atrjson = new ArticleJson();
            atrjson.setArtColumnId(art.getArtColumn().getColumnId());
            atrjson.setArtColumnName(art.getArtColumn().getColName());
            atrjson.setArtId(art.getArtId());
            atrjson.setPublicTime(art.getPublicTime());
            atrjson.setIsNew(art.getIsNew());
            atrjson.setIsMark(art.getIsMark());
            atrjson.setIsSchool(art.getIsSchool());
            atrjson.setIsIndexTop(art.getIsIndexTop());
            atrjson.setIsTop(art.getIsTop());
            atrjson.setIsColmunTop(art.getIsColmunTop());

            String title = art.getTitle();
            if (isNotBlank(key)) {
                String keyReplace = "<font style='color:red;'>" + key + "</font>";
                title = title.replaceAll(key, keyReplace);
            }
            atrjson.setTitle(title);
            articles.add(atrjson);
        }

        json.put("result", "suc");
        json.put("news", articles);
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected List<Article> formatTitle(String key, Page<Article> pages) {
        if(pages==null){
            return new ArrayList<Article>();
        }
        List<Article> list = new ArrayList<Article>();
        for (Article art : pages) {
            //防止title修改后,hibernate会自动把它保存到数据库
            Article newArt = new Article();
            newArt.setPublicTime(art.getPublicTime());
            newArt.setArtId(art.getArtId());
            newArt.setIsMark(art.getIsMark());
            newArt.setIsSchool(art.getIsSchool());
            newArt.setIsNew(art.getIsNew());
            String title = art.getTitle();
            if (isNotBlank(key)) {
                String keyReplace = "<font style='color:red;'>" + key + "</font>";
                title = title.replaceAll(key, keyReplace);
            }
            newArt.setTitle(title);
            list.add(newArt);
        }
        return list;
    }

    //返回List的前n个
    public List subList(List parentList, int n) {
        if (parentList != null && parentList.size() > n) {
            return parentList.subList(0, n);
        }
        return parentList;
    }

    //格式化数据
    public List<ArtColumn> formatNavNew(List<ArtColumn> artColumns) {
        List<ArtColumn> newData = new ArrayList<ArtColumn>();
        if (artColumns != null && artColumns.size() != 0) {
            for (int i = 0; i < artColumns.size(); i++) {
//                List<Article> articleByCid = articleService.findArticleByCid(artColumns.get(i).getColumnId());
                List<Article> list = articleService.getArticlByCidWithPage(artColumns.get(i).getColumnId() + "", 1, 8);
                if (list != null && list.size() != 0) {
//                    formatArticle(list);
                    markRecentNew(list);
                    List<Article> arts = new ArrayList<Article>(list);
                    artColumns.get(i).setListArticles(arts);
                    newData.add(artColumns.get(i));
                    //有5个的时候就不用再添加了
                    if (newData.size() > 4) {
                        break;
                    }
                }
            }
        }
        return newData;
    }

    public void formatArticle(List<Article> list) {
        for (Article art : list) {
            if (art.getTitle().length() > 14) {
                String tit = art.getTitle().substring(0, 14) + "..";
                art.setTitle(tit);
            }
        }
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Page getPages() {
        return pages;
    }

    public void setPages(Page page) {
        this.pages = page;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getSession() {
        return session;
    }

}
