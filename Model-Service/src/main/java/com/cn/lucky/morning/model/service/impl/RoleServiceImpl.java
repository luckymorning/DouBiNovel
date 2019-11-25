package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.tool.DateTool;
import com.cn.lucky.morning.model.dao.RoleMapper;
import com.cn.lucky.morning.model.domain.Role;
import com.cn.lucky.morning.model.domain.RoleExample;
import com.cn.lucky.morning.model.service.RoleService;
import com.google.common.base.Objects;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private CacheService cacheService;

    @Override
    public PageTemplate<Role> getByQuery(BaseQuery query) {

        RoleExample exp = new RoleExample();
        RoleExample.Criteria criteria = exp.createCriteria();
        if (query.isNotEmpty("customerId")) {
            criteria.andBelongCustomeEqualTo(query.getLong("customerId"));
        }


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

        exp.setOrderByClause("id desc");
        exp.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(exp, roleMapper, query);
    }

    @Override
    public boolean add(Role role) {
        return roleMapper.insertSelective(role) > 0;
    }

    @Override
    public Role getById(Long id) {
        Role role = (Role) cacheService.get(Const.cache.ROLE_ID + id);
        if (role != null) {
            return role;
        }
        role = roleMapper.selectByPrimaryKey(id);
        if (role == null) {
            return null;
        }
        cacheService.set(Const.cache.ROLE_ID + id, role, Const.cache.ROLE_ID_TTL);
        return role;
    }

    @Override
    public boolean edit(Role role) {
        cacheService.del(Const.cache.ROLE_ID + role.getId());
        return roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    @Override
    public boolean delete(Long id) {
        Role db = getById(id);

        if (db == null) {
            return true;
        }

        // 超级管理员不能删除
        if (Objects.equal(db.getIsSuper(), Const.role.IS_SUPER)) {
            return false;
        }

        cacheService.del(Const.cache.ROLE_ID + id);
        return roleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        for (int index = 0; index < ids.size(); index++) {
            Long id = ids.get(index);
            if (id != null) {
                Role role = getById(id);
                if (role != null) {
                    // 超级管理员不能删除
                    if (Objects.equal(role.getIsSuper(), Const.role.IS_SUPER)) {
                        return false;
                    }
                } else {
                    ids.remove(index);
                    index--;
                }
            } else {
                ids.remove(index);
                index--;
            }
        }

        for (Long id : ids) {
            cacheService.del(Const.cache.ROLE_ID + id);
            if (roleMapper.deleteByPrimaryKey(id) == 0) {
                return false;
            }
        }

        return true;
    }

}
