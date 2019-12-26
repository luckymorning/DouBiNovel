package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.User;

import java.util.List;

public interface BookInfoService {
    PageTemplate<BookInfo> getByQuery(BaseQuery query);

    boolean add(BookInfo bookInfo);

    boolean delete(Long id);

    BookInfo getById(Long id);

    boolean edit(BookInfo bookInfo);

    boolean deleteList(List<Long> ids);

    List<BookInfo> getBookInfoByUser(User user);
}
