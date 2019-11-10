package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.service.AnalysisNovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private AnalysisNovelService analysisNovelService;
    @RequestMapping(method = RequestMethod.POST,value = "search")
    public String search(@Param("name") String name, Model model){
        if (StringUtils.isEmpty(name)){
            model.addAttribute("msg","搜索名称不能为空");
        }
        model.addAttribute("name",name);
        List<BookInfo> list = new ArrayList<>();
        list.addAll(analysisNovelService.searchByName("https://www.xbiquge6.com/search.php?keyword=%s",name));
        model.addAttribute("list",list);
        return "/search/list";
    }
}
