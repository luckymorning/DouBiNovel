package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.dao.BookInfoMapper;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.BookInfoExample;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.BookInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class BookInfoServiceImpl implements BookInfoService {
    @Resource
    private BookInfoMapper mapper;

    @Override
    public PageTemplate<BookInfo> getByQuery(BaseQuery query) {
        BookInfoExample example = new BookInfoExample();
        BookInfoExample.Criteria criteria = example.createCriteria();
        if (query.isNotEmpty("creatorId")){
            criteria.andCreatorIdEqualTo(query.getLong("creatorId"));
        }
        if (query.isNotEmpty("bookSourceLink")){
            criteria.andBookSourceLinkEqualTo(query.getString("bookSourceLink"));
        }
        if (query.isNotEmpty("bookUrl")){
            criteria.andBookUrlEqualTo(query.getString("bookUrl"));
        }
        if (query.isNotEmpty("name")){
            criteria.andNameLike("%"+query.getString("name")+"%");
        }
        if (query.isNotEmpty("author")){
            criteria.andAuthorLike("%"+query.getString("author")+"%");
        }
        example.setPage(query.getCurrent(),query.getSize());
        example.setOrderByClause("id desc");
        return PageTemplate.create(example,mapper,query);
    }

    @Override
    public boolean add(BookInfo bookInfo) {
        return mapper.insertSelective(bookInfo) > 0;
    }

    @Override
    public boolean delete(Long id) {
        BookInfo bookInfo = getById(id);
        if (bookInfo == null){
            return true;
        }
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public BookInfo getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(BookInfo bookInfo) {
        return mapper.updateByPrimaryKeySelective(bookInfo) > 0;
    }

    @Override
    public boolean deleteList(List<Long> ids) {
        if (ids == null || ids.size() ==0){
            return true;
        }
        for (Long id : ids){
            if (!delete(id)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookInfo> getBookInfoByUser(User user) {
        BookInfoExample example = new BookInfoExample();
        example.createCriteria().andCreatorIdEqualTo(user.getId());
        List<BookInfo> list = mapper.selectByExample(example);
        return list;
    }

    @Override
    public BookInfo getBookInfoByBookUrlAndUser(String bookUrl, User user) {
        List<BookInfo> list = getBookInfoByUser(user);
        if (list!=null && list.size() > 0){
            for (BookInfo bookInfo : list){
                if (Objects.equals(bookUrl,bookInfo.getBookUrl())){
                    return bookInfo;
                }
            }
        }
        return null;
    }
}
