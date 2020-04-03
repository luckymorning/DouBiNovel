package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.mvc.MvcResult;

/**
 * 书籍分析服务类
 *
 * @author lucky_morning
 */
public interface BookAnalysisService {
    /**
     * 通过名称搜索
     *
     * @param name 书籍名称
     * @return 查询结果
     */
    MvcResult searchByName(String name);

    /**
     * 通过链接加载书籍详情
     *
     * @param url 书籍详情链接
     * @return 查询结果
     */
    MvcResult loadBookDetail(String url);

    /**
     * 通过链接加载书籍内容
     *
     * @param url 书籍内容链接
     * @return 查询结果
     */
    MvcResult loadBookContent(String url);
}
