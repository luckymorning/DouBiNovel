package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.domain.Role;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.AuthorityService;
import com.cn.lucky.morning.model.service.RoleService;
import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private AuthorityService authorityService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"ROLE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/role/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"ROLE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<Role> pageTemplate = roleService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"ROLE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add(Model model) {
        Map<AuthorityGroup, List<Authority>> authorities = authorityService.getAuthority();
        model.addAttribute("authorities", authorities);
        return "admin/role/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"ROLE_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(Role role) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(role.getName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：角色名称不能为空");
            } else {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                Role userRole = roleService.getById(user.getRoleId());
                if (!Objects.equal(userRole.getIsSuper(), Const.role.IS_SUPER)) {
                    role.setIsSuper(Const.role.NOT_SUPER);
                }
                role.setCreatorId(user.getId());
                boolean success = roleService.add(role);
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
    @RequiresPermissions(value = {"ROLE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        Map<AuthorityGroup, List<Authority>> authorities = authorityService.getAuthority();
        model.addAttribute("authorities", authorities);
        Role role = roleService.getById(id);
        if (!StringUtils.isEmpty(role.getAuthority())) {
            model.addAttribute("roleAuth", Arrays.asList(role.getAuthority().split(",")));
        }
        model.addAttribute("data", role);
        return "admin/role/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"ROLE_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(Role role) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(role.getName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：角色名称不能为空");
            } else {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                Role userRole = roleService.getById(user.getRoleId());
                if (!Objects.equal(userRole.getIsSuper(), Const.role.IS_SUPER)) {
                    role.setIsSuper(Const.role.NOT_SUPER);
                }
                boolean success = roleService.edit(role);
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
    @RequiresPermissions(value = {"ROLE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Role role = roleService.getById(user.getRoleId());
            Role delRole = roleService.getById(id);
            if (Objects.equal(id, role.getId())) {
                result.setSuccess(false);
                result.setMessage("不能删除自己的角色");
            } else if (delRole != null && Objects.equal(delRole.getIsSuper(), Const.role.IS_SUPER) && !Objects.equal(role.getIsSuper(), Const.role.IS_SUPER)) {
                result.setSuccess(false);
                result.setMessage("无权限删除超级管理员");
            } else {
                boolean success = roleService.delete(id);
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("删除失败：未知原因");
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/deleteList")
    @ResponseBody
    @RequiresPermissions(value = {"ROLE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Role role = roleService.getById(user.getRoleId());
            for (Long id : ids) {
                Role delRole = roleService.getById(id);
                if (Objects.equal(id, role.getId())) {
                    result.setSuccess(false);
                    result.setMessage("不能删除自己的角色");
                    break;
                } else if (delRole != null && Objects.equal(delRole.getIsSuper(), Const.role.IS_SUPER) && !Objects.equal(role.getIsSuper(), Const.role.IS_SUPER)) {
                    result.setSuccess(false);
                    result.setMessage("无权限删除超级管理员");
                    break;
                }
            }
            if (result.isSuccess()) {
                boolean success = roleService.deleteList(Arrays.asList(ids));
                if (!success) {
                    result.setSuccess(false);
                    result.setMessage("删除失败：未知原因");
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }
}
