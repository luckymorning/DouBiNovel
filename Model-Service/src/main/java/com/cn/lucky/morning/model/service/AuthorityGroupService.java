package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import org.springframework.cache.annotation.*;

import java.util.List;

/**
 * 权限组服务类
 *
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_AUTHOR_GROUP", cacheManager = "cacheManager")
public interface AuthorityGroupService {
    PageTemplate<AuthorityGroup> getByQuery(BaseQuery query);

    boolean add(AuthorityGroup authorityGroup);

    @CacheEvict(key = "#id")
    boolean delete(Long id);

    @Cacheable(key = "#id")
    AuthorityGroup getById(Long id);

    @Caching(evict = {@CacheEvict(key = "'all'")}, put = {@CachePut(key = "#authorityGroup.id")})
    boolean edit(AuthorityGroup authorityGroup);

    @CacheEvict(allEntries = true)
    boolean deleteList(List<Long> asList);

    List<AuthorityGroup> getAll();
}
