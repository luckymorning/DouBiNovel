package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.UpdateLogMapper;
import com.cn.lucky.morning.model.domain.UpdateLog;
import com.cn.lucky.morning.model.domain.UpdateLogExample;
import com.cn.lucky.morning.model.service.UpdateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UpdateLogServiceImpl implements UpdateLogService {
    @Resource
    private UpdateLogMapper mapper;

    @Override
    public PageTemplate<UpdateLog> getByQuery(BaseQuery query) {
        UpdateLogExample example = new UpdateLogExample();
        UpdateLogExample.Criteria criteria = example.createCriteria();

        if (query.isNotEmpty("updateDes")) {
            criteria.andUpdateDesLike("%" + query.getString("updateDes") + "%");
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
    public boolean add(UpdateLog updateLog) {
        return mapper.insertSelective(updateLog) > 0;
    }

    @Override
    public boolean delete(Long id) {
        UpdateLog log = getById(id);
        if (log == null) {
            return true;
        }
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public UpdateLog getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(UpdateLog updateLog) {
        return mapper.updateByPrimaryKeySelective(updateLog) > 0;
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
    public List<UpdateLog> findListLog() {
        UpdateLogExample example = new UpdateLogExample();
        example.setOrderByClause("id desc");
        List<UpdateLog> list = mapper.selectByExample(example);
        return list;
    }

    @Override
    public long countUpdateLogs() {
        return mapper.countByExample(new UpdateLogExample());
    }
}
