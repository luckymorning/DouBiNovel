package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.analysis.BiQuGe6NovelAnalysis;
import com.cn.lucky.morning.model.analysis.DingDiannNovelAnalysis;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookAnalysisServiceImpl implements BookAnalysisService {
    @Autowired
    private BiQuGe6NovelAnalysis biQuGe6NovelAnalysis;
    @Autowired
    private DingDiannNovelAnalysis dingDiannNovelAnalysis;
    @Override
    public MvcResult searchByName(String name) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(name)){
            result.setSuccess(false);
            result.setMessage("搜索名称不能为空");
        }else {
            List<BookInfo> list = new ArrayList<>();
            boolean isAllError = true;
            //新笔趣阁查询书籍
            MvcResult searchResult = biQuGe6NovelAnalysis.searchByName(name);
            if (searchResult.isSuccess()){
                isAllError = false;
                list.addAll(searchResult.getVal("list"));
            }
            //顶点查询书籍
            searchResult = dingDiannNovelAnalysis.searchByName(name);
            if (searchResult.isSuccess()){
                isAllError = false;
                list.addAll(searchResult.getVal("list"));
            }
            if (isAllError){
                result.setSuccess(false);
                result.setMessage(searchResult.getMessage());
            }else {
                if (list.size()>0){
                    result.addVal("list",list);
                }
            }
        }
        return result;
    }

    @Override
    public MvcResult loadBookDetail(String url) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(url)){
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        }else {
            if (url.contains(biQuGe6NovelAnalysis.BASE_URL)){
                result = biQuGe6NovelAnalysis.loadBookInfo(url);
            }else if (url.contains(dingDiannNovelAnalysis.BASE_URL)){
                result = dingDiannNovelAnalysis.loadBookInfo(url);
            }else {
                result.setSuccess(false);
                result.setMessage("未知解析源");
            }
        }
        return result;
    }

    @Override
    public MvcResult loadBookContent(String url) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(url)){
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        }else {
            if (url.contains(biQuGe6NovelAnalysis.BASE_URL)){
                result = biQuGe6NovelAnalysis.loadContent(url);
            }else if (url.contains(dingDiannNovelAnalysis.BASE_URL)){
                result = dingDiannNovelAnalysis.loadContent(url);
            }else {
                result.setSuccess(false);
                result.setMessage("未知解析源");
            }
        }
        return result;
    }
}
