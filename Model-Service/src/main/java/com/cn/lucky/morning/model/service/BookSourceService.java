package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.BookSource;
import org.springframework.cache.annotation.*;

import java.util.List;

/**
 * 书源服务类
 *
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_BOOK_SOURCE", cacheManager = "cacheManager")
public interface BookSourceService {

    /**
     * 通过条件获取
     *
     * @param query
     * @return
     */
    PageTemplate<BookSource> getByQuery(BaseQuery query);

    /**
     * 添加
     *
     * @param bookSource
     * @return
     */
    boolean add(BookSource bookSource);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "'all'"), @CacheEvict(key = "'count'")})
    boolean delete(Long id);

    /**
     * 通过id获取
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    BookSource getById(Long id);

    /**
     * 编辑
     *
     * @param bookSource
     * @return
     */
    @CachePut(key = "#bookSource.id")
    boolean edit(BookSource bookSource);

    /**
     * 通过列表删除
     *
     * @param ids
     * @return
     */
    @CacheEvict(allEntries = true)
    boolean deleteList(List<Long> ids);

    /**
     * 获取全部
     *
     * @return
     */
    @CachePut(key = "'all'")
    List<BookSource> getAll();

    /**
     * 计算书源数量
     *
     * @return
     */
    @CachePut(key = "'count'")
    long countBookSource();
}
