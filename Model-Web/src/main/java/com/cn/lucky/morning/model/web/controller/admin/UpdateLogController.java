package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.tool.Dates;
import com.cn.lucky.morning.model.domain.*;
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
import java.util.*;

@Controller
@RequestMapping("/admin/updateLog")
@RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
public class UpdateLogController {
    @Resource
    private UpdateLogService updateLogService;
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private SystemSettingService systemSettingService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"UPDATE_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/updateLog/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"UPDATE_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<UpdateLog> pageTemplate = updateLogService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"UPDATE_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/updateLog/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"UPDATE_LOG_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(UpdateLog updateLog, boolean isSendEmail) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(updateLog.getUpdateDes())) {
                result.setSuccess(false);
                result.setMessage("添加失败：内容不能为空");
            } else {
                boolean success = updateLogService.add(updateLog);
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("添加失败：未知原因");
                } else {
                    if (isSendEmail) {
                        List<User> list = userService.findAll();
                        SystemSetting setting = systemSettingService.getSetting();
                        String title = String.format("%s 网站版本更新 - " + Dates.format(new Date(), "yyyy-MM-dd"), setting.getSitename());
                        List<String> tos = new ArrayList<>();
                        for (User user : list) {
                            if (!StringUtils.isEmpty(user.getEmail())) {
                                tos.add(user.getEmail());
                            }
                        }
                        mailService.sendAllHtmlMail(tos, title, updateLog.getUpdateDes());
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
    @RequiresPermissions(value = {"UPDATE_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        model.addAttribute("data", updateLogService.getById(id));
        return "admin/updateLog/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"UPDATE_LOG_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(UpdateLog updateLog, boolean isSendEmail) {
        MvcResult result = MvcResult.create();
        try {

            if (StringUtils.isEmpty(updateLog.getUpdateDes())) {
                result.setSuccess(false);
                result.setMessage("修改失败：内容不能为空");
            } else {
                boolean success = updateLogService.edit(updateLog);
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("修改失败：未知原因");
                }else {
                    if (isSendEmail) {
                        List<User> list = userService.findAll();
                        SystemSetting setting = systemSettingService.getSetting();
                        String title = String.format("%s 网站版本更新 - " + Dates.format(new Date(), "yyyy-MM-dd"), setting.getSitename());
                        List<String> tos = new ArrayList<>();
                        for (User user : list) {
                            if (!StringUtils.isEmpty(user.getEmail())) {
                                tos.add(user.getEmail());
                            }
                        }
                        mailService.sendAllHtmlMail(tos, title, updateLog.getUpdateDes());
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
    @RequiresPermissions(value = {"UPDATE_LOG_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = updateLogService.delete(id);
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
    @RequiresPermissions(value = {"UPDATE_LOG_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = updateLogService.deleteList(Arrays.asList(ids));
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
