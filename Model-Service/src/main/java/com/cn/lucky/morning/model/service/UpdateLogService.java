package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.UpdateLog;

import java.util.List;

public interface UpdateLogService {
    PageTemplate<UpdateLog> getByQuery(BaseQuery query);

    boolean add(UpdateLog updateLog);

    boolean delete(Long id);

    UpdateLog getById(Long id);

    boolean edit(UpdateLog updateLog);

    boolean deleteList(List<Long> ids);

    List<UpdateLog> findListLog();

    long countUpdateLogs();
}
