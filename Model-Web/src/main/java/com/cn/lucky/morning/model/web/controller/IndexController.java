package com.cn.lucky.morning.model.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.*;
import com.cn.lucky.morning.model.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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


    @RequestMapping({"/","/index"})
    public String index(Model model){
        List<UpdateLog> list = updateLogService.findListLog();
        model.addAttribute("list",list);
        return "front/index";
    }

    @RequestMapping("/findNotification")
    @ResponseBody
    public MvcResult findNotification() {
        MvcResult result = MvcResult.create();
        try {
            SystemNotification notification = systemNotificationService.findLastNotification();
            if (notification==null){
                result.setSuccess(false);
                result.setMessage("无系统公告");
            }else {
                result.setData(notification);
            }
        }catch (Exception e){
            result.setSuccess(false);
            if (StringUtils.isNotEmpty(e.getMessage())){
                result.setMessage(e.getMessage());
            }else {
                result.setMessage("未知错误");
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public String search(@Param("name") String name, Model model){
        MvcResult result = bookAnalysisService.searchByName(name);
        if (!result.isSuccess()){
            model.addAttribute("msg",result.getMessage());
            return "public/error";
        }
        model.addAttribute("list",result.getVal("list"));
        model.addAttribute("name",name);
        return "front/search/list";
    }

    @RequestMapping("/book/detail")
    public String detail(String url, Model model) {
        MvcResult result = bookAnalysisService.loadBookDetail(url);
        if (!result.isSuccess()){
            model.addAttribute("msg",result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());


        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user!=null){
            BookInfo bookInfo = bookInfoService.getBookInfoByBookUrlAndUser(url,user);
            if (bookInfo!=null){
                if (StringUtils.isNotBlank(bookInfo.getLastReadCatalogLink())){
                    model.addAttribute("lastReadCatalogLink",bookInfo.getLastReadCatalogLink());
                    model.addAttribute("lastReadCatalogName",bookInfo.getLastReadCatalogName());
                }
            }
        }
        return "front/book/detail";
    }

    @RequestMapping("/book/reader")
    public String reader(String url,Model model){
        MvcResult result = bookAnalysisService.loadBookContent(url);
        if (!result.isSuccess()){
            model.addAttribute("msg",result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());
        model.addAttribute("currentUrl",url);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user!=null){
            BookInfo bookInfo = bookInfoService.getBookInfoByBookUrlAndUser(result.getVal("catalogs"),user);
            if (bookInfo!=null){
                bookInfo.setLastReadCatalogLink(url);
                bookInfo.setLastReadCatalogName(result.getVal("catalogName"));
                boolean isSuccess = bookInfoService.edit(bookInfo);
                if (!isSuccess){
                    logger.error("保存书籍阅读记录失败【"+ JSON.toJSONString(bookInfo) +"】");
                }
            }
        }


        return "front/book/reader";
    }

    @RequestMapping("/donate/list")
    public String donateList(Model model){
        List<Donate> list = donateService.getAll();
        if (list!= null && list.size()>0){
            model.addAttribute("list",list);
        }
        return "front/donate/list";
    }

    @RequestMapping("/book/loadCatalogs")
    @ResponseBody
    public MvcResult loadCatalogs(String url) {
        MvcResult result = bookAnalysisService.loadBookDetail(url);
        return result;
    }
}
