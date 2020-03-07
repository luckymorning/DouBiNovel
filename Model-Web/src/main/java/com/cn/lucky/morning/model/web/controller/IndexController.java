package com.cn.lucky.morning.model.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.network.Col;
import com.cn.lucky.morning.model.common.tool.ByteUtils;
import com.cn.lucky.morning.model.domain.*;
import com.cn.lucky.morning.model.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author wangchen
 */
@Controller
public class IndexController {
    private static final Logger logger = Logs.get();
    @Resource
    private UpdateLogService updateLogService;
    @Resource
    private BookAnalysisService bookAnalysisService;
    @Resource
    private BookInfoService bookInfoService;
    @Resource
    private DonateService donateService;
    @Resource
    private SystemNotificationService systemNotificationService;


    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        List<UpdateLog> list = updateLogService.findListLog();
        model.addAttribute("list", list);
        return "front/index";
    }

    @RequestMapping("/findNotification")
    @ResponseBody
    public MvcResult findNotification() {
        MvcResult result = MvcResult.create();
        try {
            SystemNotification notification = systemNotificationService.findLastNotification();
            if (notification == null) {
                result.setSuccess(false);
                result.setMessage("无系统公告");
            } else {
                result.setData(notification);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            if (StringUtils.isNotEmpty(e.getMessage())) {
                result.setMessage(e.getMessage());
            } else {
                result.setMessage("未知错误");
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public String search(@Param("name") String name, Model model) {
        MvcResult result = bookAnalysisService.searchByName(name);
        if (!result.isSuccess()) {
            model.addAttribute("msg", result.getMessage());
            return "public/error";
        }
        model.addAttribute("list", result.getVal("list"));
        model.addAttribute("name", name);
        return "front/search/list";
    }

    @RequestMapping("/book/detail")
    public String detail(String url, Model model) {
        MvcResult result = bookAnalysisService.loadBookDetail(url);
        if (!result.isSuccess()) {
            model.addAttribute("msg", result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());


        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user != null) {
            BookInfo bookInfo = bookInfoService.getBookInfoByBookUrlAndUser(url, user);
            if (bookInfo != null) {
                if (StringUtils.isNotBlank(bookInfo.getLastReadCatalogLink())) {
                    model.addAttribute("lastReadCatalogLink", bookInfo.getLastReadCatalogLink());
                    model.addAttribute("lastReadCatalogName", bookInfo.getLastReadCatalogName());
                }
            }
        }
        return "front/book/detail";
    }

    @RequestMapping("/book/reader")
    public String reader(String url, Model model) {
        MvcResult result = bookAnalysisService.loadBookContent(url);
        if (!result.isSuccess()) {
            model.addAttribute("msg", result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());
        model.addAttribute("currentUrl", url);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user != null) {
            BookInfo bookInfo = bookInfoService.getBookInfoByBookUrlAndUser(result.getVal("catalogs"), user);
            if (bookInfo != null) {
                bookInfo.setLastReadCatalogLink(url);
                bookInfo.setLastReadCatalogName(result.getVal("catalogName"));
                boolean isSuccess = bookInfoService.edit(bookInfo);
                if (!isSuccess) {
                    logger.error("保存书籍阅读记录失败【" + JSON.toJSONString(bookInfo) + "】");
                }
            }
        }
        return "front/book/reader";
    }

    @RequestMapping("/donate/list")
    public String donateList(Model model) {
        List<Donate> list = donateService.getAll();
        if (list != null && list.size() > 0) {
            model.addAttribute("list", list);
        }
        return "front/donate/list";
    }

    @RequestMapping("/book/loadCatalogs")
    @ResponseBody
    public MvcResult loadCatalogs(String url) {
        MvcResult result = bookAnalysisService.loadBookDetail(url);
        return result;
    }

    @RequestMapping("/book/download")
    public void download(String url, HttpServletResponse response) {
        try {
            response.setHeader("Content-type", "text/html;charset=gb2312");
            response.setCharacterEncoding("gb2312");

            if (StringUtils.isBlank(url)) {
                response.getOutputStream().write("链接不能为空！！！".getBytes());
                return;
            }
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user == null) {
                response.getOutputStream().write("很抱歉，因服务器性能较弱，下载功能仅对注册用户开放！！！".getBytes());
                return;
            }
            MvcResult result = bookAnalysisService.loadBookDetail(url);
            ServletOutputStream os = response.getOutputStream();
            if (result.isSuccess()) {
                BookInfo info = result.getVal("info");

                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(info.getName() + ".txt", "utf-8"));

                List<Col> catalogs = result.getVal("catalogs");

                os.write(("书名：" + info.getName() + "\n").getBytes());
                os.write(("作者：" + info.getAuthor() + "\n").getBytes());
                os.write(("描述：" + Jsoup.parse(info.getNovelDes()).text() + "\n").getBytes());
                os.write(("更多小说请关注：http://novel.luckymorning.cn\n").getBytes());

                int index = 10;
                for (Col col : catalogs) {
                    long start = System.currentTimeMillis();
                    if (!col.getName().startsWith("第")) {
                        os.write(("第" + index + "章.").getBytes());
                    }
                    os.write(col.getName().getBytes());
                    os.write("\n".getBytes());
                    result = bookAnalysisService.loadBookContent(col.getValue().toString());
                    if (result.isSuccess()) {
                        String content = result.getVal("content");
                        content = content.replaceAll("<br>", "\n");
                        os.write(Jsoup.parse(content).text().getBytes());
                    } else {
                        os.write(("加载内容出错！" + result.getMessage()).getBytes());
                    }
                    os.write("\n".getBytes());
                    index++;
                    logger.info("加载完毕《"+col.getName()+"》，耗时："+(System.currentTimeMillis()-start));
                }
            } else {
                os.write(result.getMessage().getBytes());
            }
        } catch (IOException e) {
            logger.error("下载书籍（" + url + "）出错"+e.getMessage());
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("下载书籍（" + url + "）出错", e);
        }
    }
}
