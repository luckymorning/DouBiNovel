package com.cn.lucky.morning.model.web;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.analysis.BiQuGe6NovelAnalysis;
import com.cn.lucky.morning.model.analysis.DingDiannNovelAnalysis;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ModelWebApplicationTests {

    @Autowired
    private BiQuGe6NovelAnalysis biQuGe6NovelAnalysis;

    @Autowired
    private DingDiannNovelAnalysis dingDiannNovelAnalysis;

    @Test
    void contextLoads() {
        MvcResult result = biQuGe6NovelAnalysis.searchByName("九星毒奶");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    void loadBookInfo(){
        String url = "https://www.xbiquge6.com/88_88158/";
        MvcResult result = biQuGe6NovelAnalysis.loadBookInfo(url);
        System.out.println("info:"+JSON.toJSONString(result.getVal("info")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));

    }

    @Test
    void loadBookContent(){
        String url = "https://www.xbiquge6.com/84_84291/614756.html";
        MvcResult result = biQuGe6NovelAnalysis.loadContent(url);
        System.out.println("content:"+JSON.toJSONString(result.getVal("content")));
        System.out.println("preCatalog:"+JSON.toJSONString(result.getVal("preCatalog")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
        System.out.println("nextCatalog:"+JSON.toJSONString(result.getVal("nextCatalog")));
    }

    @Test
    void dingDiannSearch(){
        String name = "九星毒奶";
        MvcResult result = dingDiannNovelAnalysis.searchByName(name);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    void dingdiannLoadBookInfo(){
        String url = "https://www.dingdiann.com/ddk170870/";
        MvcResult result = dingDiannNovelAnalysis.loadBookInfo(url);
        System.out.println("info:"+JSON.toJSONString(result.getVal("info")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
    }

    @Test
    void dingdiannLoadBookContent(){
        String url = "https://www.dingdiann.com/ddk210576/1134909.html";
        MvcResult result = dingDiannNovelAnalysis.loadContent(url);
        System.out.println("content:"+JSON.toJSONString(result.getVal("content")));
        System.out.println("preCatalog:"+JSON.toJSONString(result.getVal("preCatalog")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
        System.out.println("nextCatalog:"+JSON.toJSONString(result.getVal("nextCatalog")));
    }
}
