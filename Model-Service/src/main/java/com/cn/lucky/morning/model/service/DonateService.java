package com.cn.lucky.morning.model.service;


import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.Donate;

import java.util.List;

public interface DonateService {
    PageTemplate<Donate> getByQuery(BaseQuery query);

    boolean add(Donate bookSource);

    boolean delete(Long id);

    Donate getById(Long id);

    boolean edit(Donate bookSource);

    boolean deleteList(List<Long> ids);

    List<Donate> getAll();

    long countDonate();
}
