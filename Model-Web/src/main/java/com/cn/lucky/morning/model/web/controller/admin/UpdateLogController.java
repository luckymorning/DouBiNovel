package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.domain.UpdateLog;
import com.cn.lucky.morning.model.service.AuthorityGroupService;
import com.cn.lucky.morning.model.service.AuthorityService;
import com.cn.lucky.morning.model.service.UpdateLogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/updateLog")
public class UpdateLogController {
    @Resource
    private UpdateLogService updateLogService;

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
    @RequiresPermissions(value = {"UPDATE_LOG_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/updateLog/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"UPDATE_LOG_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(UpdateLog updateLog) {
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
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/edit")
    @RequiresPermissions(value = {"UPDATE_LOG_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        model.addAttribute("data", updateLogService.getById(id));
        return "admin/updateLog/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"UPDATE_LOG_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(UpdateLog updateLog) {
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
