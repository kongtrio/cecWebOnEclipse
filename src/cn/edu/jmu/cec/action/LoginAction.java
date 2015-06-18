package cn.edu.jmu.cec.action;

import cn.edu.jmu.cec.common.MD5Code;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.Article;
import cn.edu.jmu.cec.domain.Users;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by yangjb on 2015/4/9.
 */
public class LoginAction extends BaseAction<Users> implements
        ServletContextAware {
    private String verifyCode;

    //登陆验证代码
    public String execute() {
        if (session.containsKey("user")) {
            return "success";
        }
        String name = model.getUserName();
        String pwd = model.getPassword();
        if (!isNotBlank(name) || !isNotBlank(pwd)) {
            request.setAttribute("result", "4");
            return "toLogin";
        }
        String verify = (String) session.get("rand");
        if (!isNotBlank(verifyCode) || !verifyCode.equals(verify)) {
            request.setAttribute("result", "3");
            return "toLogin";
        }
        MD5Code md5 = new MD5Code();
        pwd = md5.getMD5ofStr(pwd);
        Users user = usersService.checkUser(name, pwd);
        if (user != null) {
            logger.info(user.getNickName() + "登陆成功  user level="+user.getULevel());
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastTime(new Timestamp(new Date().getTime()));
            user.setLastIp(getRemortIP(request));
            usersService.update(user);
            session.put("user", user);
            return "success";
        } else {
            //表示账号或者密码错误
            logger.info(name + "登陆失败,账号或者密码错误");
            request.setAttribute("result", "1");
            return "toLogin";
        }
    }

    public String toLogin(){
        return "toLogin";
    }

    public void formatChildColumn(List<ArtColumn> allColumn) {
        for (ArtColumn artColumn : allColumn) {
            Set<ArtColumn> children = artColumn.getArtColumns();
            for (ArtColumn child : children) {
                child.setColName("    -" + child.getColName());
            }
        }
    }


    public void setServletContext(ServletContext servletContext) {

    }

    //获取来访者IP
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
