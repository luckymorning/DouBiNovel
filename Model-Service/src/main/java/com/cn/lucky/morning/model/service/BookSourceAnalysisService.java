package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookSource;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

/**
 * 通过书源网络获取书籍
 *
 * @author lucky_morning
 */
public interface BookSourceAnalysisService {

    /**
     * 通过名称查找书籍
     *
     * @param name       书籍名称
     * @param bookSource 书源信息
     * @return 查询结果
     */
    @Async
    Future<MvcResult> searchByName(String name, BookSource bookSource);

    /**
     * 获取书籍详细信息页
     *
     * @param url        详情链接
     * @param bookSource 书源信息
     * @return 详情结果
     */
    @Async
    Future<MvcResult> loadBookInfo(String url, BookSource bookSource);

    /**
     * 获取章节内容
     *
     * @param url        章节内容链接
     * @param bookSource 书源信息
     * @return 获取章节内容
     */
    @Async
    Future<MvcResult> loadContent(String url, BookSource bookSource);
}
