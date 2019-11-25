package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BookInfoController {
    private static final Logger logger = Logs.get();
    @Autowired
    private BookAnalysisService bookAnalysisService;

    @RequestMapping("/detail")
    public String detail(String url, Model model) {
        MvcResult result = bookAnalysisService.loadBookDetail(url);
        if (!result.isSuccess()){
            model.addAttribute("msg",result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());
        return "front/book/detail";
    }

    @RequestMapping("/reader")
    public String reader(String url,Model model){
        MvcResult result = bookAnalysisService.loadBookContent(url);
        if (!result.isSuccess()){
            model.addAttribute("msg",result.getMessage());
            return "public/error";
        }
        model.addAllAttributes(result.getValues());
        return "front/book/reader";
    }
}
