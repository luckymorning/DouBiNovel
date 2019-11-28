package com.cn.lucky.morning.model.web;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.analysis.BiQuGe6NovelAnalysis;
import com.cn.lucky.morning.model.analysis.DingDiannNovelAnalysis;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.web.tools.CodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class ModelWebApplicationTests {

    @Autowired
    private BiQuGe6NovelAnalysis biQuGe6NovelAnalysis;

    @Autowired
    private DingDiannNovelAnalysis dingDiannNovelAnalysis;

    @Autowired
    private BookAnalysisService bookAnalysisService;

    @Autowired
    private TestAsynTask testAsynTask;

    @Test
    void regexMatchesTest(){
        String str = "&sdas;哈哈哈哈&123;嘿嘿额&asda123;";
        String pattern = "[&]\\d*\\w*[;]";

        System.out.println("替换前："+str);
        System.out.println("替换后："+str.replaceAll(pattern,""));
    }

    @Test
    void md5Password(){
        String password = "123321abc";
        String username = "admin";
        password = CodeUtils.MD5Pwd(username,password);
        System.out.println("加密后密码："+password);
    }

    @Test
    void testAsync() throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();
        Future<String> future1 = testAsynTask.test("future1:sleep()",5000L);
        Future<String> future2 = testAsynTask.test("future2:sleep()",5000L);
        System.out.println("future1:"+future1.get());
        System.out.println("future2:"+future2.get());
        System.out.println("耗时："+(System.currentTimeMillis() - time));
    }

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        Long time = System.currentTimeMillis();
        Future<MvcResult> future1 = biQuGe6NovelAnalysis.searchByName("九星毒奶");
//        Future<MvcResult> future2 = biQuGe6NovelAnalysis.searchByName("三界淘宝店");
        System.out.println("查询1："+JSON.toJSONString(future1.get()));
//        System.out.println("查询2："+JSON.toJSONString(future2.get()));
        System.out.println("耗时："+(System.currentTimeMillis() - time));
    }

    @Test
    void loadBookInfo() throws ExecutionException, InterruptedException {
        String url = "https://www.xbiquge6.com/88_88158/";
        MvcResult result = biQuGe6NovelAnalysis.loadBookInfo(url).get();
        System.out.println("info:"+JSON.toJSONString(result.getVal("info")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));

    }

    @Test
    void loadBookContent() throws ExecutionException, InterruptedException {
        String url = "https://www.xbiquge6.com/84_84291/614756.html";
        MvcResult result = biQuGe6NovelAnalysis.loadContent(url).get();
        System.out.println("content:"+JSON.toJSONString(result.getVal("content")));
        System.out.println("preCatalog:"+JSON.toJSONString(result.getVal("preCatalog")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
        System.out.println("nextCatalog:"+JSON.toJSONString(result.getVal("nextCatalog")));
    }

    @Test
    void dingDiannSearch() throws ExecutionException, InterruptedException {
        String name = "九星毒奶";
        MvcResult result = dingDiannNovelAnalysis.searchByName(name).get();
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    void dingdiannLoadBookInfo() throws ExecutionException, InterruptedException {
        String url = "https://www.dingdiann.com/ddk170870/";
        MvcResult result = dingDiannNovelAnalysis.loadBookInfo(url).get();
        System.out.println("info:"+JSON.toJSONString(result.getVal("info")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
    }

    @Test
    void dingdiannLoadBookContent() throws ExecutionException, InterruptedException {
        String url = "https://www.dingdiann.com/ddk210576/1134909.html";
        MvcResult result = dingDiannNovelAnalysis.loadContent(url).get();
        System.out.println("content:"+JSON.toJSONString(result.getVal("content")));
        System.out.println("preCatalog:"+JSON.toJSONString(result.getVal("preCatalog")));
        System.out.println("catalogs:"+JSON.toJSONString(result.getVal("catalogs")));
        System.out.println("nextCatalog:"+JSON.toJSONString(result.getVal("nextCatalog")));
    }
}
