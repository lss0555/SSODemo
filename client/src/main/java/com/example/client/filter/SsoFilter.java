package com.example.client.filter;

import com.example.client.model.User;
import com.example.client.redis.inter.TokenManagerInter;
import com.example.common.constance.Constans;
import com.example.common.utils.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * @Description 登录过滤器
 * @Author lss0555
 * @Date 2018/12/26/026 10:28
 **/
@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
public class SsoFilter implements Filter{
    @Resource
    HttpServletRequest request;

    @Resource
    HttpServletResponse response;

    @Resource
    TokenManagerInter tokenManagerInter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器拦截过滤");

        String contextPath = request.getServletContext().getContextPath();
        String uri = ((HttpServletRequest) request).getRequestURI();
        uri = uri.substring(contextPath.length());
        System.out.println("当前路径:"+uri);
        if(uri.equals("/test")){
            //通过
            filterChain.doFilter(servletRequest,servletResponse);
        }


        String cookieName = CookieUtil.getCookie(Constans.COOKIE_SSO, request);
        if(cookieName!=null&& !cookieName.equals("")){
            //验证cookie的有效性
            User user = tokenManagerInter.getUserInfo(cookieName);
            if(user!=null){
                //验证成功,继续执行
                //TODO 重置cookie时间,缓存时间
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                //验证失败,删除无效cookie
                CookieUtil.deleteCookie(Constans.COOKIE_SSO,response, "/");
                //返回登录界面
                String qstr = makeQueryString(request); // 将所有请求参数重新拼接成queryString
                String backUrl=request.getRequestURL() + qstr; // 回调url
                String location = "http://localhost:8002" + "/login?backUrl=" + URLEncoder.encode(backUrl, "utf-8");
                response.sendRedirect(location);
            }
        }else {
            String vtParam = pasreVtParam(request); // 从请求中
            if (vtParam == null) {
                // 请求中中没有vtParam，引导浏览器重定向到服务端执行登录校验
                //返回登录界面
                String qstr = makeQueryString(request); // 将所有请求参数重新拼接成queryString
                String backUrl=request.getRequestURL() + qstr; // 回调url
                String location = "http://localhost:8002" + "/login?backUrl=" + URLEncoder.encode(backUrl, "utf-8");
                response.sendRedirect(location);
            } else if (vtParam.length() == 0) {
                // 有vtParam，但内容为空，表示到服务端loginCheck后，得到的结果是未登录
                response.sendError(403);
            } else {
                // 让浏览器向本链接发起一次重定向，此过程去除vtParam，将vt写入cookie
                redirectToSelf(vtParam);
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }


    // 将所有请求参数重新拼接成queryString
    private String makeQueryString(HttpServletRequest request) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
// ? a= 1&a=2&b=xx [1,2][] ?a=1&a=2&b=xxx
        Enumeration<String> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
            String paraName = paraNames.nextElement();
            String[] paraVals = request.getParameterValues(paraName);
            for (String paraVal : paraVals) {
                builder.append("&").append(paraName).append("=").append(URLEncoder.encode(paraVal, "utf-8"));
            }
        }

        if (builder.length() > 0) {
            builder.replace(0, 1, "?");
        }

        return builder.toString();
    }

    // 从请求参数中解析vt
    private String pasreVtParam(HttpServletRequest request) {
        final String PARANAME = "paragram_vt=";
        String qstr = request.getQueryString();
        // a=2&b=xxx&__vt_param__=xxxxxxx
        if (qstr == null) {
            return null;
        }
        int index = qstr.indexOf(PARANAME);
        if (index > -1) {
            return qstr.substring(index + PARANAME.length());
        } else {
            return null;
        }
    }

    //重定向
    private void redirectToSelf(String vt) throws IOException {
        final String PARANAME = Constans.PARAGRAM_VT;
        // 此处拼接redirect的url，去除vt参数部分
        StringBuffer location = request.getRequestURL();

        String qstr = request.getQueryString();
        int index = qstr.indexOf(PARANAME);
        //http://www.sys1.com:8081/test/tt?a=2&b=xxx
        if (index > 0) { // 还有其它参数，para1=param1&param2=param2&__vt_param__=xxx是最后一个参数
            qstr = "?" + qstr.substring(0, qstr.indexOf(PARANAME) - 1);
        } else { // 没有其它参数 qstr = __vt_param__=xxx
            qstr = "";
        }
        location.append(qstr);
        Cookie cookie = new Cookie(Constans.COOKIE_SSO, vt);
        cookie.setPath("/");
        cookie.setMaxAge(Constans.COOKIE_SET_TIME);
//        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect(location.toString());
    }
}
