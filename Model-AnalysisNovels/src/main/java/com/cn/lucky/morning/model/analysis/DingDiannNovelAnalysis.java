package com.cn.lucky.morning.model.analysis;

import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.google.common.collect.Maps;
import okhttp3.Headers;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class DingDiannNovelAnalysis {
    private static final Logger logger = Logs.get();
    public static final String[] BASE_URL = new String[]{"https://www.dingdiann.com"};
    private static final String SOURCE_NAME = "顶点小说";
    private static final String IMG_ONERROR = "$(this).attr('src', '/imgs/nocover.jpg')";
    private Headers headers;

    @Resource
    private CacheService cacheService;

    public DingDiannNovelAnalysis() {
        headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                .build();
    }

    /**
     * 通过名称查找书籍
     *
     * @param name 书籍名称
     * @return 查询结果
     */
    @Async
    public Future<MvcResult> searchByName(String name) {
        MvcResult result = MvcResult.create();
        try {
            name = URLEncoder.encode(name, "utf8");

            String url = String.format(BASE_URL + "/searchbook.php?keyword=%s", name);
            List<BookInfo> list = (List<BookInfo>) cacheService.get(Const.cache.BOOK_SEARCH_RESULT + url);
            if (list == null) {
                list = new ArrayList<>();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());
                Element listDiv = html.selectFirst(".novelslist2");
                Elements lis = listDiv.select("li");

                for (int index = 1; index < lis.size(); index++) {
                    Element li = lis.get(index);
                    BookInfo info = new BookInfo();
                    info.setName(li.selectFirst(".s2").text());
                    info.setBookUrl(BASE_URL + li.selectFirst(".s2").child(0).attr("href"));
                    info.setBookImg("/imgs/nocover.jpg");
                    info.setBookImgError(IMG_ONERROR);
                    info.setNovelDes("");
                    info.setAuthor(li.selectFirst(".s4").text());
                    info.setNovelType(li.selectFirst(".s1").text());
                    info.setLastUpdate(li.selectFirst(".s6").text());
                    info.setLastNew(li.selectFirst(".s3").text());
                    info.setBookSourceLink(info.getBookUrl());
                    info.setBookSourceName(SOURCE_NAME);
                    list.add(info);
                }
                cacheService.set(Const.cache.BOOK_SEARCH_RESULT + url, list, Const.cache.BOOK_SEARCH_RESULT_TTL);
            }
            result.addVal("list", list);
        } catch (SocketTimeoutException e) {
            logger.error("查找书籍出错", e);
            result.setSuccess(false);
            result.setMessage("《" + SOURCE_NAME + "》网络连接超时");
        } catch (Exception e) {
            logger.error("查找书籍出错", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new AsyncResult<MvcResult>(result);
    }

    /**
     * 获取书籍详细信息页
     *
     * @param url 详情链接
     * @return 详情结果
     */
    @Async
    public Future<MvcResult> loadBookInfo(String url) {
        MvcResult result = MvcResult.create();
        try {
            Map<String, Object> map = (Map<String, Object>) cacheService.get(Const.cache.BOOK_DETAIL + url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());
                Element infoDiv = html.selectFirst("#info");
                BookInfo bookInfo = new BookInfo();
                bookInfo.setName(infoDiv.child(0).text());

                String author = infoDiv.child(1).text();
                bookInfo.setAuthor(author.substring(author.indexOf("：") + 1));

                String lastUpdate = infoDiv.child(3).text();
                bookInfo.setLastUpdate(lastUpdate.substring(lastUpdate.indexOf("：") + 1));
                bookInfo.setLastNew(infoDiv.child(4).selectFirst("a").text());

                Element introDiv = html.selectFirst("#intro");
                bookInfo.setNovelDes(introDiv.text());

                Element fmimgDiv = html.selectFirst("#fmimg");
                bookInfo.setBookImg(BASE_URL + fmimgDiv.child(0).attr("src"));
                bookInfo.setBookImgError(IMG_ONERROR);
                bookInfo.setBookUrl(url);
                bookInfo.setBookSourceLink(bookInfo.getBookUrl());
                bookInfo.setBookSourceName(SOURCE_NAME);
                map.put("info", bookInfo);

                List<Col> catalogs = new ArrayList<>();

                Element listDiv = html.selectFirst("#list");
                Elements dds = listDiv.select("dd");
                for (int index = 12; index < dds.size(); index++) {
                    Element dd = dds.get(index);
                    Element link = dd.child(0);
                    String name = link.text();
                    String href = link.attr("href");
                    href = BASE_URL + href;
                    catalogs.add(new Col(name, href));
                }

                map.put("catalogs", catalogs);

                cacheService.set(Const.cache.BOOK_DETAIL + url, map, Const.cache.BOOK_DETAIL_TTL);
            }

            result.setSuccess(true);
            result.addAllVal(map);

        } catch (SocketTimeoutException e) {
            logger.error("获取书籍详情出错", e);
            result.setSuccess(false);
            result.setMessage("《" + SOURCE_NAME + "》网络连接超时");
        } catch (Exception e) {
            logger.error("获取书籍详情出错", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new AsyncResult<MvcResult>(result);
    }

    /**
     * 获取章节内容
     *
     * @param url 章节内容链接
     * @return 获取章节内容
     */
    @Async
    public Future<MvcResult> loadContent(String url) {
        MvcResult result = MvcResult.create();
        try {

            Map<String, Object> map = (Map<String, Object>) cacheService.get(Const.cache.BOOK_CATALOG_CONTENT + url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());
                Element name = html.selectFirst(".bookname");
                map.put("catalogName", name.child(0).text());

                Element div = html.selectFirst("#content");
                String content = div.html().replaceAll("\n", "");
                while (content.indexOf("<br><br>") != -1) {
                    content = content.replaceAll("<br><br>", "<br>");
                }
                map.put("content", content);

                Element bottom = html.selectFirst(".bottem2");
                Elements links = bottom.select("a");
                String preCatalog = links.get(1).attr("href");
                if (preCatalog.endsWith(".html")) {
                    map.put("preCatalog", BASE_URL + preCatalog);
                }
                String catalogs = links.get(2).attr("href");
                map.put("catalogs", BASE_URL + catalogs);
                String nextCatalog = links.get(3).attr("href");
                if (nextCatalog.endsWith(".html")) {
                    map.put("nextCatalog", BASE_URL + nextCatalog);
                }

                cacheService.set(Const.cache.BOOK_CATALOG_CONTENT + url, map, Const.cache.BOOK_CATALOG_CONTENT_TTL);
            }
            result.setSuccess(true);
            result.addAllVal(map);

        } catch (SocketTimeoutException e) {
            logger.error("获取章节内容出错", e);
            result.setSuccess(false);
            result.setMessage("《" + SOURCE_NAME + "》网络连接超时");
        } catch (Exception e) {
            logger.error("获取章节内容出错", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new AsyncResult<MvcResult>(result);
    }

    /**
     * 预加载章节内容
     *
     * @param url 章节内容链接
     */
    @Async
    public void loadNextContent(String url) {
        try {

            Map<String, Object> map = (Map<String, Object>) cacheService.get(Const.cache.BOOK_CATALOG_CONTENT + url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());
                Element name = html.selectFirst(".bookname");
                map.put("catalogName", name.child(0).text());

                Element div = html.selectFirst("#content");
                String content = div.html().replaceAll("\n", "");
                while (content.indexOf("<br><br>") != -1) {
                    content = content.replaceAll("<br><br>", "<br>");
                }
                map.put("content", content);

                Element bottom = html.selectFirst(".bottem2");
                Elements links = bottom.select("a");
                String preCatalog = links.get(1).attr("href");
                if (preCatalog.endsWith(".html")) {
                    map.put("preCatalog", BASE_URL + preCatalog);
                }
                String catalogs = links.get(2).attr("href");
                map.put("catalogs", BASE_URL + catalogs);
                String nextCatalog = links.get(3).attr("href");
                if (nextCatalog.endsWith(".html")) {
                    map.put("nextCatalog", BASE_URL + nextCatalog);
                }
                cacheService.set(Const.cache.BOOK_CATALOG_CONTENT + url, map, Const.cache.BOOK_CATALOG_CONTENT_TTL);
            }

        } catch (SocketTimeoutException e) {
            logger.error("获取章节内容出错", e);
        } catch (Exception e) {
            logger.error("获取章节内容出错", e);
        }
    }

    /**
     * 是否用此类解析链接
     *
     * @param url 代解析链接
     * @return 是否包含在此类中
     */
    public boolean isContains(String url) {
        for (String baseUrl : BASE_URL) {
            if (url.contains(baseUrl)) {
                return true;
            }
        }
        return false;
    }
}
