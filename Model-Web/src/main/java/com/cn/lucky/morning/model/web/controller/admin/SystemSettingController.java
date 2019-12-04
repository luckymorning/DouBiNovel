package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.SystemSetting;
import com.cn.lucky.morning.model.service.SystemSettingService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/setting")
public class SystemSettingController {
    @Resource
    private SystemSettingService systemSettingService;

    @RequestMapping(value = {"","/","index"})
    @RequiresPermissions(value = {"SYSTEM_SETTING_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String index(Model model){
        SystemSetting setting = systemSettingService.getSetting();
        model.addAttribute("data",setting);
        return "admin/setting/setting";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_SETTING_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(SystemSetting setting){
        MvcResult result = MvcResult.create();
        try{
            boolean isSuccess = systemSettingService.edit(setting);
            if (!isSuccess){
                result.setSuccess(false);
                result.setMessage("修改失败：未知原因");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(2);
            String message = e.getMessage();
            if (StringUtils.isEmpty(message)){
                message = "未知原因";
            }
            result.setMessage("修改失败："+message);
        }
        return result;
    }
}
