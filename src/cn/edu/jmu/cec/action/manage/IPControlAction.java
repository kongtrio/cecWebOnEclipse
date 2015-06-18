package cn.edu.jmu.cec.action.manage;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.domain.FriendLink;
import cn.edu.jmu.cec.domain.IPControl;
import cn.edu.jmu.cec.domain.Users;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class IPControlAction extends BaseAction<IPControl> implements
        ServletContextAware {
    public String execute() {
        Users user = (Users) session.get("user");
        logger.info(user.getNickName()+"用户进入修改IP页面");
        //获取后台访问白名单
        List<IPControl> managerIps = ipControlSevice.getByType(0);
        //获取查看校内新闻白名单
        List<IPControl> schoolIps = ipControlSevice.getByType(1);
        //获取整个系统黑名单
        List<IPControl> systemIps = ipControlSevice.getByType(2);
        request.setAttribute("managerIps",managerIps);
        request.setAttribute("schoolIps",schoolIps);
        request.setAttribute("systemIps",systemIps);

        request.setAttribute("path","ipcontrol");
        return "toPage";
    }

    public void add() {
        response.setContentType("text/html;charset=UTF-8");
        IPControl ipControl = model;
        ipControlSevice.save(ipControl);
        boolean flag = false;
        List<IPControl> ips = ipControlSevice.getByType(model.getType());
        if(ips!=null && ips.size()>0){
            flag = true;
        }
        JSONObject json = new JSONObject();
        json.put("ips",ips);
        json.put("res",flag);
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void del() {
        response.setContentType("text/html;charset=UTF-8");
        int id = model.getId();
        ipControlSevice.delete(id);
        boolean flag = false;
        List<IPControl> ips = ipControlSevice.getByType(model.getType());
        if(ips!=null && ips.size()>0){
            flag = true;
        }
        JSONObject json = new JSONObject();
        json.put("ips",ips);
        json.put("res",flag);
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setServletContext(ServletContext servletContext) {

    }


}
