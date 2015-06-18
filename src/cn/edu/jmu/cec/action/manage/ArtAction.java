package cn.edu.jmu.cec.action.manage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.ServletContext;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;

import cn.edu.jmu.cec.common.Page;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.service.ArtColumnSevice;
import cn.edu.jmu.cec.service.ArticleService;
import cn.edu.jmu.cec.service.UsersService;

public class ArtAction extends BaseAction<Article> implements
        ServletContextAware {
    private ServletContext servletContext;
    //图片访问路径
    private final static String TEMPATH = "upload/titlePic/";
    //图片保存路径
        private final static String SAVE_PATH = "/usr/tomcat/apache-tomcat-8.0.23/webapps/cecWeb/upload/titlePic/";
//    private final static String SAVE_PATH = "D:\\javaCode\\cecWeb\\out\\artifacts\\cecWeb_war_exploded\\/upload\\titlePic/";
    private File artImage;
    private String artImageFileName;
    private int colId;
    private int page;
    private String type;
    private String key;
    private String ids;
    private String stage;

    //新闻管理
    public String toArtManage() {
        request.setAttribute("path", "showArticle");
        toShowNews();
        return "artManage";
    }

    public String toAdd() {
        Users user = (Users) session.get("user");
        if (user == null) {
            return "indexPage";
        }
        List<ArtColumn> allColumn = null;
        if (user.getULevel()) {
            allColumn = artColumnSevice.findIsParentWithAll();
        } else {
            List<ColmunRight> byUsers = colmunRightService.findByUsers(user);
            allColumn = getArtColumnWithRight(byUsers);
        }

        request.setAttribute("columns", allColumn);
        request.setAttribute("path", "articleAdd");
        return "addArticle";
    }

    //添加新闻
    public String addNews() {
        if (!isNotBlank(model.getAuthor()) || !isNotBlank(model.getTitle())) {
            request.setAttribute("result", "error");
            return toAdd();
        }

        ArtColumn colmun = artColumnSevice.getById(colId);
        if(colmun.getArtColumns().size()>0){
            request.setAttribute("result", "ColumnErr");
            return toAdd();
        }
        if (colmun == null) {
            request.setAttribute("result", "error");
            return toAdd();
        }
        if (model != null) {
            model.setArtColumn(colmun);
            saveImage(model);
            if (model.getIsAddress() && !isNotBlank(model.getOutAddress())) {
                request.setAttribute("result", "error");
                return toAdd();
            } else if (!isNotBlank(model.getTitle())) {
                request.setAttribute("result", "error");
                return toAdd();
            }
            model.setStatus((short) 1);
            model.setIsNewTab(8);
            articleService.save(model);

            //更新栏目新闻数
            ArtColumn artColumn = model.getArtColumn();
            artColumn.setArtCount(artColumn.getArtCount() + 1);
            artColumnSevice.modify(artColumn);

            //更新发布人所发布的新闻数
            Users user = (Users) session.get("user");
            user.setArtCount(user.getArtCount()+1);
            usersService.update(user);
        } else {
            request.setAttribute("result", "error");
            return toAdd();
        }

        //更新发布新闻数
//        Users user = (Users) session.get("user");
//        user.setArtCount(user.getArtCount() + 1);
//        usersService.update(user);

        request.setAttribute("result", "addSuc");
        colId = 0;
        return toAdd();
    }

    //保存标题图片
    public void saveImage(Article article) {
        if (artImage == null) {
            return;
        }
        int index = artImageFileName.lastIndexOf('.');
        long nowTime = System.currentTimeMillis();
        String artFileName = nowTime + artImageFileName.substring(index);
        File i = new File(SAVE_PATH, artFileName);
        try {
            FileUtils.copyFile(artImage, i);
            article.setTitlePic(TEMPATH + "/" + artFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //展示新闻
    public void toShowNews() {
        Users user = (Users) session.get("user");
        List<ArtColumn> allColumn = null;
        ArtColumn colmun = null;
        if (user.getULevel()) {
            allColumn = artColumnSevice.findIsParentWithAll();
            if (colId != 0) {
                colmun = artColumnSevice.getById(colId);
            }
        } else {
            List<ColmunRight> byUsers = colmunRightService.findByUsers(user);
            allColumn = getArtColumnWithRight(byUsers);
            if (colId == 0) {
                colmun = getAllRightColum(byUsers);
            }else{
                colmun = artColumnSevice.getById(colId);
                if(colmun.getArtColumns().size()!=0){
                    colmun = getNewColumnWithColumnRight(colmun, byUsers);
                }
            }
        }

        Page<Article> pages = null;


        if (!isNotBlank(key)) {
            pages = articleService.getAllByPage(1, colmun, 13);
            int totalCountByColId = articleService.getTotalCountByCol(colmun);
            pages.setTotalCount(totalCountByColId);
            pages.setPageNum(totalCountByColId / 13 + 1);
        } else {
            try {
                pages = articleService.getDataByPage(type, key, 1, colmun, 13);
                int totalCountByColId = articleService.getTotalCountByKey(key, type,colmun);
                pages.setTotalCount(totalCountByColId);
                pages.setPageNum(totalCountByColId / 13 + 1);
            } catch (NumberFormatException e) {
            }
        }

        //准备栏目列表
        request.setAttribute("columns", allColumn);

        formatTitle(key, pages);
        request.setAttribute("news", pages);
        if (isNotBlank(type)) {
            request.setAttribute("type", type);
        }
        if (isNotBlank(key)) {
            request.setAttribute("key", key);
        }
        if (isNotBlank(colId)) {
            request.setAttribute("colId", colId);
        }
    }

    //分页获取新闻，异步ajax
    public void showNewByPage() {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        Page<Article> pages = null;
        Users user = (Users) session.get("user");
        ArtColumn colmun = null;
//        if(!user.getULevel() && colId==0){
//            List<ColmunRight> byUsers = colmunRightService.findByUsers(user);
//            colmun = getAllRightColum(byUsers);
//        }else{
//            colmun = artColumnSevice.getById(colId);
//        }
        if (user.getULevel()) {
            if (colId != 0) {
                colmun = artColumnSevice.getById(colId);
            }
        } else {
            List<ColmunRight> byUsers = colmunRightService.findByUsers(user);
            if (colId == 0) {
                colmun = getAllRightColum(byUsers);
            }else{
                colmun = artColumnSevice.getById(colId);
                if(colmun.getArtColumns().size()!=0){
                    colmun = getNewColumnWithColumnRight(colmun, byUsers);
                }
            }
        }

        if (key == null || "".equals(key)) {
            pages = articleService.getAllByPage(page, colmun, 13);
        } else {
            try {
                key = URLDecoder.decode(key, "utf-8");
            } catch (UnsupportedEncodingException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            try {
                pages = articleService.getDataByPage(type, key, page, colmun, 13);
            } catch (NumberFormatException e) {
                json.put("result", "fail:key is not a number");
                try {
                    response.getWriter().write(json.toString());
                    return;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }

        sendArticleData(key, json, pages);
    }


    //删除新闻
    public String deleteNews() {
        logger.info("删除以下文章" + ids);
        String idsStr[] = ids.split(",");
        int idsInt[] = new int[idsStr.length];
        for (int i = 0; i < idsStr.length; i++) {
            idsInt[i] = Integer.parseInt(idsStr[i]);
        }

        for (int i=0;i<idsInt.length;i++){
            Article byId = articleService.getById(idsInt[i]);
            if(byId==null){
                continue;
            }
            ArtColumn artColumn = byId.getArtColumn();
            artColumn.setArtCount(artColumn.getArtCount()+1);
            artColumnSevice.modify(artColumn);
        }

        if (isNotBlank(ids)) {
            articleService.deleteByIds(idsInt);
        }
        return toArtManage();
    }

    //前往修改新闻页面
    public String toAlterView() {
        if (isNotBlank(model.getArtId())) {
            Article art = articleService.getById(model.getArtId());
            if (art == null) {
                return toArtManage();
            }
            List<ArtColumn> allColumn = artColumnSevice.findIsParentWithAll();

            request.setAttribute("columns", allColumn);
            request.setAttribute("art", art);
            request.setAttribute("path", "showArticle");
        } else {
            return toArtManage();
        }
        return "alterView";
    }

    //修改新闻
    public String alterNews() {
        Article article = articleService.getById(model.getArtId());
        if (isNotBlank(article)) {
            saveImage(model);
            ArtColumn artCol = artColumnSevice.getById(colId);
            if (isNotBlank(artCol)) {
                article.setArtColumn(artCol);

                //修改栏目新闻条数
                ArtColumn artColumn = article.getArtColumn();
                artColumn.setArtCount(artColumn.getArtCount()-1);
                artColumnSevice.modify(artColumn);
                ArtColumn parent = artColumn.getArtColumn();
                if(parent!=null){
                    parent.setArtCount(parent.getArtCount()-1);
                    artColumnSevice.modify(parent);
                }

                artCol.setArtCount(artCol.getArtCount()+1);
                artColumnSevice.modify(artCol);
                ArtColumn parent2 = artCol.getArtColumn();
                if(parent2!=null){
                    parent2.setArtCount(parent2.getArtCount()+1);
                    artColumnSevice.modify(parent2);
                }
            }
            article.setAuthor(model.getAuthor());
            article.setIsAddress(model.getIsAddress());
            if (model.getIsAddress()) {
                article.setOutAddress(model.getOutAddress());
                article.setContent("");
            } else {
                article.setContent(model.getContent());
                article.setOutAddress("");
            }
            article.setSummary(model.getSummary());
            article.setTitle(model.getTitle());
            article.setIsMark(model.getIsMark());
            article.setIsColmunTop(model.getIsColmunTop());
            article.setIsIndexTop(model.getIsIndexTop());
            article.setIsTop(model.getIsTop());
            article.setIsSchool(model.getIsSchool());

            if (isNotBlank(model.getTitlePic())) {
                article.setTitlePic(model.getTitlePic());
            }
            articleService.update(article);
            stage = "showNews";
            key = model.getArtId() + "";
            type = "artId";
            request.setAttribute("result", "alterSuc");
            return toArtManage();
        }
        return "input";
    }

    //将所有权限的栏目以树形的结构展现出来
    public List<ArtColumn> getArtColumnWithRight(List<ColmunRight> colmunRights) {
        List<ArtColumn> list = new ArrayList<ArtColumn>();
        for (ColmunRight colmunRight : colmunRights) {
            ArtColumn artColumn = colmunRight.getArtColumn();
            if (artColumn.getArtColumn() == null && artColumn.getArtColumns().size() == 0) {
                ArtColumn newColumn = new ArtColumn();
                newColumn.setColumnId(artColumn.getColumnId());
                newColumn.setColName(artColumn.getColName());
                list.add(artColumn);
            } else if (artColumn.getArtColumn() == null && artColumn.getArtColumns().size() != 0) {
                ArtColumn newColumn = new ArtColumn();
                Set<ArtColumn> artColumns = artColumn.getArtColumns();
                Set<ArtColumn> newColumns = new HashSet<ArtColumn>();
                for (ArtColumn column : artColumns) {
                    ArtColumn newCol = new ArtColumn();
                    newCol.setColumnId(column.getColumnId());
                    newCol.setColName(column.getColName());
                    newColumns.add(newCol);
                }
                newColumn.setColumnId(artColumn.getColumnId());
                newColumn.setColName(artColumn.getColName());
                newColumn.setArtColumns(newColumns);
                list.add(artColumn);
            } else if (artColumn.getArtColumn() != null) {
                ArtColumn newColumn = null;
                ArtColumn parent = artColumn.getArtColumn();
                if (list.contains(parent)) {
                    for (int i = 0; i < list.size(); i++) {
                        ArtColumn artColumn1 = list.get(i);
                        if (parent.equals(artColumn1)) {
                            Set artColumns = artColumn1.getArtColumns();
                            newColumn = artColumn.cloneColumn();
                            artColumns.add(newColumn);
                        }
                    }
                } else {
                    newColumn = artColumn.cloneColumn();
                    ArtColumn newParent = parent.cloneColumn();
                    Set<ArtColumn> children = new HashSet<ArtColumn>();
                    children.add(newColumn);
                    newParent.setArtColumns(children);
                    list.add(newParent);
                }
            }
        }
        return list;
    }

    public ArtColumn getAllRightColum(List<ColmunRight> colmunRights) {
        ArtColumn artColumn = new ArtColumn();
        Set<ArtColumn> columns = new HashSet<ArtColumn>();
        for (ColmunRight colmunRight : colmunRights) {
            ArtColumn artColumn1 = colmunRight.getArtColumn();
            ArtColumn newColumn = artColumn1.cloneColumn();
            columns.add(newColumn);
        }
        artColumn.setArtColumns(columns);
        return artColumn;
    }

    //制造一个又规定权限的栏目
    public ArtColumn getNewColumnWithColumnRight(ArtColumn artColumn,List<ColmunRight> colmunRights){
        List<ArtColumn> list = getArtColumnWithRight(colmunRights);
        if(!list.contains(artColumn)){
            return artColumn;
        }else{
            ArtColumn newCol = null;
            for (int i=0;i<list.size();i++){
                ArtColumn artColumn1 = list.get(i);
                if(artColumn1.equals(artColumn)){
                    newCol = artColumn1;
                }
            }
            return newCol;
        }
    }
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

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public ArtColumnSevice getArtColumnSevice() {
        return artColumnSevice;
    }

    public void setArtColumnSevice(ArtColumnSevice artColumnSevice) {
        this.artColumnSevice = artColumnSevice;
    }

    public void setServletContext(ServletContext arg0) {
        this.servletContext = servletContext;
    }

    public File getArtImage() {
        return artImage;
    }

    public void setArtImage(File artImage) {
        this.artImage = artImage;
    }

    public String getArtImageFileName() {
        return artImageFileName;
    }

    public void setArtImageFileName(String artImageFileName) {
        this.artImageFileName = artImageFileName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }


}
