package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.common.mvc.MvcResult;


public interface BookAnalysisService {
    MvcResult searchByName(String name);
    MvcResult loadBookDetail(String url);
    MvcResult loadBookContent(String url);
    void loadNextCatalogContent(String url);
}
