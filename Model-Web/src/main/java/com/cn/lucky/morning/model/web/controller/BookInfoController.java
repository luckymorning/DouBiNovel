package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.analysis.novel.BiQuGe6NovelAnalysis;
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
    public String detail(String url, String source, Model model) {
        if (StringUtils.isEmpty(source)){
            model.addAttribute("msg","解析源不能为空");
            return "/public/error";
        }else if (StringUtils.isEmpty(url)){
            model.addAttribute("msg","解析地址不能为空");
            return "/public/error";
        }else{
            MvcResult result;
            switch (source){
                case Const.analysisSource.BI_QU_GE6:
                    result = biQuGe6NovelAnalysis.loadBookInfo(url);
                    if (result.isSuccess()){
                        model.addAllAttributes(result.getValues());
                    }else {
                        model.addAttribute("msg","解析出错："+result.getMessage());
                        return "error";
                    }
                    break;
                default:
                    model.addAttribute("msg","未知解析源");
                    return "error";
            }
        }

        return "book/detail";
    }
}
