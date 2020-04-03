package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.Donate;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * 捐赠服务类
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_DONATE", cacheManager = "cacheManager")
public interface DonateService {
    /**
     * 通过条件查询
     * @param query
     * @return
     */
    PageTemplate<Donate> getByQuery(BaseQuery query);

    /**
     * 田间
     * @param bookSource
     * @return
     */
    boolean add(Donate bookSource);

    /**
     * 通过id删除
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 通过id获取
     * @param id
     * @return
     */
    Donate getById(Long id);

    boolean edit(Donate bookSource);

    boolean deleteList(List<Long> ids);

    List<Donate> getAll();

    long countDonate();
}
