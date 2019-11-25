package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @RequiresPermissions("USER_VIEW")
    public String list(){

        return "admin/user/list";
    }
}
