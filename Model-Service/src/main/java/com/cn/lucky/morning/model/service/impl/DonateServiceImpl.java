package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.DonateMapper;
import com.cn.lucky.morning.model.domain.Donate;
import com.cn.lucky.morning.model.domain.DonateExample;
import com.cn.lucky.morning.model.service.DonateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DonateServiceImpl implements DonateService {
    @Resource
    private DonateMapper mapper;

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
        return mapper.insertSelective(data) > 0;
    }

    @Override
    public boolean delete(Long id) {
        Donate data = getById(id);
        if (data == null) {
            return true;
        }
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Donate getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(Donate data) {
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
        DonateExample example = new DonateExample();
        example.setOrderByClause("id desc");
        List<Donate> list = mapper.selectByExample(example);
        return list;
    }

    @Override
    public long countDonate() {
        return mapper.countByExample(new DonateExample());
    }
}
