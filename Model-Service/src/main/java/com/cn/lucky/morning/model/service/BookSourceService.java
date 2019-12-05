package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.BookSource;

import java.util.List;

public interface BookSourceService {
    PageTemplate<BookSource> getByQuery(BaseQuery query);

    boolean add(BookSource bookSource);

    boolean delete(Long id);

    BookSource getById(Long id);

    boolean edit(BookSource bookSource);

    boolean deleteList(List<Long> ids);

    List<BookSource> getAll();
}
