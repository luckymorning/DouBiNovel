package com.cn.lucky.morning.model.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/","/index"})
    public String index(){
        return "front/index";
    }
}
