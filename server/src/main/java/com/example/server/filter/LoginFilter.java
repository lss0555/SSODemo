//package com.example.server.filter;
//
//import com.example.common.constance.CookieConstance;
//import com.example.common.utils.CookieUtil;
//import com.example.server.model.User;
//import com.example.server.redis.inter.TokenManagerInter;
//
//import javax.annotation.Resource;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URLEncoder;
//
///**
// * @Description 登录权限过滤器
// * @Author lss0555
// * @Date 2018/12/26/026 15:06
// **/
//@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
//public class LoginFilter implements Filter {
//    @Resource
//    HttpServletRequest request;
//
//    @Resource
//    HttpServletResponse response;
//
//    @Resource
//    TokenManagerInter tokenManagerInter;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("拦截请求");
////        filterChain.doFilter(servletRequest,servletResponse);
//        String cookieName = CookieUtil.getCookie(CookieConstance.COOKIE_SSO, request);
//        if(cookieName==null||cookieName.equals("")){
//            //验证cookie的有效性
//            User user = tokenManagerInter.getUserInfo(cookieName);
//            if(user!=null){
//                //验证成功,继续执行
//                filterChain.doFilter(servletRequest,servletResponse);
//            }else {
//                //验证失败,删除无效cookie
//                CookieUtil.deleteCookie(CookieConstance.COOKIE_SSO,response, "/");
//                //返回登录界面
//                String backUrl="";
//                String location = "http://localhost:8002" + "/login?backUrl=" + URLEncoder.encode(backUrl, "utf-8");
//                response.sendRedirect(location);
//            }
//        }else {
//            //返回登录界面
//            String backUrl="";
//            String location = "http://localhost:8002" + "/login?backUrl=" + URLEncoder.encode(backUrl, "utf-8");
//            response.sendRedirect(location);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
