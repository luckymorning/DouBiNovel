package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.dao.AuthorityGroupMapper;
import com.cn.lucky.morning.model.dao.AuthorityMapper;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityExample;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.domain.AuthorityGroupExample;
import com.cn.lucky.morning.model.service.AuthorityService;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityGroupMapper authorityGroupMapper;

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private CacheService cacheService;

    @Override
    public Map<AuthorityGroup, List<Authority>> getAuthority() {
        // TODO 加入缓存
        AuthorityGroupExample agexp = new AuthorityGroupExample();

        List<AuthorityGroup> aglist = authorityGroupMapper.selectByExample(agexp);

        AuthorityExample aexp = new AuthorityExample();
        List<Authority> alist = authorityMapper.selectByExample(aexp);

        Map<AuthorityGroup, List<Authority>> rst = new TreeMap<>(new Comparator<AuthorityGroup>() {

            @Override
            public int compare(AuthorityGroup o1, AuthorityGroup o2) {
                return (int) (o1.getId() - o2.getId());
            }

        });
        for (AuthorityGroup ag : aglist) {
            List<Authority> author = Lists.newArrayList();
            for (Authority a : alist) {
                if (Objects.equal(a.getGroupId(), ag.getId()))
                    author.add(a);
            }
            rst.put(ag, author);
        }
        return rst;
    }

    @Override
    public List<Authority> getByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        List<Authority> list = Lists.newArrayList();
        for (Long id : ids) {
            Authority auth = getById(id);
            list.add(auth);
        }
        return list;
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
    public boolean delete(Long id) {
        Authority authority = getById(id);
        if (authority == null) {
            return true;
        }
        cacheService.del(Const.cache.AUTHORITY_ID + id);
        return authorityMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean edit(Authority authority) {
        cacheService.del(Const.cache.AUTHORITY_ID + authority.getId());
        return authorityMapper.updateByPrimaryKeySelective(authority) > 0;
    }

    @Override
    public boolean add(Authority authority) {
        return authorityMapper.insertSelective(authority) > 0;
    }

    @Override
    public PageTemplate<Authority> getByQuery(BaseQuery query) {
        AuthorityExample example = new AuthorityExample();

        AuthorityExample.Criteria criteria = example.createCriteria();
        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }
        if (query.isNotEmpty("groupId")) {
            criteria.andGroupIdEqualTo(query.getLong("groupId"));
        }
        example.setOrderByClause("id desc");
        example.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(example, authorityMapper, query);
    }

    @Override
    public Authority getById(Long id) {
        Authority authority = (Authority) cacheService.get(Const.cache.AUTHORITY_ID + id);
        if (authority == null) {
            authority = authorityMapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.AUTHORITY_ID + id, authority, Const.cache.AUTHORITY_ID_TTL);
        }
        return authorityMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean getByCode(String code) {
        AuthorityExample example = new AuthorityExample();
        example.createCriteria().andCodeEqualTo(code);
        List<Authority> authorities = authorityMapper.selectByExample(example);
        if (authorities.size() != 0) {
            return true;
        } else return false;
    }

    @Override
    public boolean deleteByAuthorityGroupId(Long authorityGroupId) {
        AuthorityExample example = new AuthorityExample();
        example.createCriteria().andGroupIdEqualTo(authorityGroupId);
        List<Authority> list = authorityMapper.selectByExample(example);
        if (list != null) {
            for (Authority authority : list) {
                if (!delete(authority.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

}
