package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
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
    @Resource
    private CacheService cacheService;

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
        cacheService.del(Const.cache.UPDATE_LOG_ID+"list");
        return mapper.insertSelective(updateLog) > 0;
    }

    @Override
    public boolean delete(Long id) {
        UpdateLog log = getById(id);
        if (log == null) {
            return true;
        }
        cacheService.del(Const.cache.UPDATE_LOG_ID+"list");
        cacheService.del(Const.cache.UPDATE_LOG_ID + id);
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public UpdateLog getById(Long id) {
        UpdateLog log = (UpdateLog) cacheService.get(Const.cache.UPDATE_LOG_ID + id);
        if (log == null) {
            log = mapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.UPDATE_LOG_ID + id, log, Const.cache.UPDATE_LOG_ID_TTL);
        }
        return log;
    }

    @Override
    public boolean edit(UpdateLog updateLog) {
        cacheService.del(Const.cache.UPDATE_LOG_ID+"list");
        cacheService.del(Const.cache.UPDATE_LOG_ID + updateLog.getId());
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
        List<UpdateLog> list = (List<UpdateLog>) cacheService.get(Const.cache.UPDATE_LOG_ID+"list");
        if (list == null){
            UpdateLogExample example = new UpdateLogExample();
            example.setOrderByClause("id desc");
            list = mapper.selectByExample(example);
            cacheService.set(Const.cache.UPDATE_LOG_ID+"list",list,Const.cache.UPDATE_LOG_ID_TTL);
        }
        return list;
    }

    @Override
    public long countUpdateLogs() {
        return mapper.countByExample(new UpdateLogExample());
    }
}
