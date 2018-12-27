package com.example.server2.config;

import com.example.server2.model.ClientSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 动态加载配置文件
 *
 * @ClassName:DynamicLoadPropertySource
 * @author:Wh
 * @date: 2017年9月14日 下午3:22:54
 */


public class DynamicLoadPropertySource extends MapPropertySource {

    private static int tokenTimeout = 30; // 令牌有效期，单位为分钟，默认30分钟

    private static boolean secureMode = false; // 是否必须为https

    private static int autoLoginExpDays = 365; // 自动登录状态有效期限，默认一年

    private static List<ClientSystem> clientSystems = new ArrayList<ClientSystem>();
    private static Logger log = LoggerFactory.getLogger(DynamicLoadPropertySource.class);
    private static Map<String, Object> map = new ConcurrentHashMap<String, Object>(64);
    private static ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

    static {
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                map = dynamicLoadMapInfo();
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    /**
     * @param name
     * @param source
     */
    public DynamicLoadPropertySource(String name, Map<String, Object> source) {
        super(name, map);
    }


    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }

    /**
     * //动态获取资源信息
     *
     * @return
     */
    protected static Map<String, Object> dynamicLoadMapInfo() {
        return mockMapInfo();
    }

    /**
     * @return
     */

    private static Map<String, Object> mockMapInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = getProperties();


        return map;
    }

    /**
     * 获取配置文件信息
     *
     * @return
     */
    public static Map<String, Object> getProperties() {

        Properties props = new Properties();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("dynamic_config.yml");
            props.load(in);
            Enumeration<?> en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String property = props.getProperty(key);
                map.put(key, property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}