package cn.edu.jmu.cec.filter;

/**
 * Created by yangjb on 2015/3/25.
 */

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareFilter;

public class MyStrutsPrepareFilter extends StrutsPrepareFilter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        //不过滤的url
        String url = request.getRequestURI();
        if (url.contains("/ueditor/")) {
            //其他 /jsp/ 下的几个路径我不常用，就没写了
            //System.out.println("使用自定义的过滤器"+url);
            chain.doFilter(req, res);
        } else {
            //System.out.println("使用默认的过滤器");
            super.doFilter(req, res, chain);
        }
    }
}
