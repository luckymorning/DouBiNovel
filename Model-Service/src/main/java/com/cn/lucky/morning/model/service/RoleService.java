package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.Role;

import java.util.List;

public interface RoleService {

    PageTemplate<Role> getByQuery(BaseQuery query);

    boolean add(Role role);

    Role getById(Long id);

    boolean edit(Role role);

    boolean delete(Long id);

    boolean deleteList(List<Long> ids);

    List<Role> findAll(boolean isSuper);
}
