package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.analysis.BookSourceAnalysis;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.service.BookSourceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class BookAnalysisServiceImpl implements BookAnalysisService {
    private static final Logger logger = Logs.get();
    @Resource
    private BookSourceAnalysis bookSourceAnalysis;
    @Resource
    private BookSourceService bookSourceService;

    @Override
    public MvcResult searchByName(String name) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(name)) {
            result.setSuccess(false);
            result.setMessage("搜索名称不能为空");
        } else {
            List<BookInfo> list = new ArrayList<>();
            List<BookSource> bookSources = bookSourceService.getAll();
            if (bookSources== null || bookSources.size() == 0){
                result.setSuccess(false);
                result.setMessage("请添加书源");
            }else {
                List<Future<MvcResult>> futureList = new ArrayList<>(bookSources.size());
                for (BookSource bookSource : bookSources){
                    futureList.add(bookSourceAnalysis.searchByName(name,bookSource));
                }

                boolean isAllError = true;
                StringBuffer errorMsg = new StringBuffer();

                for (int index = 0; index < futureList.size(); index++){
                    Future<MvcResult> future = futureList.get(index);
                    try {
                        MvcResult searchResult = future.get();
                        if (searchResult.isSuccess()){
                            list.addAll(searchResult.getVal("list"));
                            isAllError = false;
                        }else {
                            errorMsg.append("书源【"+bookSources.get(index)+"】查询出错:");
                            if (StringUtils.isBlank(searchResult.getMessage())){
                                errorMsg.append("未知原因");
                            }else {
                                errorMsg.append(searchResult.getMessage());
                            }
                            errorMsg.append(";\n");
                        }
                    }catch (Exception e){
                        errorMsg.append("书源【"+bookSources.get(index)+"】加载出错:");
                        if (StringUtils.isBlank(e.getMessage())){
                            errorMsg.append("未知原因");
                        }else {
                            errorMsg.append(e.getMessage());
                        }
                        errorMsg.append(";\n");
                    }
                }

                if (isAllError){
                    result.setSuccess(false);
                    result.setMessage(errorMsg.toString());
                    logger.error("书籍搜素出错："+result.getMessage());
                }else {
                    result.addVal("list", list);
                }
            }
        }
        return result;
    }

    @Override
    public MvcResult loadBookDetail(String url) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isBlank(url)) {
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        } else {
            try {
                Future<MvcResult> future;
                List<BookSource> bookSources = bookSourceService.getAll();
                if (bookSources== null || bookSources.size() == 0){
                    result.setSuccess(false);
                    result.setMessage("请添加书源");
                }else {
                    boolean isNotFound = true;
                    for (BookSource bookSource : bookSources){
                        if (url.contains(bookSource.getBaseUrl())){
                            isNotFound = false;
                            future = bookSourceAnalysis.loadBookInfo(url,bookSource);
                            result = future.get();
                            break;
                        }
                    }
                    if (isNotFound){
                        result.setSuccess(false);
                        result.setMessage("未知解析源，请直接访问 【" + url + "】");
                    }
                }
            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage("解析书籍详情出错（" + e.getMessage() + "），请刷新重试，或直接访问【" + url + "】");
                logger.error("解析书籍详情【" + url + "】出错", e);
            }
        }
        return result;
    }

    @Override
    public MvcResult loadBookContent(String url) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isBlank(url)) {
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        } else {
            try {
                Future<MvcResult> future;
                List<BookSource> bookSources = bookSourceService.getAll();
                if (bookSources== null || bookSources.size() == 0){
                    result.setSuccess(false);
                    result.setMessage("请添加书源");
                }else {
                    boolean isNotFound = true;
                    for (BookSource bookSource : bookSources){
                        if (url.contains(bookSource.getBaseUrl())){
                            isNotFound = false;
                            future = bookSourceAnalysis.loadContent(url,bookSource);
                            result = future.get();
                            break;
                        }
                    }
                    if (isNotFound){
                        result.setSuccess(false);
                        result.setMessage("未知解析源，请直接访问 【" + url + "】");
                    }
                }
            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage("解析章节内容出错（" + e.getMessage() + "），请刷新重试，或直接访问【" + url + "】");
                logger.error("解析章节内容【" + url + "】出错", e);
            }
        }
        if (result.isSuccess()) {
            loadNextCatalogContent(result.getVal("nextCatalog"));
        }
        return result;
    }

    @Override
    public void loadNextCatalogContent(String url) {
        if (!StringUtils.isEmpty(url)) {
            try {
                List<BookSource> bookSources = bookSourceService.getAll();
                if (bookSources!=null && bookSources.size() >0){
                    for (BookSource bookSource : bookSources){
                        if (url.contains(bookSource.getBaseUrl())){
                            bookSourceAnalysis.loadNextContent(url,bookSource);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("解析预览章节内容【" + url + "】出错", e);
            }
        }
    }


}
