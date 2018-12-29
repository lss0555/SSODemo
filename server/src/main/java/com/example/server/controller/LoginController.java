package com.example.server.controller;

import com.alibaba.fastjson.JSON;
import com.example.common.constance.Constans;
import com.example.common.utils.CookieUtil;
import com.example.common.utils.StringUtils;
import com.example.server.model.Result;
import com.example.server.model.User;
import com.example.server.redis.inter.TokenManagerInter;
import com.example.server.service.inter.UserLoginServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description 测试
 * @Author lss0555
 * @Date 2018/12/26/026 10:20
 **/
@Controller
public class LoginController {
    @Resource
    UserLoginServiceInter userLoginServiceInter;

    @Resource
    TokenManagerInter tokenManagerInter;

    @Resource
    HttpServletRequest request;

    @Resource
    HttpServletResponse response;

    /**
     * @Description  登录页面`
     **/
    @GetMapping("/login")
    public String login(String backUrl, ModelMap map) throws Exception{
        String cookie = CookieUtil.getCookie(Constans.COOKIE_SSO, request);
        if(cookie==null){
            map.put("backUrl", backUrl);
            return "login";
        }else {
            if (backUrl != null) {
                response.sendRedirect(StringUtils.appendUrlParameter(backUrl, Constans.PARAGRAM_VT, cookie));
                return null;
            } else {
                User user = tokenManagerInter.getUserInfo(cookie);
                if(user!=null){
                    map.put("user", user);
                }
                map.put("vt", cookie);
                return "loginSuccess";
            }
        }
    }

    /**
     * @Description  用户登录接口
     **/
    @PostMapping("/userLogin")
    @ResponseBody
    public Result<User> userLogin(User user){
        return userLoginServiceInter.login(user);
    }

    @GetMapping("/loginOut")
    public ModelAndView loginout(){
        ModelAndView view = new ModelAndView("loginOut");
        String cookie = CookieUtil.getCookie(Constans.COOKIE_SSO, request);
        if(cookie!=null&&!cookie.equals("")){
            tokenManagerInter.deleteToken(cookie);
        }
        CookieUtil.deleteCookie(Constans.COOKIE_SSO,response,"/");
        return view;
    }

    @GetMapping("/getCk")
    @ResponseBody
    public String getCk(){
        return "hello world";
    }

    @CrossOrigin
    @GetMapping("/getCk1")
    @ResponseBody
    public String getCk1(){
        return "hello world1";
    }

    @GetMapping("/getCk2")
    @ResponseBody
    public String getCk2(String callback){
        String result=callback+"("+ JSON.toJSONString("hello world2")+")";
        return result;
    }
}
