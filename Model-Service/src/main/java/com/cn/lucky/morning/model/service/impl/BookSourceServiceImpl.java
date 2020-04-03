package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.BookSourceMapper;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.domain.BookSourceExample;
import com.cn.lucky.morning.model.service.BookSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookSourceServiceImpl implements BookSourceService {
    @Resource
    private BookSourceMapper mapper;

    @Override
    public PageTemplate<BookSource> getByQuery(BaseQuery query) {
        BookSourceExample example = new BookSourceExample();
        BookSourceExample.Criteria criteria = example.createCriteria();

        if (query.isNotEmpty("name")) {
            criteria.andNameLike("%" + query.getString("name") + "%");
        }
        example.setOrderByClause("id desc");
        example.setPage(query.getCurrent(), query.getSize());

        return PageTemplate.create(example, mapper, query);
    }

    @Override
    public boolean add(BookSource bookSource) {
        return mapper.insertSelective(bookSource) > 0;
    }

    @Override
    public boolean delete(Long id) {
        BookSource bookSource = getById(id);
        if (bookSource == null) {
            return true;
        }
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public BookSource getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(BookSource bookSource) {
        return mapper.updateByPrimaryKeySelective(bookSource) > 0;
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
    public List<BookSource> getAll() {
        BookSourceExample example = new BookSourceExample();
        List<BookSource> list = mapper.selectByExample(example);
        if (list == null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public long countBookSource() {
        return mapper.countByExample(new BookSourceExample());
    }
}
