package com.example.server2.config;
import com.example.server2.model.ClientSystem;
import com.example.server2.model.LoginUser;
import com.example.server2.service.CaptchaAuthenticationHandler;
import com.example.server2.service.CaptchaPreLoginHandler;
import com.example.server2.service.UserSerializer;
import com.example.server2.service.inter.IAuthenticationHandler;
import com.example.server2.service.inter.IPreLoginHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 应用配置信息
 *
 * @author Administrator
 */
@Service("config")
public class Config implements ConfigServiceInter {
    @Resource
    CaptchaAuthenticationHandler captchaAuthenticationHandler;
    @Resource
    CaptchaPreLoginHandler captchaPreLoginHandler;
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private ResourceLoader resourceLoader;

    private IAuthenticationHandler authenticationHandler; // 鉴权处理器

    // 用户信息转换序列化实现
    private UserSerializer userSerializer;

    private IPreLoginHandler preLoginHandler; // 登录前预处理器
    private String loginViewName = "/login"; // 登录页面视图名称

    private int tokenTimeout = 30; // 令牌有效期，单位为分钟，默认30分钟

    private boolean secureMode = false; // 是否必须为https

    private int autoLoginExpDays = 365; // 自动登录状态有效期限，默认一年

    private List<ClientSystem> clientSystems = new ArrayList<ClientSystem>();

    /**
     * 重新加载配置，以支持热部署
     *
     * @throws Exception
     */
    public void refreshConfig() throws Exception {
        Map<String, Object> map = DynamicLoadPropertySource.dynamicLoadMapInfo();
        // vt有效期参数
        String configTokenTimeout = (String) map.get("tokenTimeout");
        if (configTokenTimeout != null) {
            try {
                tokenTimeout = Integer.parseInt(configTokenTimeout);
                logger.debug("config.properties设置tokenTimeout={}", tokenTimeout);
            } catch (NumberFormatException e) {
                logger.warn("tokenTimeout参数配置不正确");
            }
        }

        // 是否仅https安全模式下运行
        String configScureMode = map.get("secureMode").toString();
        if (configScureMode != null) {
            secureMode = Boolean.parseBoolean(configScureMode);
            logger.debug("config.properties设置secureMode={}", secureMode);
        }

        // 自动登录有效期
        String configAutoLoginExpDays = map.get("autoLoginExpDays").toString();
        if (configAutoLoginExpDays != null) {
            try {
                autoLoginExpDays = Integer.parseInt(configAutoLoginExpDays);
                logger.debug("config.properties设置autoLoginExpDays={}", autoLoginExpDays);
            } catch (NumberFormatException e) {
                logger.warn("autoLoginExpDays参数配置不正确");
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        logger.info("data{};currentTime:{}", map.get("a"), sdf.format(new Date()));
        clientSystems.clear();
        ClientSystem clientSystem = new ClientSystem();
        clientSystem.setId(map.get("test1.id") == null ? "" : map.get("test1.id").toString());
        clientSystem.setName(map.get("test1.name") == null ? "" : map.get("test1.name").toString());
        clientSystem.setBaseUrl(map.get("test1.baseUrl").toString());
        clientSystem.setHomeUri(map.get("test1.homeUri").toString());
        clientSystem.setInnerAddress(map.get("test1.innerAddress").toString());
        clientSystems.add(clientSystem);
        ClientSystem clientSystem1 = new ClientSystem();
        clientSystem.setId(map.get("test2.id") == null ? "" : map.get("test2.id").toString());
        clientSystem.setName(map.get("test2.name") == null ? "" : map.get("test2.name").toString());
        clientSystem.setBaseUrl(map.get("test2.baseUrl").toString());
        clientSystem.setHomeUri(map.get("test2.homeUri").toString());
        clientSystem.setInnerAddress(map.get("test2.innerAddress").toString());
        clientSystems.add(clientSystem);
        clientSystems.add(clientSystem1);
    }

    // 加载客户端系统配置列表
    @SuppressWarnings("unchecked")
    private void loadClientSystems() throws Exception {

    }

    /**
     * 应用停止时执行，做清理性工作，如通知客户端logout
     */
    @PreDestroy
    public void destroy() {
        for (ClientSystem clientSystem : clientSystems) {
            clientSystem.noticeShutdown();
        }
    }

    /**
     * 获取当前鉴权处理器
     *
     * @return
     */
    public IAuthenticationHandler getAuthenticationHandler() {
        return authenticationHandler;
    }

    @PostConstruct
    public void setAuthenticationHandler() {
        this.authenticationHandler = captchaAuthenticationHandler;
    }

    /**
     * 获取登录前预处理器
     *
     * @return
     */
    public IPreLoginHandler getPreLoginHandler() {
        return preLoginHandler;
    }

    @PostConstruct
    public void setPreLoginHandler() {
        this.preLoginHandler = captchaPreLoginHandler;
    }

    /**
     * 获取登录页面视图名称
     *
     * @return
     */
    public String getLoginViewName() {
        return loginViewName;
    }

    @PostConstruct
    public void setLoginViewName() {
        this.loginViewName = "login2";
    }

    /**
     * 获取令牌有效期，单位为分钟
     *
     * @return
     */
    public int getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(int tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    /**
     * 客户端系统列表
     *
     * @return
     */
    public List<ClientSystem> getClientSystems() {
        return clientSystems;
    }

    public void setClientSystems(List<ClientSystem> clientSystems) {
        this.clientSystems = clientSystems;
    }

    /**
     * 获取指定用户的可用系统列表
     *
     * @param loginUser
     * @return
     * @throws Exception
     */
    public List<ClientSystem> getClientSystems(LoginUser loginUser) throws Exception {
        Set<String> authedSysIds = getAuthenticationHandler().authedSystemIds(loginUser);

        // null表示允许全部
        if (authedSysIds == null) {
            return clientSystems;
        }

        List<ClientSystem> auhtedSystems = new ArrayList<ClientSystem>();
        for (ClientSystem clientSystem : clientSystems) {
            if (authedSysIds.contains(clientSystem.getId())) {
                auhtedSystems.add(clientSystem);
            }
        }

        return auhtedSystems;
    }


    @Override
    public boolean isSecureMode() {
        return secureMode;
    }

    public int getAutoLoginExpDays() {
        return autoLoginExpDays;
    }

    public UserSerializer getUserSerializer() {
        return userSerializer;
    }

    /**
     * @param userSerializer
     */
    public void setUserSerializer(UserSerializer userSerializer) {
        this.userSerializer = userSerializer;
    }

}
