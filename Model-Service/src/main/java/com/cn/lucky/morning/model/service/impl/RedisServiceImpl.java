package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate template;

    @Override
    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }
    }

    @Override
    public void removePattern(String pattern) {
        Set<String> keys = this.template.keys(pattern);
        if (keys.size() > 0) {
            this.template.delete(keys);
        }
    }

    @Override
    public void remove(String key) {
        if (this.exists(key)) {
            this.template.delete(key);
        }
    }

    @Override
    public boolean exists(String key) {
        return this.template.hasKey(key);
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> operations = this.template.opsForValue();
        return (String) operations.get(key);
    }

    @Override
    public boolean set(String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = this.template.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String key, String value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = this.template.opsForValue();
            operations.set(key, value);
            this.template.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> range(String key, Long start, Long end) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.range(key, start, end);
    }

    @Override
    public void trim(String key, Long start, Long end) {
        ListOperations<String, String> operations = this.template.opsForList();
        operations.trim(key, start, end);
    }

    @Override
    public Long size(String key) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.size(key);
    }

    @Override
    public Long leftPush(String key, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.leftPush(key, value);
    }

    @Override
    public Long leftPushAll(String key, String... values) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.leftPushAll(key, values);
    }

    @Override
    public Long leftPushIfPresent(String key, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.leftPushIfPresent(key, value);
    }

    @Override
    public Long leftPush(String key, String pivot, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.leftPush(key, pivot, value);
    }

    @Override
    public Long rightPush(String key, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPush(key, value);
    }

    @Override
    public Long rightPushAll(String key, String... values) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPushAll(key, values);
    }

    @Override
    public Long rightPushIfPresent(String key, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPushIfPresent(key, value);
    }

    @Override
    public Long rightPush(String key, String pivot, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPush(key, pivot, value);
    }

    @Override
    public void set(String key, Long index, String value) {
        ListOperations<String, String> operations = this.template.opsForList();
        operations.set(key, index, value);
    }

    @Override
    public Long remove(String key, Long count, Object value) {
        ListOperations<String, String> operations = this.template.opsForList();
        if (this.exists(key)) {
            return operations.remove(key, count, value);
        }
        return null;
    }

    @Override
    public String index(String key, Long index) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.index(key, index);
    }

    @Override
    public String leftPop(String key) {
        ListOperations<String, String> operations = this.template.opsForList();
        if (this.exists(key)) {
            return operations.leftPop(key);
        }
        return null;
    }

    @Override
    public String leftPop(String key, Long timeout, TimeUnit unit) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.leftPop(key, timeout, unit);
    }

    @Override
    public String rightPop(String key) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPop(key);
    }

    @Override
    public String rightPop(String key, Long timeout, TimeUnit unit) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPop(key, timeout, unit);
    }

    @Override
    public String rightPopAndLeftPush(String sourceKey, String destinationKey) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPopAndLeftPush(sourceKey, destinationKey);
    }

    @Override
    public String rightPopAndLeftPush(String sourceKey, String destinationKey, Long timeout, TimeUnit unit) {
        ListOperations<String, String> operations = this.template.opsForList();
        return operations.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }
}
