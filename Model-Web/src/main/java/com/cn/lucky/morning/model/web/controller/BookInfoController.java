package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.analysis.BiQuGe6NovelAnalysis;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.log.Logs;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import org.apache.commons.lang.StringUtils;
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
    private BiQuGe6NovelAnalysis biQuGe6NovelAnalysis;

    @RequestMapping("/detail")
    public String detail(String url, Model model) {
        if (StringUtils.isEmpty(url)){
            model.addAttribute("msg","解析地址不能为空");
            return "public/error";
        }else{
            MvcResult result;
            if (url.contains(Const.analysisSource.BI_QU_GE6)){
                result = biQuGe6NovelAnalysis.loadBookInfo(url);
                if (result.isSuccess()){
                    model.addAllAttributes(result.getValues());
                }else {
                    model.addAttribute("msg","解析出错："+result.getMessage());
                    return "public/error";
                }
            }else {
                model.addAttribute("msg","未知解析源");
                return "public/error";
            }
        }

        return "book/detail";
    }

    @RequestMapping("/reader")
    public String reader(String url,Model model){
        if (StringUtils.isEmpty(url)){
            model.addAttribute("msg","解析地址不能为空");
            return "/public/error";
        }else{

            MvcResult result;
            if (url.contains(Const.analysisSource.BI_QU_GE6)){
                result = biQuGe6NovelAnalysis.loadContent(url);
                if (result.isSuccess()){
                    model.addAllAttributes(result.getValues());
                }else {
                    model.addAttribute("msg","解析出错："+result.getMessage());
                    return "public/error";
                }
            }else {
                model.addAttribute("msg","未知解析源");
                return "public/error";
            }
        }
        return "book/reader";
    }
}
