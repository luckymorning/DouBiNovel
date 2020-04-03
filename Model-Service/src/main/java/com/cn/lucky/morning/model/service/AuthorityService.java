package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * 权限服务类
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_AUTHOR", cacheManager = "cacheManager")
public interface AuthorityService {

    Map<AuthorityGroup, List<Authority>> getAuthority();

    @Cacheable(key = "#ids")
    List<Authority> getByIds(List<Long> ids);

    @CacheEvict(allEntries = true)
    boolean deleteList(List<Long> asList);

    @CacheEvict(key = "#id")
    boolean delete(Long id);

    @CachePut(key = "#authority.id")
    boolean edit(Authority authority);

    boolean add(Authority authority);

    PageTemplate<Authority> getByQuery(BaseQuery query);

    @Cacheable(key = "#id")
    Authority getById(Long id);

    @Cacheable(key = "#code")
    boolean getByCode(String code);

    @CacheEvict(key = "#authorityGroupId")
    boolean deleteByAuthorityGroupId(Long authorityGroupId);
}
