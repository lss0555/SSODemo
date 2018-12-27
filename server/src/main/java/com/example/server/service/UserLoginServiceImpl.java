package com.example.server.service;

import com.example.common.constance.Constans;
import com.example.server.dao.UserDaoMapper;
import com.example.server.model.Result;
import com.example.server.model.User;
import com.example.server.redis.inter.TokenManagerInter;
import com.example.server.service.inter.UserLoginServiceInter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Description 用户登录实现类
 * @Author lss0555
 * @Date 2018/12/26/026 11:31
 **/
@Service
public class UserLoginServiceImpl implements UserLoginServiceInter {
    @Resource
    UserDaoMapper userDao;

    @Resource
    TokenManagerInter tokenManagerInter;

    @Resource
    HttpServletResponse response;
    @Resource
    HttpServletRequest request;

    @Resource
    HttpSession session;

    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        if(user.getUsername()==null||user.getUsername().equals("")||user.getPassword()==null||user.getPassword().equals("")){
            result.setErrorcode(-1);
            result.setErrorMsg("请输入用户名或者密码");
            return  result;
        }
        User u=null;
        try {
            u =userDao.login(user);
            if(u!=null){
                //登录成功
                result.setErrorcode(0);
                result.setModel(user);
                //设置cookie
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                Cookie cookie = new Cookie(Constans.COOKIE_SSO, uuid);
                cookie.setMaxAge(Constans.COOKIE_SET_TIME);//设置cookie为一分钟
                cookie.setPath("/");
//                cookie.setSecure(true);
                response.addCookie(cookie);
                //存入redis中
                user.setUuid(uuid);
                System.out.println("创建uuid:"+uuid);
                boolean creatToken = tokenManagerInter.createToken(user);
                System.out.println("创建token:"+creatToken);
            }else {
                result.setErrorcode(-3);
                result.setErrorMsg("登录失败,请检查用户名或者密码是否正确");
            }
        }catch (Exception e){
            result.setErrorMsg("登录失败,"+e.getMessage());
            result.setErrorcode(-2);
            return result;
        }
        return result;
    }
}
