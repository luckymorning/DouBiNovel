package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.AuthorityGroupMapper;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.domain.AuthorityGroupExample;
import com.cn.lucky.morning.model.service.AuthorityGroupService;
import com.cn.lucky.morning.model.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorityGroupServiceImpl implements AuthorityGroupService {
    @Resource
    private AuthorityGroupMapper authorityGroupMapper;
    @Autowired
    private AuthorityService authorityService;

    @Override
    public PageTemplate<AuthorityGroup> getByQuery(BaseQuery query) {
        AuthorityGroupExample example = new AuthorityGroupExample();

        AuthorityGroupExample.Criteria criteria = example.createCriteria();
        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }
        example.setOrderByClause("id desc");
        example.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(example, authorityGroupMapper, query);
    }

    @Override
    public boolean add(AuthorityGroup authorityGroup) {
        return authorityGroupMapper.insertSelective(authorityGroup) > 0;
    }

    @Override
    public boolean delete(Long id) {
        AuthorityGroup authorityGroup = getById(id);
        if (authorityGroup == null) {
            return true;
        }
        authorityService.deleteByAuthorityGroupId(id);
        return authorityGroupMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public AuthorityGroup getById(Long id) {
        return authorityGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(AuthorityGroup authorityGroup) {
        return authorityGroupMapper.updateByPrimaryKeySelective(authorityGroup) > 0;
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
    public List<AuthorityGroup> getAll() {
        return authorityGroupMapper.selectByExample(new AuthorityGroupExample());
    }
}
