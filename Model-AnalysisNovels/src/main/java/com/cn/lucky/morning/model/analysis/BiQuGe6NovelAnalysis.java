package com.cn.lucky.morning.model.analysis;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.domain.BookInfo;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BiQuGe6NovelAnalysis {
    private static final String BASE_URL = "https://www.xbiquge6.com";
    private static final String IMG_ONERROR="$(this).attr('src', 'https://www.xbiquge6.com/images/nocover.jpg')";

    /**
     * 通过名称查找书籍
     * @param name
     * @return
     */
    public List<BookInfo> searchByName(String name) {
        String url = String.format(BASE_URL+"/search.php?keyword=%s",name);
        List<BookInfo> list = new ArrayList<>();
        try {
            Response response = NetWorkUtil.get(url,null,true);
            Document html = Jsoup.parse(response.body().string());
            Elements divs = html.select(".result-game-item");
            for (Element div : divs){
                BookInfo info = new BookInfo();
                info.setName(div.selectFirst(".result-game-item-title-link").text());
                info.setBookUrl(div.selectFirst(".result-game-item-title-link").attr("href"));
                info.setBookImg(div.selectFirst(".result-game-item-pic-link-img").attr("src"));
                info.setBookImgError(IMG_ONERROR);
                info.setNovelDes(div.selectFirst(".result-game-item-desc").text());
                Elements infos = div.select(".result-game-item-info-tag");
                info.setAuthor(infos.get(0).child(1).text());
                info.setNovelType(infos.get(1).child(1).text());
                info.setLastUpdate(infos.get(2).child(1).text());
                info.setLastNew(infos.get(3).child(1).text());
                info.setBookSourceLink(info.getBookUrl());
                info.setBookSourceName("新笔趣阁");
                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取书籍详细信息页
     * @param url
     * @return
     */
    public MvcResult loadBookInfo(String url){
        MvcResult result = MvcResult.create();
        try {
            Response response = NetWorkUtil.get(url,null,true);
            Document html = Jsoup.parse(response.body().string());
            Element infoDiv = html.selectFirst("#info");
            BookInfo bookInfo = new BookInfo();
            bookInfo.setName(infoDiv.child(0).text());
            String author = infoDiv.child(1).text();
            bookInfo.setAuthor(author.substring(author.indexOf("：")+1));
            String lastUpdate = infoDiv.child(3).text();
            bookInfo.setLastUpdate(lastUpdate.substring(lastUpdate.indexOf("：")+1));
            bookInfo.setLastNew(infoDiv.child(4).selectFirst("a").text());

            Element introDiv = html.selectFirst("#intro");
            bookInfo.setNovelDes(introDiv.child(0).text());

            Element fmimgDiv = html.selectFirst("#fmimg");
            bookInfo.setBookImg(fmimgDiv.child(0).attr("src"));
            bookInfo.setBookImgError(IMG_ONERROR);
            bookInfo.setBookUrl(url);
            bookInfo.setBookSourceLink(bookInfo.getBookUrl());
            bookInfo.setBookSourceName("新笔趣阁");
            result.addVal("info",bookInfo);

            List<Col> catalogs = new ArrayList<>();

            Element listDiv = html.selectFirst("#list");
            Elements dds = listDiv.select("dd");
            for (Element dd: dds){
                Element link = dd.child(0);
                String name = link.text();
                String href = link.attr("href");
                href = BASE_URL + href;
                catalogs.add(new Col(name,href));
            }

            result.addVal("catalogs",catalogs);


        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 获取章节内容
     * @param url
     * @return
     */
    public MvcResult loadContent(String url){
        MvcResult result = MvcResult.create();
        try {
            Response response = NetWorkUtil.get(url,null,true);
            Document html = Jsoup.parse(response.body().string());
            Element name = html.selectFirst(".bookname");
            result.addVal("catalogName",name.child(0).text());

            Element div = html.selectFirst("#content");
            String content = div.html();
            while (content.indexOf("\n<br>\n<br>")!=-1){
                content = content.replaceAll("\n<br>\n<br>","<br>");
            }
            content.replaceAll("\n<br>","<br>");
            result.addVal("content",content);

            Element bottom = html.selectFirst(".bottem2");
            Elements links = bottom.select("a");
            String preCatalog = links.get(0).attr("href");
            if (preCatalog.endsWith(".html")){
                result.addVal("preCatalog",BASE_URL+preCatalog);
            }
            String catalogs = links.get(1).attr("href");
            result.addVal("catalogs",BASE_URL+catalogs);
            String nextCatalog = links.get(2).attr("href");
            if (nextCatalog.endsWith(".html")){
                result.addVal("nextCatalog",BASE_URL+nextCatalog);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
