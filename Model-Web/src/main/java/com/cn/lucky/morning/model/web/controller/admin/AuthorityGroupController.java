package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.service.AuthorityGroupService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/authorityGroup")
public class AuthorityGroupController {
    @Resource
    private AuthorityGroupService authorityGroupService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"AUTHORITY_GROUP_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/authorityGroup/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_GROUP_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<AuthorityGroup> pageTemplate = authorityGroupService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"AUTHORITY_GROUP_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/authorityGroup/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_GROUP_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(AuthorityGroup authorityGroup) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityGroupService.add(authorityGroup);
            if (!success) {
                result.setSuccess(false);
                result.setMessage("添加失败：未知原因");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/edit")
    @RequiresPermissions(value = {"AUTHORITY_GROUP_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        model.addAttribute("data", authorityGroupService.getById(id));
        return "admin/authorityGroup/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_GROUP_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(AuthorityGroup authorityGroup) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityGroupService.edit(authorityGroup);
            if (!success) {
                result.setSuccess(false);
                result.setMessage("修改失败：未知原因");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_GROUP_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityGroupService.delete(id);
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
    @RequiresPermissions(value = {"AUTHORITY_GROUP_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityGroupService.deleteList(Arrays.asList(ids));
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
