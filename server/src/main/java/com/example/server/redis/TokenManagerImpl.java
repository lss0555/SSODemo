package com.example.server.redis;
import com.alibaba.fastjson.JSON;
import com.example.common.constance.Constans;
import com.example.server.model.User;
import com.example.server.redis.inter.TokenManagerInter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
/**
 * Description  token管理  <br/>
 */
@Service
public class TokenManagerImpl implements TokenManagerInter {
    private static final Logger logger = LoggerFactory.getLogger(TokenManagerImpl.class);
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    HttpSession session;

    /**
     * 创建一个token关联上指定用户
     * @return 生成的token
     */
    @Override
    public boolean createToken(User user) {
        try {
            redisTemplate.opsForValue().set(user.getUuid(), JSON.toJSONString(user));
            //TODO 时间设置
            redisTemplate.expire(user.getUuid(), Constans.COOKIE_SET_TIME, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查token是否有效
     * @param token
     * @return 是否有效
     */
    @Override
    public boolean checkToken(final String token) {
        if (token == null) {
            return false;
        }
        try {
            Boolean hasKey = redisTemplate.hasKey(token);
            if (hasKey) {
                //延长时长
                //TODO 时间设置
                redisTemplate.expire(token, Constans.COOKIE_SET_TIME, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param token 获取登录用户的token
     */
    @Override
    public User getUserInfo(String token) {
        try {
            String s = redisTemplate.opsForValue().get(token);
            return JSON.parseObject(s,User.class);
        }catch (Exception e){
            System.out.println("redis获取数据失败,"+e);
            return null;
        }
    }

    /**
     * 清除token
     * @param token 登录用户的token
     */
    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}
