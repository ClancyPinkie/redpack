package com.redpack.Filter;

import com.alibaba.fastjson.JSON;
import com.redpack.common.BaseContext;
import com.redpack.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 创建自定义过滤器
 * 启动类加入注解@ServletComponentScan
 */
@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("拦截到请求：{}",request.getRequestURL());

        //1、获取请求的URL
        String requestURI = request.getRequestURI();

        //2、定义不需要处理的URL,即放行
        String[] urls = new String[]{
                "/user/login",
                "/user/register",
                "/webapp/page/login.html"
        };

        //判断本次请求是否需要放行
        boolean check = check(urls,requestURI);

        //3、如果不需要放行则直接通过
        if (check){
            log.info("请求已放行：{} ",requestURI);
            filterChain.doFilter(request,response);
            return;
        }



        //4、判断登陆状态，如果已经登陆则保存当前id
        if (request.getSession().getAttribute("user") != null){
            log.info("用户 {} 已登陆",request.getSession().getAttribute("user"));

            //保存当前用户id，用户刷新页面依然能保持登陆状态
            Long empId = (Long) request.getSession().getAttribute("user");
            BaseContext.setThreadLocalId(empId);

            filterChain.doFilter(request,response);
            return;
        }

        //4-2、判断手机端用户登陆情况
//        if (request.getSession().getAttribute("phone") != null){
//            log.info("用户 {} 已登陆",request.getSession().getAttribute("phone"));
//
//            Long userId = (Long) request.getSession().getAttribute("phone");
//            BaseContext.setThreadLocalId(userId);
//
//            filterChain.doFilter(request,response);
//            return;
//        }

        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }



    /**
     * 路径匹配，判断本次的请求是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls,String requestURL){

        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match){
                return true;
            }
        }
        return false;

    }
}
