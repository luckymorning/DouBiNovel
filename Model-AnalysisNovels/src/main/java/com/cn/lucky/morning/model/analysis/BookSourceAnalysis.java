package com.cn.lucky.morning.model.analysis;

import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.BookSource;
import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.objects.XNull;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class BookSourceAnalysis {
    private static final Logger logger = Logs.get();
    private Headers headers;

    @Resource
    private CacheService cacheService;

    public BookSourceAnalysis() {
        headers = new Headers.Builder()
                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                .build();
    }

    /**
     * 通过名称查找书籍
     *
     * @param name       书籍名称
     * @param bookSource 书源信息
     * @return 查询结果
     */
    @Async
    public Future<MvcResult> searchByName(String name, BookSource bookSource) {
        String url = String.format(bookSource.getBaseUrl() + bookSource.getSearchUrl(), name);
        MvcResult result = MvcResult.create();
        try {
            List<BookInfo> list = (List<BookInfo>) cacheService.get(url);
            if (list == null) {
                list = new ArrayList<>();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());
                Elements resultListElements = html.select(bookSource.getSearchResultSelector());
                for (Element resultItem : resultListElements) {
                    BookInfo info = new BookInfo();

                    Element nameElem = resultItem.selectFirst(bookSource.getResultItemBookNameSelector());
                    if (nameElem != null) {
                        info.setName(nameElem.text());
                    }

                    Element bookUrlElem = resultItem.selectFirst(bookSource.getResultItemBookUrlSelector());
                    if (bookUrlElem != null) {
                        info.setBookUrl(bookUrlElem.attr("href"));
                    }

                    Element bookImgElem = resultItem.selectFirst(bookSource.getResultItemBookImageUrlSelector());
                    if (bookImgElem != null) {
                        info.setBookImg(bookImgElem.attr("src"));
                    }

                    info.setBookImgError(bookSource.getImageError());

                    Element bookDesElem = resultItem.selectFirst(bookSource.getResultItemBookDesSelector());
                    if (bookDesElem != null) {
                        info.setNovelDes(bookDesElem.text());
                    }

                    Element authorElem = resultItem.selectFirst(bookSource.getResultItemBookAuthorSelector());
                    if (authorElem != null) {
                        info.setAuthor(authorElem.text());
                    }

                    Element novelTypeElem = resultItem.selectFirst(bookSource.getResultItemBookTypeSelector());
                    if (novelTypeElem != null) {
                        info.setNovelType(novelTypeElem.text());
                    }

                    Element updateElem = resultItem.selectFirst(bookSource.getResultItemBookLastUpdateSelector());
                    if (updateElem != null) {
                        info.setLastUpdate(updateElem.text());
                    }

                    Element lastNewElem = resultItem.selectFirst(bookSource.getResultItemBookLastNewSelector());
                    if (lastNewElem != null) {
                        info.setLastNew(lastNewElem.text());
                    }
                    info.setBookSourceLink(info.getBookUrl());
                    info.setBookSourceName(bookSource.getName());

                    list.add(info);
                }
                cacheService.set(url, list, Const.cache.BOOK_SEARCH_RESULT_TTL);
            }
            result.addVal("list", list);
        } catch (SocketTimeoutException e) {
            logger.error("查找书籍出错", e);
            result.setSuccess(false);
            result.setMessage("《" + bookSource.getName() + "》网络连接超时");
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
     * @param url        详情链接
     * @param bookSource 书源信息
     * @return 详情结果
     */
    @Async
    public Future<MvcResult> loadBookInfo(String url, BookSource bookSource) {
        MvcResult result = MvcResult.create();
        try {
            Map<String, Object> map = (Map<String, Object>) cacheService.get(url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());

                //加载书籍详情
                BookInfo bookInfo = new BookInfo();

                Element nameElem = html.selectFirst(bookSource.getBookDetailBookNameSelector());
                if (nameElem != null) {
                    bookInfo.setName(nameElem.text());
                }

                Element authorElem = html.selectFirst(bookSource.getBookDetailBookAuthorSelector());
                if (authorElem != null) {
                    String author = authorElem.text();
                    bookInfo.setAuthor(author.substring(author.indexOf("：") + 1));
                }

                Element updateElem = html.selectFirst(bookSource.getBookDetailBookLastUpdateSelector());
                if (updateElem != null) {
                    String lastUpdate = updateElem.text();
                    bookInfo.setLastUpdate(lastUpdate.substring(lastUpdate.indexOf("：") + 1));
                }

                Element lastNewElem = html.selectFirst(bookSource.getBookDetailBookLastNewSelector());
                if (lastNewElem != null) {
                    bookInfo.setLastNew(lastNewElem.text());
                }

                Element desElem = html.selectFirst(bookSource.getBookDetailBookDesSelector());
                if (desElem != null) {
                    bookInfo.setNovelDes(desElem.text());
                }

                Element bookImgElem = html.selectFirst(bookSource.getBookDetailBookImageUrlSelector());
                if (bookImgElem != null) {
                    bookInfo.setBookImg(bookImgElem.attr("src"));
                }

                bookInfo.setBookImgError(bookSource.getImageError());
                bookInfo.setBookUrl(url);
                bookInfo.setBookSourceLink(bookSource.getBaseUrl());
                bookInfo.setBookSourceName(bookSource.getName());


                map.put("info", bookInfo);

                //加载章节
                List<Col> catalogs = new ArrayList<>();

                Elements catalogList = html.select(bookSource.getBookDetailCatalogListSelector());
                if (catalogList != null) {
                    for (Element catalogItem : catalogList) {
                        Element catalogNameElem = catalogItem.selectFirst(bookSource.getBookDetailCatalogItemNameSelector());
                        String catalogName;
                        if (catalogNameElem != null) {
                            catalogName = catalogNameElem.text();
                        } else {
                            catalogName = "未知名称";
                        }
                        String href;
                        Element hrefElem = catalogItem.selectFirst(bookSource.getBookDetailCatalogItemLinkSelector());
                        if (hrefElem != null) {
                            href = hrefElem.attr("href");
                            if (!href.startsWith("http://") || !href.startsWith("https://")) {
                                href = bookSource.getBaseUrl() + href;
                            }
                        } else {
                            href = "#";
                        }
                        catalogs.add(new Col(catalogName, href));
                    }
                }

                map.put("catalogs", catalogs);

                cacheService.set(url, map, Const.cache.BOOK_DETAIL_TTL);
            }
            result.setSuccess(true);
            result.addAllVal(map);


        } catch (SocketTimeoutException e) {
            logger.error("获取书籍详情出错", e);
            result.setSuccess(false);
            result.setMessage("《" + bookSource.getName() + "》网络连接超时");
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
     * @param url        章节内容链接
     * @param bookSource 书源信息
     * @return 获取章节内容
     */
    @Async
    public Future<MvcResult> loadContent(String url, BookSource bookSource) {
        MvcResult result = MvcResult.create();
        try {

            Map<String, Object> map = (Map<String, Object>) cacheService.get(url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());

                Element bookNameElem = html.selectFirst(bookSource.getBookContentNameSelector());
                if (bookNameElem != null) {
                    map.put("bookName", bookNameElem.text());
                }

                Element catalogNameElem = html.selectFirst(bookSource.getBookContentCatalogNameSelector());
                if (catalogNameElem != null) {
                    map.put("catalogName", catalogNameElem.text());
                }

                Element contentElem = html.selectFirst(bookSource.getBookContentSelector());
                if (contentElem != null) {
                    String content = contentElem.html();
                    if (StringUtils.isNotBlank(bookSource.getContentRegex())) {
                        content = content.replaceAll("&nbsp;", "##");
                        content = content.replaceAll(bookSource.getContentRegex(), "");
                        content = content.replaceAll("##", "&nbsp;");
                    }
                    map.put("content", content);
                }

                Element preCatalogElem = html.selectFirst(bookSource.getBookContentPreCatalogSelector());
                if (preCatalogElem != null) {
                    String preCatalog = preCatalogElem.attr("href");
                    if (!preCatalog.startsWith("http://") || !preCatalog.startsWith("https://")) {
                        map.put("preCatalog", bookSource.getBaseUrl() + preCatalog);
                    }
                }

                Element catalogsElem = html.selectFirst(bookSource.getBookContentCatalogListSelector());
                if (catalogsElem != null) {
                    String catalogs = catalogsElem.attr("href");
                    if (!catalogs.startsWith("http://") || !catalogs.startsWith("https://")) {
                        map.put("catalogs", bookSource.getBaseUrl() + catalogs);
                    }
                }

                Element nextCatalogElem = html.selectFirst(bookSource.getBookContentNextCatalogSelector());
                if (nextCatalogElem != null) {
                    String nextCatalog = nextCatalogElem.attr("href");
                    if (!nextCatalog.startsWith("http://") || !nextCatalog.startsWith("https://")) {
                        map.put("nextCatalog", bookSource.getBaseUrl() + nextCatalog);
                    }
                }
                cacheService.set(url, map, Const.cache.BOOK_CATALOG_CONTENT_TTL);
            }
            result.setSuccess(true);
            result.addAllVal(map);

        } catch (SocketTimeoutException e) {
            logger.error("获取书籍章节内容出错", e);
            result.setSuccess(false);
            result.setMessage("《" + bookSource.getName() + "》网络连接超时，");
        } catch (Exception e) {
            logger.error("获取书籍章节内容出错", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new AsyncResult<MvcResult>(result);
    }

    /**
     * 预加载章节内容
     *
     * @param url        章节内容链接
     * @param bookSource 书源信息
     */
    @Async
    public void loadNextContent(String url, BookSource bookSource) {
        try {
            Map<String, Object> map = (Map<String, Object>) cacheService.get(url);
            if (map == null) {
                map = Maps.newHashMap();
                Response response = NetWorkUtil.get(url, headers, true);
                Document html = Jsoup.parse(response.body().string());

                Element bookNameElem = html.selectFirst(bookSource.getBookContentNameSelector());
                if (bookNameElem != null) {
                    map.put("bookName", bookNameElem.text());
                }

                Element catalogNameElem = html.selectFirst(bookSource.getBookContentCatalogNameSelector());
                if (catalogNameElem != null) {
                    map.put("catalogName", catalogNameElem.text());
                }

                Element contentElem = html.selectFirst(bookSource.getBookContentSelector());
                if (contentElem != null) {
                    String content = contentElem.html();
                    if (StringUtils.isNotBlank(bookSource.getContentRegex())) {
                        content = content.replaceAll("&nbsp;", "##");
                        content = content.replaceAll(bookSource.getContentRegex(), "");
                        content = content.replaceAll("##", "&nbsp;");
                    }
                    map.put("content", content);
                }

                Element preCatalogElem = html.selectFirst(bookSource.getBookContentPreCatalogSelector());
                if (preCatalogElem != null) {
                    String preCatalog = preCatalogElem.attr("href");
                    if (!preCatalog.startsWith("http://") || !preCatalog.startsWith("https://")) {
                        map.put("preCatalog", bookSource.getBaseUrl() + preCatalog);
                    }
                }

                Element catalogsElem = html.selectFirst(bookSource.getBookContentCatalogListSelector());
                if (catalogsElem != null) {
                    String catalogs = catalogsElem.attr("href");
                    if (!catalogs.startsWith("http://") || !catalogs.startsWith("https://")) {
                        map.put("catalogs", bookSource.getBaseUrl() + catalogs);
                    }
                }

                Element nextCatalogElem = html.selectFirst(bookSource.getBookContentNextCatalogSelector());
                if (nextCatalogElem != null) {
                    String nextCatalog = nextCatalogElem.attr("href");
                    if (!nextCatalog.startsWith("http://") || !nextCatalog.startsWith("https://")) {
                        map.put("nextCatalog", bookSource.getBaseUrl() + nextCatalog);
                    }
                }
                cacheService.set(url, map, Const.cache.BOOK_CATALOG_CONTENT_TTL);
            }

        } catch (SocketTimeoutException e) {
            logger.error("获取书籍章节内容出错", e);
        } catch (Exception e) {
            logger.error("获取书籍章节内容出错", e);
        }
    }
}
