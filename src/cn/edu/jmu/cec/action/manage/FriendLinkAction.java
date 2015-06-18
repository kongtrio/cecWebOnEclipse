package cn.edu.jmu.cec.action.manage;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.FriendLink;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class FriendLinkAction extends BaseAction<FriendLink> implements
        ServletContextAware {
    private String ids;
    public String execute() {
        List<FriendLink> all = friendLinkService.getAll();
        request.setAttribute("friendLinks",all);
        request.setAttribute("path","friendLink");
        return "friendlink";
    }

    public String toAdd(){
        request.setAttribute("path","addLink");
        return "add";
    }

    public String add() {
        String result = "";
        if (!isNotBlank(model.getName()) || !isNotBlank(model.getAddress())) {
            result = "fail";
        } else {
            if(!model.getAddress().contains("http")){
                 model.setAddress("http://"+model.getAddress());
            }
            friendLinkService.save(model);
            result = "suc";
        }

        request.setAttribute("result",result);
        return toAdd();
    }

    public String del(){
        String idsStr[] = ids.split(",");
        int idsInt[] = new int[idsStr.length];
        for (int i = 0; i < idsStr.length; i++) {
            idsInt[i] = Integer.parseInt(idsStr[i]);
        }

        for (int i=0;i<idsInt.length;i++){
            friendLinkService.delete(idsInt[i]);
        }
        return execute();
    }

    public void update(){
        if(!isNotBlank(model.getName()) || !isNotBlank(model.getAddress())){
            try {
                response.getWriter().write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        FriendLink friendLink = friendLinkService.getById(model.getFriendLinkId());
        if(friendLink!=null){
            friendLink.setName(model.getName());
            String addr = model.getAddress();
            if(!model.getAddress().contains("http")){
                addr = "http://"+ addr;
            }
            friendLink.setAddress(addr);
            friendLink.setIsIndex(model.getIsIndex());
            friendLink.setSummary(model.getSummary());
            friendLinkService.update(friendLink);
        }else{
            try {
                response.getWriter().write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setServletContext(ServletContext servletContext) {

    }
}
