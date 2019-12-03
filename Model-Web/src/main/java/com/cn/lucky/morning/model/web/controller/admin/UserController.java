package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/list")
    @RequiresPermissions("USER_VIEW")
    public String list() {

        return "admin/index";
    }

    @RequestMapping("/doAdd")
    @RequiresPermissions("USER_ADD")
    public String doAdd() {

        return "admin/user/add";
    }
}
