package com.example.server2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

/**
 * 加载动态配置信息
 * @ClassName:DynamicConfig
 * @author:Wh
 * @date: 2017年9月14日 下午3:38:17
 */
@Configuration
public class DynamicConfig {
    //资源信息元数据:PropertySource包含name和泛型，一份资源信息存在唯一的name以及对应泛型数据，在这里设计为泛型表明可拓展自定.PropertySource在集合中的唯一性只能去看name
    public static final String DYNAMIC_CONFIG_NAME = "dynamic_config";
    @Autowired
    AbstractEnvironment environment;

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(new DynamicLoadPropertySource(DYNAMIC_CONFIG_NAME, null));
    }
}