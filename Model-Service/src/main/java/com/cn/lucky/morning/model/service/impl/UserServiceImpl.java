package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.tool.DateTool;
import com.cn.lucky.morning.model.dao.UserMapper;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.domain.UserExample;
import com.cn.lucky.morning.model.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private CacheService cacheService;

    @Override
    public PageTemplate<User> getByQuery(BaseQuery query) {

        UserExample exp = new UserExample();
        UserExample.Criteria criteria = exp.createCriteria();

        Date[] ds = DateTool.getDateCondition(query);
        Date start = ds[0];
        Date end = ds[1];

        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }

        if (start == null && end != null) {
            criteria.andCreatedLessThanOrEqualTo(new Timestamp(end.getTime()));
        } else if (start != null && end == null) {
            criteria.andCreatedGreaterThanOrEqualTo(new Timestamp(start.getTime()));
        } else if (start != null && end != null) {
            criteria.andCreatedBetween(new Timestamp(start.getTime()), new Timestamp(end.getTime()));
        }
        exp.setOrderByClause("id desc");
        exp.setPage(query.getCurrent(), query.getSize());

        PageTemplate<User> page = PageTemplate.create(exp, userMapper, query);
        if (page.getList() != null && page.getList().size() > 0) {
            for (User user : page.getList()) {
                user.setPassword(null);
            }
        }
        return page;
    }

    @Override
    public boolean add(User user) {
        cacheService.del(Const.cache.USER_ID + "all");
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public User getById(Long id) {
        User user = (User) cacheService.get(Const.cache.USER_ID + id);
        if (user == null) {
            user = userMapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.USER_ID + id, user, Const.cache.USER_ID_TTL);
        }
        return user;
    }

    @Override
    public User getByPhoneOrCodeOrEmail(String value) {
        UserExample exp = new UserExample();
        exp.createCriteria().andPhoneEqualTo(value);
        exp.or().andCodeEqualTo(value);
        exp.or().andEmailEqualTo(value);
        List<User> list = userMapper.selectByExample(exp);
        User user = (CollectionUtils.isEmpty(list) || list.size() == 0) ? null : list.get(0);
        return user;
    }

    @Override
    public boolean edit(User user) {
        cacheService.del(Const.cache.USER_ID + user.getId());
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public boolean delete(Long id) {
        User db = getById(id);
        if (db == null) {
            return true;
        }
        cacheService.del(Const.cache.USER_ID + id);
        return userMapper.deleteByPrimaryKey(id) > 0;
    }
}
