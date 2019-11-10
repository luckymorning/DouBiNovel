package com.cn.lucky.morning.model.web.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/search")
public class SearchController {
    @RequestMapping(method = RequestMethod.POST,value = "search")
    public String search(@Param("name") String name, Model model){
        if (StringUtils.isEmpty(name)){
            model.addAttribute("msg","搜索名称不能为空");
        }
        model.addAttribute("name",name);
        return "/search/list";
    }
}
