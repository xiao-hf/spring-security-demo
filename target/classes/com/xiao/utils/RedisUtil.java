package com.xiao.utils;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    public void set(String key, Object val, long exp, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, val, exp, unit);
    }
    public void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }
    public boolean contains(String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }
    public void expire(String key, long exp, TimeUnit unit) {
        redisTemplate.expire(key, exp ,unit);
    }
    public void del(String key) {
        redisTemplate.delete(key);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public Set<String> getKeys() {
        return redisTemplate.keys("*");
    }
    public Long getExp(String key) {
        return redisTemplate.getExpire(key);
    }
}