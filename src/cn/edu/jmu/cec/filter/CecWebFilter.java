package cn.edu.jmu.cec.filter;

import cn.edu.jmu.cec.domain.IPControl;
import cn.edu.jmu.cec.domain.Users;
import cn.edu.jmu.cec.service.IPControlSevice;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/5/21.
 */
public class CecWebFilter extends HttpServlet implements Filter {
    public Logger logger = Logger.getLogger(this.getClass());

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        IPControlSevice iPControlSevice = (IPControlSevice) wac.getBean("IPControlSevice");
        String userIp = getRemortIP(request);
        List<IPControl> systemIp = iPControlSevice.getByType(2);
        if (systemIp != null && systemIp.size() > 0) {
            boolean flag = ipIsContain(systemIp, userIp);
            if (flag) {
                logger.info("有黑名单用户企图登录系统 ip为 > " + userIp);
                response.sendRedirect("/cecWeb/error.jsp");
//                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        }

        HttpSession session = request.getSession(true);
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/manage") || requestURI.contains("/login")) {
            List<IPControl> manageIps = iPControlSevice.getByType(0);
            if (manageIps == null || manageIps.size() == 0 || !ipIsContain(manageIps, userIp)) {
                logger.info("有不法用户企图登录后台系统 ip疑似为 > " + userIp);
                response.sendRedirect("/cecWeb/error.jsp");
//                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

        }

        if(requestURI.contains("/manage")){
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                logger.info("有不法用户企图登录后台系统 ip疑似为 > " + userIp);
                response.sendRedirect("/cecWeb/error.jsp");
//                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
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

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

}
