package com.cn.lucky.morning.model.web;

import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.service.BookSourceService;
import com.cn.lucky.morning.model.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;

@SpringBootTest
class ModelWebApplicationTests {


    @Autowired
    private BookAnalysisService bookAnalysisService;

    @Autowired
    private BookSourceService bookSourceService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

//    @Test
//    void download() throws IOException {
//        File file = new File("D://test.txt");
//        if (!file.exists()){
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(file,false);
//        long start = System.currentTimeMillis();
//        String url = "https://www.xsbiquge.com/77_77523/";
//        MvcResult result = bookAnalysisService.loadBookDetail(url);
//        if (result.isSuccess()){
//            BookInfo info = result.getVal("info");
//            List<Col> catalogs = result.getVal("catalogs");
//
////            StringBuilder builder = new StringBuilder();
////            builder.append("******************\n");
////            builder.append("书名："+info.getName()+"\n");
////            builder.append("作者："+info.getAuthor()+"\n");
////            builder.append("描述："+Jsoup.parse(info.getNovelDes()).text()+"\n");
////            builder.append("更多小说请关注：http://novel.luckymorning.cn\n");
////            builder.append("******************\n");
//
//            fos.write(("书名："+info.getName()+"\n").getBytes("utf8"));
//            fos.write(("作者："+info.getAuthor()+"\n").getBytes("utf8"));
//            fos.write(("描述："+Jsoup.parse(info.getNovelDes()).text()+"\n").getBytes("utf8"));
//            fos.write(("更多小说请关注：novel.luckymorning.cn\n").getBytes("utf8"));
//
//            int index = 1;
//            for (Col col:catalogs){
//                if (!col.getName().startsWith("第")){
//                    fos.write(("第"+index+"章.").getBytes("utf8"));
//                }
//                fos.write(col.getName().getBytes("utf8"));
//                fos.write("\n".getBytes("utf8"));
//                result = bookAnalysisService.loadBookContent(col.getValue().toString());
//                if (result.isSuccess()){
//                    String content = result.getVal("content");
//                    content = content.replaceAll("<br>","\n").replaceAll("&nbsp;"," ");
////                    builder.append(Jsoup.parse(result.getVal("catalogName")).text());
////                    builder.append(Jsoup.parse(content).text());
//                    fos.write(content.getBytes("utf8"));
//                    fos.write("\n".getBytes("utf8"));
//                }else {
//                    System.out.println("加载章节《"+col.getName()+"》出错！"+result.getMessage());
//                    return;
//                }
//                index++;
//                if (index > 3){
//                    break;
//                }
//            }
////            System.out.println(JSON.toJSONString(builder.toString()));
//        }else {
//            System.out.println("加载出错！"+result.getMessage());
//        }
//        fos.flush();
//        fos.close();
//        System.out.println("完毕，总耗时："+(System.currentTimeMillis() - start));
//    }

//
//    @Test
//    void simpleMailSend(){
//        mailService.sendSimpleMail("lucky_morning@163.com","test simple mail"," hello this is simple mail");
//    }
//
//    @Test
//    void sendHtmlMail(){
//        String content="<html>\n" +
//                "<body>\n" +
//                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
//                "</body>\n" +
//                "</html>";
//        mailService.sendHtmlMail("lucky_morning@163.com","test simple mail",content);
//    }
//
//    @Test
//    public void sendTemplateMail() {
//        String siteName = "逗逼联盟-逗逼阅读";
//        //创建邮件正文
//        Context context = new Context();
//        context.setVariable("siteName", siteName);
//        context.setVariable("mail","lucky_morning@163.com");
//        context.setVariable("code",String.format("%4d",new Random().nextInt(10000)));
//        String emailContent = templateEngine.process("mail/regist_mail", context);
//
//        mailService.sendHtmlMail("lucky_morning@163.com",String.format("欢迎注册 %s ",siteName),emailContent);
//    }

//
//    @Test
//    void bookSourceAnalysisTest() throws ExecutionException, InterruptedException {
//        List<BookSource> list = bookSourceService.getAll();
//        for (BookSource bookSource : list){
//            System.out.println("书源信息："+JSON.toJSONString(bookSource));
//            Future<MvcResult> future = bookSourceAnalysis.searchByName("斗破苍穹",bookSource);
//            MvcResult result = future.get();
//            System.out.println("搜索结果："+JSON.toJSONString(result));
//        }
//
////        Future<MvcResult> future = bookSourceAnalysis.loadBookInfo("https://www.xsbiquge.com/0_657/", list.get(0));
////        MvcResult result = future.get();
////        System.out.println("详情解析结果：" + JSON.toJSONString(result));
//
////        Future<MvcResult> future = bookSourceAnalysis.loadContent("https://www.xsbiquge.com/0_657/8932553.html", list.get(0));
////        MvcResult result = future.get();
////        System.out.println("章节解析结果：" + JSON.toJSONString(result));
//    }
//
//    @Test
//    void biqugeContentTest() throws IOException {
//        Headers headers = new Headers.Builder()
//                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                .build();
//        //书源名称
//        String name = "新笔趣阁";
//        //书源根地址
//        String baseUrl = "https://www.xsbiquge.com";
//
//        //章节名称
//        String bookContentCatalogNameSelector = ".bookname > h1";
//        //章节内容
//        String bookContentSelector = "#content";
//        //内容过滤正则表达式
//        String regex = "[&]\\d*\\w*[;]*\\d*\\w*[;]";
//        //上一章节地址
//        String bookContentPreCatalogSelector = ".bottem2 > a:eq(0)";
//        //章节列表地址
//        String bookContentCatalogListSelector = ".bottem2 > a:eq(1)";
//        //下一章节地址
//        String bookContentNextCatalogSelector = ".bottem2 > a:eq(2)";
//
//        String url = "https://www.xsbiquge.com/89_89694/717244.html";
//        Map<String,Object> map = Maps.newHashMap();
//        Response response = NetWorkUtil.get(url, headers, true);
//        Document html = Jsoup.parse(response.body().string());
//
//
//        map.put("catalogName", html.selectFirst(bookContentCatalogNameSelector).text());
//
//        String content = html.selectFirst(bookContentSelector).html();
//        content = content.replaceAll("&nbsp;", "##");
//        content = content.replaceAll(regex, "");
//        content = content.replaceAll("##", "&nbsp;");
//        map.put("content", content);
//
//        String preCatalog = html.selectFirst(bookContentPreCatalogSelector).attr("href");
//        if (!preCatalog.startsWith("http://")||!preCatalog.startsWith("https://")) {
//            map.put("preCatalog", baseUrl + preCatalog);
//        }
//
//        String catalogs = html.selectFirst(bookContentCatalogListSelector).attr("href");
//        if (!catalogs.startsWith("http://")||!catalogs.startsWith("https://")) {
//            map.put("catalogs", baseUrl + catalogs);
//        }
//
//        String nextCatalog = html.selectFirst(bookContentNextCatalogSelector).attr("href");
//        if (!nextCatalog.startsWith("http://")||!nextCatalog.startsWith("https://")) {
//            map.put("nextCatalog", baseUrl + nextCatalog);
//        }
//        System.out.println(JSON.toJSONString(map));
//    }
//
//    @Test
//    void biqugeDetailTest() throws IOException {
//        Headers headers = new Headers.Builder()
//                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                .build();
//        //书源名称
//        String name = "新笔趣阁";
//        //书源根地址
//        String baseUrl = "https://www.xsbiquge.com";
//        //书籍地址错误方法
//        String imageError = "$(this).attr('src', '/imgs/nocover.jpg')";
//        //书籍详情-书籍名称
//        String bookDetailBookNameSelector = "#info > h1";
//        //书籍详情-书籍作者
//        String bookDetailBookAuthorSelector = "#info > p:eq(1)";
//        //书籍详情-书籍更新时间
//        String bookDetailBookLastUpdateSelector = "#info > p:eq(3)";
//        //书籍详情-书籍最新章节名称
//        String bookDetailBookLastNewSelector = "#info > p:eq(4) > a";
//        //书籍详情-书籍描述
//        String bookDetailBookDesSelector = "#intro";
//        //书籍详情-书籍封面地址
//        String bookDetailBookImageUrlSelector = "#fmimg > img";
//
//        //书籍章节列表
//        String bookDetailCatalogListSelector = "#list dd";
//        //书籍章节列表名称
//        String bookDetailCatalogItemNameSelector = "a";
//        //书籍章节列表连接
//        String bookDetailCatalogItemLinkSelector = "a";
//
//
//        String url = "https://www.zwdu.com/book/31087/";
//        Response response = NetWorkUtil.get(url, headers, true);
//        byte[] bytes = response.body().bytes();
//        String charset = ByteUtils.getEncoding(bytes);
//        String responseStr = new String(bytes,charset);
//        Document html = Jsoup.parse(responseStr);
//        System.out.println("charset:"+ charset);
//
//        //加载书籍详情
//        BookInfo bookInfo = new BookInfo();
//
//        bookInfo.setName(html.selectFirst(bookDetailBookNameSelector).text());
//
//        String author = html.selectFirst(bookDetailBookAuthorSelector).text();
//        bookInfo.setAuthor(author.substring(author.indexOf("：") + 1));
//
//        String lastUpdate = html.selectFirst(bookDetailBookLastUpdateSelector).text();
//        bookInfo.setLastUpdate(lastUpdate.substring(lastUpdate.indexOf("：") + 1));
//
//        bookInfo.setLastNew(html.selectFirst(bookDetailBookLastNewSelector).text());
//
//        bookInfo.setNovelDes(html.selectFirst(bookDetailBookDesSelector).text());
//
//        bookInfo.setBookImg(html.selectFirst(bookDetailBookImageUrlSelector).attr("src"));
//        bookInfo.setBookImgError(imageError);
//        bookInfo.setBookUrl(url);
//        bookInfo.setBookSourceLink(bookInfo.getBookUrl());
//        bookInfo.setBookSourceName(name);
//
//
//        //加载章节
//        List<Col> catalogs = new ArrayList<>();
//
//        Elements catalogList = html.select(bookDetailCatalogListSelector);
//        for (Element catalogItem : catalogList) {
//            String catalogName = catalogItem.selectFirst(bookDetailCatalogItemNameSelector).text();
//
//            String href = catalogItem.selectFirst(bookDetailCatalogItemLinkSelector).attr("href");
//            if (!href.startsWith("http://") || !href.startsWith("https://")) {
//                href = baseUrl + href;
//            }
//            catalogs.add(new Col(catalogName, href));
//        }
//        System.out.println(JSON.toJSONString(catalogs));
//
//    }
//
//    @Test
//    void biqugeSearchTest() throws IOException {
//        Headers headers = new Headers.Builder()
//                .add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                .build();
//        //书源名称
//        String name = "新笔趣阁";
//        //书源根地址
//        String baseUrl = "https://www.xsbiquge.com";
//        //书籍图片地址错误方法
//        String imageError = "$(this).attr('src', '/imgs/nocover.jpg')";
//        //搜索请求规则
//        String searchUrl = "/search.php?keyword=%s";
//        //搜索结果集列表
//        String searchResultSelector = "div.result-game-item";
//        //搜索结果书籍名称
//        String resultItemBookNameSelector = ".result-game-item-title-link";
//        //搜索结果书籍地址
//        String resultItemBookUrlSelector = ".result-game-item-title-link";
//        //搜索结果书籍图片地址
//        String resultItemBookImageUrlSelector = ".result-game-item-pic-link-img";
//        //搜索结果书籍描述
//        String resultItemBookDesSelector = ".result-game-item-desc";
//        //搜索结果书籍作者
//        String resultItemBookAuthorSelector = ".result-game-item-info-tag:eq(0) > span:eq(1)";
//        //搜索结果书籍类型
//        String resultItemBookTypeSelector = ".result-game-item-info-tag:eq(1) > span:eq(1)";
//        //搜索结果书籍更新时间
//        String resultItemBookLastUpdateSelector = ".result-game-item-info-tag:eq(2) > span:eq(1)";
//        //搜索结果书籍最新章节名
//        String resultItemBookLastNewSelector = ".result-game-item-info-tag:eq(3) > a";
//
//
//        String url = baseUrl + String.format(searchUrl, "重生");
//
//        Response response = NetWorkUtil.get(url, headers, true);
//        Document html = Jsoup.parse(response.body().string());
//        Elements resultListElements = html.select(searchResultSelector);
//        for (Element resultItem : resultListElements) {
//
//            BookInfo info = new BookInfo();
//            info.setName(resultItem.selectFirst(resultItemBookNameSelector).text());
//            info.setBookUrl(resultItem.selectFirst(resultItemBookUrlSelector).attr("href"));
//            info.setBookImg(resultItem.selectFirst(resultItemBookImageUrlSelector).attr("src"));
//            info.setBookImgError(imageError);
//            info.setNovelDes(resultItem.selectFirst(resultItemBookDesSelector).text());
//            info.setAuthor(resultItem.selectFirst(resultItemBookAuthorSelector).text());
//            info.setNovelType(resultItem.selectFirst(resultItemBookTypeSelector).text());
//            info.setLastUpdate(resultItem.selectFirst(resultItemBookLastUpdateSelector).text());
//            info.setLastNew(resultItem.selectFirst(resultItemBookLastNewSelector).text());
//            info.setBookSourceLink(info.getBookUrl());
//            info.setBookSourceName(name);
//
//
//            System.out.println(JSON.toJSONString(info));
//            break;
//        }
//
//    }
}
