package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.domain.UpdateLog;
import com.cn.lucky.morning.model.service.UpdateLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private UpdateLogService updateLogService;
    @RequestMapping({"/","/index"})
    public String index(Model model){
        List<UpdateLog> list = updateLogService.findLatest2Log();
        model.addAttribute("list",list);
        return "front/index";
    }
}
