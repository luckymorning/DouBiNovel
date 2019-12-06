package com.cn.lucky.morning.model.web;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.analysis.BookSourceAnalysis;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.service.BookSourceService;
import com.cn.lucky.morning.model.web.tools.CodeUtils;
import com.google.common.collect.Maps;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class ModelWebApplicationTests {


    @Autowired
    private BookAnalysisService bookAnalysisService;

    @Autowired
    private BookSourceService bookSourceService;

    @Autowired
    private BookSourceAnalysis bookSourceAnalysis;
    @Autowired
    private CacheService cacheService;

    @Test
    void customerTest() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        cacheService.set("testDelay",currentTime,5);
        for (int index = 0; index < 10; index++){
            System.out.println("第【"+index+"】秒，取值："+cacheService.get("testDelay"));
            Thread.sleep(1000);
        }
        System.out.println("结束");
    }

    @Test
    void bookSourceAnalysisTest() throws ExecutionException, InterruptedException {
        List<BookSource> list = bookSourceService.getAll();
//        for (BookSource bookSource : list){
//            System.out.println("书源信息："+JSON.toJSONString(bookSource));
//            Future<MvcResult> future = bookSourceAnalysis.searchByName("斗破苍穹",bookSource);
//            MvcResult result = future.get();
//            System.out.println("搜索结果："+JSON.toJSONString(result));
//        }
//        Future<MvcResult> future = bookSourceAnalysis.loadBookInfo("https://www.xsbiquge.com/0_657/", list.get(0));
//        MvcResult result = future.get();
//        System.out.println("详情解析结果：" + JSON.toJSONString(result));

//        Future<MvcResult> future = bookSourceAnalysis.loadContent("https://www.xsbiquge.com/0_657/8932553.html", list.get(0));
//        MvcResult result = future.get();
//        System.out.println("章节解析结果：" + JSON.toJSONString(result));
    }

    @Test
    void biqugeContentTest() throws IOException {
        Headers headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                .build();
        //书源名称
        String name = "新笔趣阁";
        //书源根地址
        String baseUrl = "https://www.xsbiquge.com";

        //章节名称
        String bookContentCatalogNameSelector = ".bookname > h1";
        //章节内容
        String bookContentSelector = "#content";
        //内容过滤正则表达式
        String regex = "[&]\\d*\\w*[;]*\\d*\\w*[;]";
        //上一章节地址
        String bookContentPreCatalogSelector = ".bottem2 > a:eq(0)";
        //章节列表地址
        String bookContentCatalogListSelector = ".bottem2 > a:eq(1)";
        //下一章节地址
        String bookContentNextCatalogSelector = ".bottem2 > a:eq(2)";

        String url = "https://www.xsbiquge.com/89_89694/717244.html";
        Map<String,Object> map = Maps.newHashMap();
        Response response = NetWorkUtil.get(url, headers, true);
        Document html = Jsoup.parse(response.body().string());


        map.put("catalogName", html.selectFirst(bookContentCatalogNameSelector).text());

        String content = html.selectFirst(bookContentSelector).html();
        content = content.replaceAll("&nbsp;", "##");
        content = content.replaceAll(regex, "");
        content = content.replaceAll("##", "&nbsp;");
        map.put("content", content);

        String preCatalog = html.selectFirst(bookContentPreCatalogSelector).attr("href");
        if (!preCatalog.startsWith("http://")||!preCatalog.startsWith("https://")) {
            map.put("preCatalog", baseUrl + preCatalog);
        }

        String catalogs = html.selectFirst(bookContentCatalogListSelector).attr("href");
        if (!catalogs.startsWith("http://")||!catalogs.startsWith("https://")) {
            map.put("catalogs", baseUrl + catalogs);
        }

        String nextCatalog = html.selectFirst(bookContentNextCatalogSelector).attr("href");
        if (!nextCatalog.startsWith("http://")||!nextCatalog.startsWith("https://")) {
            map.put("nextCatalog", baseUrl + nextCatalog);
        }
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    void biqugeDetailTest() throws IOException {
        Headers headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                .build();
        //书源名称
        String name = "新笔趣阁";
        //书源根地址
        String baseUrl = "https://www.xsbiquge.com";
        //书籍地址错误方法
        String imageError = "$(this).attr('src', '/imgs/nocover.jpg')";
        //书籍详情-书籍名称
        String bookDetailBookNameSelector = "#info > h1";
        //书籍详情-书籍作者
        String bookDetailBookAuthorSelector = "#info > p:eq(1)";
        //书籍详情-书籍更新时间
        String bookDetailBookLastUpdateSelector = "#info > p:eq(3)";
        //书籍详情-书籍最新章节名称
        String bookDetailBookLastNewSelector = "#info > p:eq(4) > a";
        //书籍详情-书籍描述
        String bookDetailBookDesSelector = "#intro";
        //书籍详情-书籍封面地址
        String bookDetailBookImageUrlSelector = "#fmimg > img";

        //书籍章节列表
        String bookDetailCatalogListSelector = "#list dd";
        //书籍章节列表名称
        String bookDetailCatalogItemNameSelector = "a";
        //书籍章节列表连接
        String bookDetailCatalogItemLinkSelector = "a";


        String url = "https://www.xsbiquge.com/89_89694/";
        Response response = NetWorkUtil.get(url, headers, true);
        Document html = Jsoup.parse(response.body().string());

        //加载书籍详情
        BookInfo bookInfo = new BookInfo();

        bookInfo.setName(html.selectFirst(bookDetailBookNameSelector).text());

        String author = html.selectFirst(bookDetailBookAuthorSelector).text();
        bookInfo.setAuthor(author.substring(author.indexOf("：") + 1));

        String lastUpdate = html.selectFirst(bookDetailBookLastUpdateSelector).text();
        bookInfo.setLastUpdate(lastUpdate.substring(lastUpdate.indexOf("：") + 1));

        bookInfo.setLastNew(html.selectFirst(bookDetailBookLastNewSelector).text());

        bookInfo.setNovelDes(html.selectFirst(bookDetailBookDesSelector).text());

        bookInfo.setBookImg(html.selectFirst(bookDetailBookImageUrlSelector).attr("src"));
        bookInfo.setBookImgError(imageError);
        bookInfo.setBookUrl(url);
        bookInfo.setBookSourceLink(bookInfo.getBookUrl());
        bookInfo.setBookSourceName(name);


        //加载章节
        List<Col> catalogs = new ArrayList<>();

        Elements catalogList = html.select(bookDetailCatalogListSelector);
        for (Element catalogItem : catalogList) {
            String catalogName = catalogItem.selectFirst(bookDetailCatalogItemNameSelector).text();
            String href = catalogItem.selectFirst(bookDetailCatalogItemLinkSelector).attr("href");
            if (!href.startsWith("http://") || !href.startsWith("https://")) {
                href = baseUrl + href;
            }
            catalogs.add(new Col(catalogName, href));
        }
        System.out.println(JSON.toJSONString(catalogs));

    }

    @Test
    void biqugeSearchTest() throws IOException {
        Headers headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                .build();
        //书源名称
        String name = "新笔趣阁";
        //书源根地址
        String baseUrl = "https://www.xsbiquge.com";
        //书籍图片地址错误方法
        String imageError = "$(this).attr('src', '/imgs/nocover.jpg')";
        //搜索请求规则
        String searchUrl = "/search.php?keyword=%s";
        //搜索结果集列表
        String searchResultSelector = "div.result-game-item";
        //搜索结果书籍名称
        String resultItemBookNameSelector = ".result-game-item-title-link";
        //搜索结果书籍地址
        String resultItemBookUrlSelector = ".result-game-item-title-link";
        //搜索结果书籍图片地址
        String resultItemBookImageUrlSelector = ".result-game-item-pic-link-img";
        //搜索结果书籍描述
        String resultItemBookDesSelector = ".result-game-item-desc";
        //搜索结果书籍作者
        String resultItemBookAuthorSelector = ".result-game-item-info-tag:eq(0) > span:eq(1)";
        //搜索结果书籍类型
        String resultItemBookTypeSelector = ".result-game-item-info-tag:eq(1) > span:eq(1)";
        //搜索结果书籍更新时间
        String resultItemBookLastUpdateSelector = ".result-game-item-info-tag:eq(2) > span:eq(1)";
        //搜索结果书籍最新章节名
        String resultItemBookLastNewSelector = ".result-game-item-info-tag:eq(3) > a";


        String url = baseUrl + String.format(searchUrl, "重生");

        Response response = NetWorkUtil.get(url, headers, true);
        Document html = Jsoup.parse(response.body().string());
        Elements resultListElements = html.select(searchResultSelector);
        for (Element resultItem : resultListElements) {

            BookInfo info = new BookInfo();
            info.setName(resultItem.selectFirst(resultItemBookNameSelector).text());
            info.setBookUrl(resultItem.selectFirst(resultItemBookUrlSelector).attr("href"));
            info.setBookImg(resultItem.selectFirst(resultItemBookImageUrlSelector).attr("src"));
            info.setBookImgError(imageError);
            info.setNovelDes(resultItem.selectFirst(resultItemBookDesSelector).text());
            info.setAuthor(resultItem.selectFirst(resultItemBookAuthorSelector).text());
            info.setNovelType(resultItem.selectFirst(resultItemBookTypeSelector).text());
            info.setLastUpdate(resultItem.selectFirst(resultItemBookLastUpdateSelector).text());
            info.setLastNew(resultItem.selectFirst(resultItemBookLastNewSelector).text());
            info.setBookSourceLink(info.getBookUrl());
            info.setBookSourceName(name);


            System.out.println(JSON.toJSONString(info));
            break;
        }

    }

    @Test
    void md5Password() {
        String password = "123321abc";
        String username = "admin";
        password = CodeUtils.MD5Pwd(username, password);
        System.out.println("加密后密码：" + password);
    }
}
