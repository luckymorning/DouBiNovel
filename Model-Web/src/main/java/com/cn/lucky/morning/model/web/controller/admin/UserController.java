package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.Role;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.RoleService;
import com.cn.lucky.morning.model.service.UserService;
import com.cn.lucky.morning.model.web.tools.CodeUtils;
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
@RequestMapping("/admin/user")
@RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
public class UserController {
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"USER_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Role role = roleService.getById(user.getRoleId());
        List<Role> roles = roleService.findAll(Objects.equal(role.getIsSuper(),Const.role.IS_SUPER));
        model.addAttribute("roles", roles);
        return "admin/user/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"USER_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<User> pageTemplate = userService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"USER_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Role role = roleService.getById(user.getRoleId());
        List<Role> roles = roleService.findAll(Objects.equal(role.getIsSuper(),Const.role.IS_SUPER));
        model.addAttribute("roles", roles);
        return "admin/user/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"USER_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(User user) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(user.getName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：用户名称不能为空");
            }else if (user.getRoleId() == null){
                result.setSuccess(false);
                result.setMessage("添加失败：用户角色不能为空");
            }else if (StringUtils.isEmpty(user.getCode())){
                result.setSuccess(false);
                result.setMessage("添加失败：用户账号不能为空");
            }else if (StringUtils.isEmpty(user.getPassword())){
                result.setSuccess(false);
                result.setMessage("添加失败：用户密码不能为空");
            }else if (userService.codeIsExist(user.getCode(),null)){
                result.setSuccess(false);
                result.setMessage("添加失败：用户账号已存在");
            }else {
                user.setPassword(CodeUtils.MD5Pwd(user.getCode(),user.getPassword()));
                boolean success = userService.add(user);
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
    @RequiresPermissions(value = {"USER_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Role role = roleService.getById(user.getRoleId());
        List<Role> roles = roleService.findAll(Objects.equal(role.getIsSuper(),Const.role.IS_SUPER));
        model.addAttribute("roles", roles);
        User data = userService.getById(id);
        model.addAttribute("data", data);
        return "admin/user/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"USER_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(User user) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(user.getName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：用户名称不能为空");
            }else if (user.getRoleId() == null){
                result.setSuccess(false);
                result.setMessage("修改失败：用户角色不能为空");
            }else if (StringUtils.isEmpty(user.getCode())){
                result.setSuccess(false);
                result.setMessage("修改失败：用户账号不能为空");
            }else if (userService.codeIsExist(user.getCode(),user.getId())){
                result.setSuccess(false);
                result.setMessage("修改失败：用户账号已存在");
            }else {
                User source = userService.getById(user.getId());
                if (!StringUtils.isEmpty(user.getPassword())){
                    user.setPassword(CodeUtils.MD5Pwd(user.getCode(),user.getPassword()));
                }else if (!Objects.equal(user.getCode(),source.getCode())){
                    result.setSuccess(false);
                    result.setMessage("修改code,必须填写密码");
                }else {
                    user.setPassword(null);
                }
                if (result.isSuccess()){
                    boolean success = userService.edit(user);
                    if (!success) {
                        result.setSuccess(false);
                        result.setMessage("修改失败：未知原因");
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
    @RequiresPermissions(value = {"USER_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Role role = roleService.getById(user.getRoleId());
            User delUser = userService.getById(id);
            Role delUserRole = null;
            if (delUser!=null){
                delUserRole = roleService.getById(delUser.getRoleId());
            }
            if (Objects.equal(id, user.getId())) {
                result.setSuccess(false);
                result.setMessage("不能删除自己");
            } else if (delUserRole!=null && Objects.equal(delUserRole.getIsSuper(), Const.role.IS_SUPER) && !Objects.equal(role.getIsSuper(), Const.role.IS_SUPER)) {
                result.setSuccess(false);
                result.setMessage("无权限删除超级管理员");
            } else {
                boolean success = userService.delete(id);
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
    @RequiresPermissions(value = {"USER_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Role role = roleService.getById(user.getRoleId());
            for (Long id : ids) {
                User delUser = userService.getById(id);
                Role delUserRole = null;
                if (delUser!=null){
                    delUserRole = roleService.getById(delUser.getRoleId());
                }
                if (Objects.equal(id, user.getId())) {
                    result.setSuccess(false);
                    result.setMessage("不能删除自己");
                    break;
                } else if (delUserRole!=null && Objects.equal(delUserRole.getIsSuper(), Const.role.IS_SUPER) && !Objects.equal(role.getIsSuper(), Const.role.IS_SUPER)) {
                    result.setSuccess(false);
                    result.setMessage("无权限删除超级管理员");
                    break;
                }
            }
            if (result.isSuccess()) {
                boolean success = userService.deleteList(Arrays.asList(ids));
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

    @RequestMapping("/changePass")
    public String changePass(){
        return "admin/user/changePass";
    };

    @RequestMapping(method = RequestMethod.POST, value = "/doChangePass")
    @ResponseBody
    public MvcResult doChangePass(String password,String newPassword,String rePassword){
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if (user == null){
                result.setSuccess(false);
                result.setMessage("用户未登录");
            }else if (StringUtils.isBlank(newPassword)){
                result.setSuccess(false);
                result.setMessage("新密码不能为空");
            }else if (!Objects.equal(newPassword,rePassword)){
                result.setSuccess(false);
                result.setMessage("新密码与重复密码不一致");
            }else {
                user = userService.getById(user.getId());
                if(!Objects.equal(CodeUtils.MD5Pwd(user.getCode(),password),user.getPassword())){
                    result.setSuccess(false);
                    result.setMessage("旧密码错误");
                } else{
                    user.setPassword(CodeUtils.MD5Pwd(user.getCode(),newPassword));
                    if (!userService.edit(user)){
                        result.setSuccess(false);
                        result.setMessage("未知错误");
                    }
                }
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("未知错误,"+e.getMessage());
        }
        return result;
    }
}
