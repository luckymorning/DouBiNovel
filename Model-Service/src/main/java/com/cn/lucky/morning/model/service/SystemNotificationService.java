package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.SystemNotification;

import java.util.List;

public interface SystemNotificationService {
    PageTemplate<SystemNotification> getByQuery(BaseQuery query);

    boolean add(SystemNotification data);

    boolean delete(Long id);

    SystemNotification getById(Long id);

    boolean edit(SystemNotification data);

    boolean deleteList(List<Long> ids);

    SystemNotification findLastNotification();
}
