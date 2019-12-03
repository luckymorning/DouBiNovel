package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.analysis.BiQuGe6NovelAnalysis;
import com.cn.lucky.morning.model.analysis.DingDiannNovelAnalysis;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.service.BookAnalysisService;
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
    private BiQuGe6NovelAnalysis biQuGe6NovelAnalysis;
    @Resource
    private DingDiannNovelAnalysis dingDiannNovelAnalysis;

    @Override
    public MvcResult searchByName(String name) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(name)) {
            result.setSuccess(false);
            result.setMessage("搜索名称不能为空");
        } else {
            List<BookInfo> list = new ArrayList<>();
            boolean isAllError = true;
            //新笔趣阁查询书籍
            Future<MvcResult> biQuGeFuture = biQuGe6NovelAnalysis.searchByName(name);
            //顶点查询书籍
            Future<MvcResult> dingDianFuture = dingDiannNovelAnalysis.searchByName(name);

            try {
                MvcResult biQuGeResult = biQuGeFuture.get();
                if (biQuGeResult.isSuccess()) {
                    isAllError = false;
                    list.addAll(biQuGeResult.getVal("list"));
                }
                MvcResult dingDianResult = dingDianFuture.get();
                if (dingDianResult.isSuccess()) {
                    isAllError = false;
                    list.addAll(dingDianResult.getVal("list"));
                }
                if (isAllError) {
                    result.setSuccess(false);
                    result.setMessage(dingDianResult.getMessage());
                } else {
                    if (list.size() > 0) {
                        result.addVal("list", list);
                    }
                }
            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage("查询出错：" + e.getMessage());
                logger.error("查询书籍【" + name + "】出错", e);
            }
        }
        return result;
    }

    @Override
    public MvcResult loadBookDetail(String url) {
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(url)) {
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        } else {
            try {
                Future<MvcResult> future;
                if (biQuGe6NovelAnalysis.isContains(url)) {
                    future = biQuGe6NovelAnalysis.loadBookInfo(url);
                    result = future.get();
                } else if (dingDiannNovelAnalysis.isContains(url)) {
                    future = dingDiannNovelAnalysis.loadBookInfo(url);
                    result = future.get();
                } else {
                    result.setSuccess(false);
                    result.setMessage("未知解析源，请直接访问 【" + url + "】");
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
        if (StringUtils.isEmpty(url)) {
            result.setSuccess(false);
            result.setMessage("解析地址不能为空");
        } else {
            try {
                Future<MvcResult> future;
                if (biQuGe6NovelAnalysis.isContains(url)) {
                    future = biQuGe6NovelAnalysis.loadContent(url);
                    result = future.get();
                } else if (dingDiannNovelAnalysis.isContains(url)) {
                    future = dingDiannNovelAnalysis.loadContent(url);
                    result = future.get();
                } else {
                    result.setSuccess(false);
                    result.setMessage("未知解析源");
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
                if (biQuGe6NovelAnalysis.isContains(url)) {
                    biQuGe6NovelAnalysis.loadNextContent(url);
                } else if (dingDiannNovelAnalysis.isContains(url)) {
                    dingDiannNovelAnalysis.loadNextContent(url);
                }
            } catch (Exception e) {
                logger.error("解析预览章节内容【" + url + "】出错", e);
            }
        }
    }


}
