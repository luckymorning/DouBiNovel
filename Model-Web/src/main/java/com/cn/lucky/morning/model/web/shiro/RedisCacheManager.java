package com.cn.lucky.morning.model.web.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisCacheManager implements CacheManager {
    @Resource
    private RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return (Cache<K, V>) redisCache;
    }
}
