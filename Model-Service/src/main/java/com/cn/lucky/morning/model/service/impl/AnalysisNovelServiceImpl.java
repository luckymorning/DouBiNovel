package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.service.AnalysisNovelService;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisNovelServiceImpl implements AnalysisNovelService {
    @Override
    public List<BookInfo> searchByName(String url, String name) {
        url = String.format(url,name);
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
                info.setBookImgError("$(this).attr('src', 'https://www.xbiquge6.com/images/nocover.jpg')");
                info.setNovelDes(div.selectFirst(".result-game-item-desc").text());
                Elements infos = div.select(".result-game-item-info-tag");
                info.setAuthor(infos.get(0).child(1).text());
                info.setNovelType(infos.get(1).child(1).text());
                info.setLastUpdate(infos.get(2).child(1).text());
                info.setLastNew(infos.get(3).child(1).text());
                info.setBookSourceLink("https://www.xbiquge6.com/");
                info.setBookSourceName("新笔趣阁");
                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
