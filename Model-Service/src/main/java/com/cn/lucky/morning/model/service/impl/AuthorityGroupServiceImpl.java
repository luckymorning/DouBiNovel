package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.dao.AuthorityGroupMapper;
import com.cn.lucky.morning.model.domain.Authority;
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
    private CacheService cacheService;
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
        cacheService.del(Const.cache.AUTHORITY_GROUP_ID+"all");
        return authorityGroupMapper.insertSelective(authorityGroup) > 0;
    }

    @Override
    public boolean delete(Long id) {
        AuthorityGroup authorityGroup = getById(id);
        if (authorityGroup == null){
            return true;
        }
        authorityService.deleteByAuthorityGroupId(id);
        cacheService.del(Const.cache.AUTHORITY_GROUP_ID+id);
        cacheService.del(Const.cache.AUTHORITY_GROUP_ID+"all");
        return authorityGroupMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public AuthorityGroup getById(Long id) {
        AuthorityGroup authorityGroup = (AuthorityGroup) cacheService.get(Const.cache.AUTHORITY_GROUP_ID + id);
        if (authorityGroup == null) {
            authorityGroup = authorityGroupMapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.AUTHORITY_GROUP_ID+id,authorityGroup,Const.cache.AUTHORITY_ID_GROUP_TTL);
        }
        return authorityGroup;
    }

    @Override
    public boolean edit(AuthorityGroup authorityGroup) {
        cacheService.del(Const.cache.AUTHORITY_GROUP_ID+"all");
        cacheService.del(Const.cache.AUTHORITY_GROUP_ID+authorityGroup.getId());
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
        List<AuthorityGroup> list = (List<AuthorityGroup>) cacheService.get(Const.cache.AUTHORITY_GROUP_ID+"all");
        if (list == null){
            AuthorityGroupExample example = new AuthorityGroupExample();
            list = authorityGroupMapper.selectByExample(example);
            cacheService.set(Const.cache.AUTHORITY_GROUP_ID+"all",list,Const.cache.AUTHORITY_ID_GROUP_TTL);
        }
        return list;
    }
}
