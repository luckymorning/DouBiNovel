package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityGroup;

import java.util.List;
import java.util.Map;

public interface AuthorityService {

    Map<AuthorityGroup, List<Authority>> getAuthority();

    List<Authority> getByIds(List<Long> ids);

    boolean deleteList(List<Long> asList);

    boolean delete(Long id);

    boolean edit(Authority authority);

    boolean add(Authority authority);

    PageTemplate<Authority> getByQuery(BaseQuery query);

    Authority getById(Long id);

    boolean getByCode(String code);

    boolean deleteByAuthorityGroupId(Long authorityGroupId);
}
