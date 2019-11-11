package com.cn.lucky.morning.model.web;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.analysis.novel.BiQuGe6NovelAnalysis;
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

    @Test
    void contextLoads() {
        List<BookInfo> list = biQuGe6NovelAnalysis.searchByName("九星毒奶");
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void loadBookInfo(){
        String url = "https://www.xbiquge6.com/88_88158/";
        MvcResult result = biQuGe6NovelAnalysis.loadBookInfo(url);
        System.out.println("info:"+JSON.toJSONString(result.getVal("info")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));

    }

}
