package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.LoginLog;

import java.util.List;

public interface LoginLogService {

    PageTemplate<LoginLog> getByQuery(BaseQuery query);

    boolean add(LoginLog log);

    boolean delete(Long id);

    boolean deleteList(List<Long> ids);
}
