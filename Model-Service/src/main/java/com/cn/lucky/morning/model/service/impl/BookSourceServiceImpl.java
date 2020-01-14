package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.dao.BookSourceMapper;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.domain.BookSourceExample;
import com.cn.lucky.morning.model.service.BookSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookSourceServiceImpl implements BookSourceService {
    @Resource
    private BookSourceMapper mapper;
    @Autowired
    private CacheService cacheService;

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
        cacheService.del(Const.cache.BOOK_SOURCE_ID + "all");
        return mapper.insertSelective(bookSource) > 0;
    }

    @Override
    public boolean delete(Long id) {
        BookSource bookSource = getById(id);
        if (bookSource == null) {
            return true;
        }
        cacheService.del(Const.cache.BOOK_SOURCE_ID + id);
        cacheService.del(Const.cache.BOOK_SOURCE_ID + "all");
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public BookSource getById(Long id) {
        BookSource bookSource = (BookSource) cacheService.get(Const.cache.BOOK_SOURCE_ID + id);
        if (bookSource == null) {
            bookSource = mapper.selectByPrimaryKey(id);
            cacheService.set(Const.cache.BOOK_SOURCE_ID + id, bookSource, Const.cache.BOOK_SOURCE_ID_TTL);
        }
        return bookSource;
    }

    @Override
    public boolean edit(BookSource bookSource) {
        cacheService.del(Const.cache.BOOK_SOURCE_ID + "all");
        cacheService.del(Const.cache.BOOK_SOURCE_ID + bookSource.getId());
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
        List<BookSource> list = (List<BookSource>) cacheService.get(Const.cache.BOOK_SOURCE_ID + "all");
        if (list == null) {
            BookSourceExample example = new BookSourceExample();
            list = mapper.selectByExample(example);
            cacheService.set(Const.cache.BOOK_SOURCE_ID + "all", list, Const.cache.BOOK_SOURCE_ID_TTL);
        }
        return list;
    }

    @Override
    public long countBookSource() {
        return mapper.countByExample(new BookSourceExample());
    }
}
