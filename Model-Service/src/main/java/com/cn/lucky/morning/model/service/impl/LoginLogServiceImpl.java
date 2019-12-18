package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.tool.DateTool;
import com.cn.lucky.morning.model.dao.LoginLogMapper;
import com.cn.lucky.morning.model.domain.LoginLog;
import com.cn.lucky.morning.model.domain.LoginLogExample;
import com.cn.lucky.morning.model.service.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper mapper;

    @Override
    public PageTemplate<LoginLog> getByQuery(BaseQuery query) {

        LoginLogExample exp = new LoginLogExample();
        LoginLogExample.Criteria criteria = exp.createCriteria();

        Date[] ds = DateTool.getDateCondition(query);
        Date start = ds[0];
        Date end = ds[1];

        if (start == null && end != null) {
            criteria.andCreatedLessThanOrEqualTo(new Timestamp(end.getTime()));
        } else if (start != null && end == null) {
            criteria.andCreatedGreaterThanOrEqualTo(new Timestamp(start.getTime()));
        } else if (start != null && end != null) {
            criteria.andCreatedBetween(new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        }
        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }
        if (query.isNotEmpty("loginIp")){
            criteria.andLoginIpEqualTo(query.getString("loginIp"));
        }
        if (query.isNotEmpty("loginType")){
            criteria.andLoginTypeEqualTo(query.getInt("loginType"));
        }

        exp.setOrderByClause("id desc");
        exp.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(exp, mapper, query);
    }

    @Override
    public boolean add(LoginLog log) {
        return mapper.insertSelective(log) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        for (Long id : ids){
            if (!delete(id)){
                return false;
            }
        }
        return true;
    }
}
