package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.User;
import org.springframework.cache.annotation.*;

import java.util.List;

/**
 * 书籍内容获取服务类
 *
 * @author lucky_morning
 */
@CacheConfig(cacheNames = "SERVICE_BOOK_INFO", cacheManager = "cacheManager")
public interface BookInfoService {
    /**
     * 通过条件查询
     * @param query 查询条件
     * @return
     */
    PageTemplate<BookInfo> getByQuery(BaseQuery query);

    /**
     * 添加书籍
     * @param bookInfo
     * @return
     */
    boolean add(BookInfo bookInfo);

    /**
     * 通过id删除书籍
     * @param id
     * @return
     */
    @Caching(evict = {@CacheEvict(key = "'infos_user_'+#bookInfo.creatorId"), @CacheEvict(key = "#id")})
    boolean delete(Long id);

    /**
     * 通过id获取书籍
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    BookInfo getById(Long id);


    /**
     * 编辑
     * @param bookInfo
     * @return
     */
    @Caching(put = {@CachePut(key = "#bookInfo.id")}, evict = {@CacheEvict(key = "'infos_user_'+#bookInfo.creatorId")})
    boolean edit(BookInfo bookInfo);

    /**
     * 通过列表删除
     * @param ids
     * @return
     */
    @CacheEvict(allEntries = true)
    boolean deleteList(List<Long> ids);

    /**
     * 通过用户获取书籍列表
     * @param user
     * @return
     */
    @Cacheable(key = "'infos_user_'+#user.id")
    List<BookInfo> getBookInfoByUser(User user);

    /**
     * 通过书籍地址和用户获取书籍信息
     * @param bookUrl
     * @param user
     * @return
     */
    BookInfo getBookInfoByBookUrlAndUser(String bookUrl, User user);
}
