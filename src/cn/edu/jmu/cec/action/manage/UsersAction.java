package cn.edu.jmu.cec.action.manage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.common.MD5Code;
import cn.edu.jmu.cec.domain.ArtColumn;
import org.apache.struts2.util.ServletContextAware;

import cn.edu.jmu.cec.domain.Users;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class UsersAction extends BaseAction<Users> implements
        ServletContextAware {
    private int[] ids;
    private String verifyCode;

    private String toBackStage() {
//        Users user = (Users) session.get("user");
//        if (user == null) {
//            request.setAttribute("result", "2");
//            return "index";
//        }
//        if (user.getULevel()) {
//            List<ArtColumn> colParent = artColumnSevice.findByParentId(0);
//            formatData(colParent);
//        }
        return "state";
    }

    public String toUser() {

        request.setAttribute("path","addUser");
        return "addUser";
    }

    public String showUser(){
                List<Users> all = usersService.findAll();
        request.setAttribute("users", all);
        request.setAttribute("path","showUser");
        return "user";
    }

    public String exit() {
        session.remove("user");
        return "toIndex";
    }


    public String addUser() {
        MD5Code md5 = new MD5Code();
        Users user = (Users) model;
        String md5ofStr = md5.getMD5ofStr(user.getPassword());
        user.setPassword(md5ofStr);
        user.setArtCount(0);
        user.setLoginCount(0);
        user.setRegTime(new Timestamp(new Date().getTime()));
        user.setLastTime(new Timestamp(new Date().getTime()));
        usersService.update(user);
        request.setAttribute("result", "suc");
        return toUser();
    }

    public String delete() {
        request.setAttribute("stage", "two");
        for (int i = 0; i < ids.length; i++) {
            Users user = usersService.getById(ids[i]);
            usersService.delete(user);
        }
        return showUser();
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public void setServletContext(ServletContext arg0) {
        // TODO Auto-generated method stub

    }

    public int[] getIds() {
        return ids;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setIds(int[] ids) {

        this.ids = ids;
    }
}
