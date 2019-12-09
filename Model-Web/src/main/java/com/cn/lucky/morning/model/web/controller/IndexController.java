package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.Donate;
import com.cn.lucky.morning.model.domain.UpdateLog;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.service.DonateService;
import com.cn.lucky.morning.model.service.UpdateLogService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private UpdateLogService updateLogService;
    @Resource
    private BookAnalysisService bookAnalysisService;
    @Resource
    private DonateService donateService;


    @RequestMapping({"/","/index"})
    public String index(Model model){
        List<UpdateLog> list = updateLogService.findLatest2Log();
        model.addAttribute("list",list);
        return "front/index";
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
}
