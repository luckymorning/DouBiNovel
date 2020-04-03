package com.cn.lucky.morning.model.service;

import okhttp3.Headers;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * 网络请求服务类
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_NETWORK", cacheManager = "cacheManager")
public interface NetWorkService {

    /**
     * get请求
     *
     * @param url 请求连接
     * @return 返回结果
     */
    @Cacheable(key = "#url")
    String get(String url);

    /**
     * post请求
     *
     * @param url    请求链接
     * @param params 请求参数
     * @param key    唯一标志
     * @return 返回结果
     */
    @Cacheable(key = "#url+'_'+#key")
    String post(String url, Map<String, String> params, String key);

    /**
     * get请求
     *
     * @param url     请求连接
     * @param headers 请求头
     * @return 返回结果
     */
    @Cacheable(key = "#url")
    String get(String url, Headers headers);

    /**
     * post请求
     *
     * @param url     请求链接
     * @param params  请求参数
     * @param headers 请求头
     * @param key     唯一标志
     * @return 返回结果
     */
    @Cacheable(key = "#url+'_'+#key")
    String post(String url, Map<String, String> params, Headers headers, String key);

}
