package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.User;

import java.util.List;

public interface UserService {

    PageTemplate<User> getByQuery(BaseQuery query);

    boolean add(User user);

    User getById(Long id);

    User getByPhoneOrCodeOrEmail(String value);

    boolean edit(User user);

    boolean delete(Long id);

    boolean deleteList(List<Long> ids);

    boolean codeIsExist(String code, Long selfId);

    long countUser();
}
