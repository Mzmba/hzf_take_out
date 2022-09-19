package com.ka.filter;

import com.alibaba.fastjson.JSON;
import com.ka.common.R;
import com.ka.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: LoginCheckFilter
 * @Description: 检查用户是否已经完成登录，拦截所有地址
 * @author: momo
 * @date:
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("LoginCheckFilter拦截到请求了，uri是：{}", requestURI);
//        定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
//        2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
//        3、如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理，因为在访问不需要处理的路径", requestURI);
            filterChain.doFilter(request, response);//过滤器传下去
            return;
        }
//        4、判断登录状态，如果后端员工已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null && !(requestURI.contains("/user") || requestURI.contains("/front"))) {
            log.info("用户已登录所以直接放行，用户id为：{}", request.getSession().getAttribute("employee"));
            //employee里面存的就一个empid哦！！
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }
        //        4-2、判断登录状态，如果前端用户已登录，则直接放行
        if (request.getSession().getAttribute("user") != null && !(requestURI.contains("/employee") || requestURI.contains("/backend"))) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("user"));
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
//        5、如果未登录则返回未登录结果,通过输出流向客户端页面响应数据，前端收到这个json信息会判定，然后就跳转到登录页面
        //前端有拦截器完成跳转页面，所以用输入流写个json来触发前端的拦截器完成跳转
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        //不放行
        return;
    }

    //路径匹配，检查本次请求是否需要放行
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match == true) {
                return true;
            }
        }
        return false;
    }

}
