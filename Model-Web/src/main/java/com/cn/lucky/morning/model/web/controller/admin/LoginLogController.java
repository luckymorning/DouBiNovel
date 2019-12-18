package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.LoginLog;
import com.cn.lucky.morning.model.service.LoginLogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/loginLog")
@RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
public class LoginLogController {
    @Resource
    private LoginLogService loginLogService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"LOGIN_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/loginLog/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"LOGIN_LOG_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<LoginLog> pageTemplate = loginLogService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value = {"LOGIN_LOG_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = loginLogService.delete(id);
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
    @RequiresPermissions(value = {"LOGIN_LOG_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = loginLogService.deleteList(Arrays.asList(ids));
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
