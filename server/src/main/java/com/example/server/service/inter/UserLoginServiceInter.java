package com.example.server.service.inter;

import com.example.server.model.Result;
import com.example.server.model.User;

/**
 * @Description 用户登录接口
 * @Author lss0555
 **/
public interface UserLoginServiceInter {
    Result<User> login(User user);
}
