package com.cn.lucky.morning.model.service;

import com.cn.lucky.morning.model.domain.BookInfo;

import java.util.List;

public interface AnalysisNovelService {
    List<BookInfo> searchByName(String url,String name);
}
