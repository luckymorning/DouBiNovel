package com.cn.lucky.morning.model.web.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache implements Cache<Object,Object> {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Object get(Object s) throws CacheException {
        return redisTemplate.opsForValue().get(s);
    }

    @Override
    public Object put(Object s, Object o) throws CacheException {
        redisTemplate.opsForValue().set(s,o);
        return o;
    }

    @Override
    public Object remove(Object s) throws CacheException {
        Object result = get(s);
        redisTemplate.delete(s);
        return result;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<Object> keys() {
        return redisTemplate.keys("*");
    }

    @Override
    public Collection<Object> values() {
        return redisTemplate.opsForValue().multiGet(keys());
    }
}
