package com.example.client.redis.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description Redis配置  <br/>
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }


    /**
     * 实例化 RedisTemplate 对象
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> getRedisTemplate() {
        JedisConnectionFactory factory = getConnectionFactory();
//        RedisTemplate<String, Object> template = new StringRedisTemplate(factory);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        ////如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setStringSerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        //开启事务
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(factory);
        return template;
    }
}
