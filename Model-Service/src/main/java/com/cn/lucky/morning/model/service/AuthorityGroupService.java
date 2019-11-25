package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.AuthorityGroup;

import java.util.List;

public interface AuthorityGroupService {
    PageTemplate<AuthorityGroup> getByQuery(BaseQuery query);

    boolean add(AuthorityGroup authorityGroup);

    boolean delete(Long id);

    AuthorityGroup getById(Long id);

    boolean edit(AuthorityGroup authorityGroup);

    boolean deleteList(List<Long> asList);

    List<AuthorityGroup> getAll();
}
