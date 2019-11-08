package com.plant.smartwater.box.common.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService {

    /**
     * 不设置过期时间
     */
    void setRedisTemplate(RedisTemplate<String, Object> redisTemplate);

    boolean expire(String key, long time);
    long getExpire(String key);
    boolean hasKey(String key);
    void del(String... key);
    Object get(String key);
    boolean set(String key, Object value);
    boolean set(String key, Object value, long time);
    long incr(String key, long delta);
    long decr(String key, long delta);
    Object hget(String key, String item);
    Map<Object,Object> hmget(String key);
    boolean hmset(String key, Map<String, Object> map);
    boolean hmset(String key, Map<String, Object> map, long time);
    boolean hset(String key, String item, Object value);
    boolean hset(String key, String item, Object value, long time);
    void hdel(String key, Object... item);
    boolean hHasKey(String key, String item);
    double hincr(String key, String item, double by);
    Set<Object> sGet(String key);
    boolean sHasKey(String key, Object value);
    long sSet(String key, Object... values);
    long sSetAndTime(String key, long time, Object... values);
    long sGetSetSize(String key);
    List<Object> lGet(String key, long start, long end);
    long lGetListSize(String key);
    Object lGetIndex(String key, long index);
    boolean lSet(String key, Object value);
    boolean lSet(String key, Object value, long time);
    boolean lSet(String key, List<Object> value);
    boolean lSet(String key, List<Object> value, long time);
    boolean lUpdateIndex(String key, long index, Object value);
    long lRemove(String key, long count, Object value);
}
