package com.wbm.forum.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author：Ming
 * @Date: 2022/11/10 1:19
 */
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }
    public void set(String key,String value,Long expireTime){
        stringRedisTemplate.opsForValue().set(key,value,expireTime,TimeUnit.MINUTES);
    }
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
    public void deleteByKeys(String key) {
        Set<String> keys = stringRedisTemplate.keys(key+"*");
        stringRedisTemplate.delete(keys);
    }
}

