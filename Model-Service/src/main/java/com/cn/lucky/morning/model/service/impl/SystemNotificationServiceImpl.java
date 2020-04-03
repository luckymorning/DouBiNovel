package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.SystemNotificationMapper;
import com.cn.lucky.morning.model.domain.SystemNotification;
import com.cn.lucky.morning.model.domain.SystemNotificationExample;
import com.cn.lucky.morning.model.service.SystemNotificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemNotificationServiceImpl implements SystemNotificationService {
    @Resource
    private SystemNotificationMapper mapper;

    @Override
    public PageTemplate<SystemNotification> getByQuery(BaseQuery query) {
        SystemNotificationExample example = new SystemNotificationExample();
        SystemNotificationExample.Criteria criteria = example.createCriteria();

        if (query.isNotEmpty("title")) {
            criteria.andTitleLike("%" + query.getString("title") + "%");
        }
        if (query.isNotEmpty("order")) {
            example.setOrderByClause(query.getString("order"));
        } else {
            example.setOrderByClause("id desc");
        }
        example.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(example, mapper, query);
    }

    @Override
    public boolean add(SystemNotification data) {
        return mapper.insertSelective(data) > 0;
    }

    @Override
    public boolean delete(Long id) {
        SystemNotification notification = getById(id);
        if (notification == null) {
            return true;
        }
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public SystemNotification getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(SystemNotification data) {
        return mapper.updateByPrimaryKeySelective(data) > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        for (Long id : ids) {
            if (!delete(id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SystemNotification findLastNotification() {
        SystemNotification notification = null;
        SystemNotificationExample example = new SystemNotificationExample();
        example.setOrderByClause("id desc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<SystemNotification> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            notification = list.get(0);
        }
        return notification;
    }
}
