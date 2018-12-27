package com.example.client.redis.inter;

import com.example.client.model.User;

/**
 * Description  token管理  <br/>
 */
public interface TokenManagerInter {
    /**
     * 创建一个token关联上指定用户
     * @return 生成的token
     */
     boolean createToken(User user);

    /**
     * 检查token是否有效
     * @param token
     * @return 是否有效
     */
     boolean checkToken(String token);

    /**
     * 清除token
     */
     void deleteToken(String token);

    /**
     * 清除token
     * @param token 登录用户的token
     */
    User getUserInfo(String token);
}
