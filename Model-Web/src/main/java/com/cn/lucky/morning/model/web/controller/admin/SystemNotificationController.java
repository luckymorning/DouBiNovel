package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.SystemNotification;
import com.cn.lucky.morning.model.domain.SystemSetting;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/systemNotification")
@RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
public class SystemNotificationController {
    @Resource
    private SystemNotificationService systemNotificationService;
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private SystemSettingService systemSettingService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/systemNotification/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<SystemNotification> pageTemplate = systemNotificationService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/systemNotification/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(SystemNotification systemNotification, boolean isSendEmail) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(systemNotification.getTitle())) {
                result.setSuccess(false);
                result.setMessage("添加失败：标题不能为空");
            } else if (StringUtils.isEmpty(systemNotification.getContent())) {
                result.setSuccess(false);
                result.setMessage("添加失败：内容不能为空");
            } else {
                boolean success = systemNotificationService.add(systemNotification);
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("添加失败：未知原因");
                } else {
                    if (isSendEmail) {
                        List<User> list = userService.findAll();
                        SystemSetting setting = systemSettingService.getSetting();
                        String title = String.format("%s 系统公共 - %s", setting.getSitename(), systemNotification.getTitle());
                        List<String> tos = new ArrayList<>();
                        for (User user : list) {
                            if (!StringUtils.isEmpty(user.getEmail())) {
                                tos.add(user.getEmail());
                            }
                        }
                        mailService.sendAllHtmlMail(tos, title, systemNotification.getContent());
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/edit")
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        model.addAttribute("data", systemNotificationService.getById(id));
        return "admin/systemNotification/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(SystemNotification systemNotification, boolean isSendEmail) {
        MvcResult result = MvcResult.create();
        try {

            if (StringUtils.isEmpty(systemNotification.getTitle())) {
                result.setSuccess(false);
                result.setMessage("修改失败：标题不能为空");
            } else if (StringUtils.isEmpty(systemNotification.getContent())) {
                result.setSuccess(false);
                result.setMessage("修改失败：内容不能为空");
            } else {
                boolean success = systemNotificationService.edit(systemNotification);
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("修改失败：未知原因");
                } else {
                    if (isSendEmail) {
                        List<User> list = userService.findAll();
                        SystemSetting setting = systemSettingService.getSetting();
                        String title = String.format("%s 系统公共 - %s", setting.getSitename(), systemNotification.getTitle());
                        List<String> tos = new ArrayList<>();
                        for (User user : list) {
                            if (!StringUtils.isEmpty(user.getEmail())) {
                                tos.add(user.getEmail());
                            }
                        }
                        mailService.sendAllHtmlMail(tos, title, systemNotification.getContent());
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = systemNotificationService.delete(id);
            if (!success) {
                result.setSuccess(false);
                result.setMessage("删除失败：未知原因");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/deleteList")
    @ResponseBody
    @RequiresPermissions(value = {"SYSTEM_NOTIFICATION_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = systemNotificationService.deleteList(Arrays.asList(ids));
            if (!success) {
                result.setSuccess(false);
                result.setMessage("删除失败：未知原因");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }
}
