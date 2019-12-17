package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.dao.DonateMapper;
import com.cn.lucky.morning.model.domain.Donate;
import com.cn.lucky.morning.model.domain.DonateExample;
import com.cn.lucky.morning.model.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DonateServiceImpl implements DonateService {
    @Resource
    private DonateMapper mapper;
    @Autowired
    private CacheService cacheService;

    @Override
    public PageTemplate<Donate> getByQuery(BaseQuery query) {
        DonateExample example = new DonateExample();
        DonateExample.Criteria criteria = example.createCriteria();

        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }
        example.setOrderByClause("id desc");
        example.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(example, mapper, query);
    }

    @Override
    public boolean add(Donate data) {
        cacheService.del(Const.cache.DONATE_ID + "all");
        return mapper.insertSelective(data) > 0;
    }

    @Override
    public boolean delete(Long id) {
        Donate data = getById(id);
        if (data == null) {
            return true;
        }
        cacheService.del(Const.cache.DONATE_ID + id);
        cacheService.del(Const.cache.DONATE_ID + "all");
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Donate getById(Long id) {
        Donate data = (Donate) cacheService.get(Const.cache.DONATE_ID + id);
        if (data == null) {
            data = mapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.DONATE_ID + id, data, Const.cache.DONATE_ID_TTL);
        }
        return data;
    }

    @Override
    public boolean edit(Donate data) {
        cacheService.del(Const.cache.DONATE_ID + "all");
        cacheService.del(Const.cache.DONATE_ID + data.getId());
        return mapper.updateByPrimaryKeySelective(data) > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        for (Long id : ids) {
            if (id != null) {
                if (!delete(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Donate> getAll() {
        List<Donate> list = (List<Donate>) cacheService.get(Const.cache.DONATE_ID + "all");
        if (list == null) {
            DonateExample example = new DonateExample();
            example.setOrderByClause("id desc");
            list = mapper.selectByExample(example);
            cacheService.set(Const.cache.DONATE_ID + "all", list, Const.cache.DONATE_ID_TTL);
        }
        return list;
    }
}
