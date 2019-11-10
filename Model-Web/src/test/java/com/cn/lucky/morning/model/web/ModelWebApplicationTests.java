package com.cn.lucky.morning.model.web;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.service.AnalysisNovelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ModelWebApplicationTests {

    @Autowired
    private AnalysisNovelService analysisNovelService;

    @Test
    void contextLoads() {
        List<BookInfo> list = analysisNovelService.searchByName("https://www.xbiquge6.com/search.php?keyword=%s","九星毒奶");
        System.out.println(JSON.toJSONString(list));
    }

}
