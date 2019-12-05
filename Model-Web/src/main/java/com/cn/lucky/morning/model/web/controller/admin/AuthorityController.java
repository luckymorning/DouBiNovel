package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.service.AuthorityGroupService;
import com.cn.lucky.morning.model.service.AuthorityService;
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
@RequestMapping("/admin/authority")
public class AuthorityController {
    @Resource
    private AuthorityService authorityService;
    @Resource
    private AuthorityGroupService authorityGroupService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"AUTHORITY_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list(Model model) {
        List<AuthorityGroup> groups = authorityGroupService.getAll();
        model.addAttribute("groups", groups);
        return "admin/authority/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<Authority> pageTemplate = authorityService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"AUTHORITY_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add(Model model) {
        List<AuthorityGroup> groups = authorityGroupService.getAll();
        model.addAttribute("groups", groups);
        return "admin/authority/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(Authority authority) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(authority.getCode())) {
                result.setSuccess(false);
                result.setMessage("添加失败：code不能为空");
            } else if (authorityService.getByCode(authority.getCode())) {
                result.setSuccess(false);
                result.setMessage("添加失败：code已存在");
            } else if (StringUtils.isEmpty(authority.getName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：名称不能为空");
            } else if (authority.getGroupId() == null) {
                result.setSuccess(false);
                result.setMessage("添加失败：请选择所属权限组");
            } else {
                AuthorityGroup authorityGroup = authorityGroupService.getById(authority.getGroupId());
                if (authorityGroup == null) {
                    result.setSuccess(false);
                    result.setMessage("所属权限组不存在");
                } else {
                    authority.setGroupName(authorityGroup.getName());
                    boolean success = authorityService.add(authority);
                    if (!success) {
                        result.setSuccess(false);
                        result.setMessage("添加失败：未知原因");
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
    @RequiresPermissions(value = {"AUTHORITY_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        List<AuthorityGroup> groups = authorityGroupService.getAll();
        model.addAttribute("groups", groups);
        model.addAttribute("data", authorityService.getById(id));
        return "admin/authority/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"AUTHORITY_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(Authority authority) {
        MvcResult result = MvcResult.create();
        try {

            if (StringUtils.isEmpty(authority.getCode())) {
                result.setSuccess(false);
                result.setMessage("修改失败：code不能为空");
            } else if (StringUtils.isEmpty(authority.getName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：名称不能为空");
            } else if (authority.getGroupId() == null) {
                result.setSuccess(false);
                result.setMessage("修改失败：请选择所属权限组");
            } else {
                AuthorityGroup authorityGroup = authorityGroupService.getById(authority.getGroupId());
                if (authorityGroup == null) {
                    result.setSuccess(false);
                    result.setMessage("所属权限组不存在");
                } else {
                    Authority source = authorityService.getById(authority.getId());
                    if (!Objects.equals(source.getCode(), authority.getCode()) && authorityService.getByCode(authority.getCode())) {
                        result.setSuccess(false);
                        result.setMessage("修改失败：code已存在");
                    } else {
                        boolean success = authorityService.edit(authority);
                        if (!success) {
                            result.setSuccess(false);
                            result.setMessage("修改失败：未知原因");
                        }
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
    @RequiresPermissions(value = {"AUTHORITY_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityService.delete(id);
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
    @RequiresPermissions(value = {"AUTHORITY_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = authorityService.deleteList(Arrays.asList(ids));
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
