package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.*;
import com.cn.lucky.morning.model.service.DonateService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/donate")
public class DonateController {
    @Resource
    private DonateService donateService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"DONATE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/donate/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"DONATE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<Donate> pageTemplate = donateService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"DONATE_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/donate/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"DONATE_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(Donate data) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(data.getName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：捐赠人姓名不能为空");
            }else if (StringUtils.isEmpty(data.getNickName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：捐赠人昵称不能为空");
            }else if (StringUtils.isEmpty(data.getDonateMoney())) {
                result.setSuccess(false);
                result.setMessage("添加失败：捐赠金额不能为空");
            } else if (StringUtils.isEmpty(data.getDonateSource())) {
                result.setSuccess(false);
                result.setMessage("添加失败：捐赠方式不能为空");
            } else {
                boolean success = donateService.add(data);
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
    @RequiresPermissions(value = {"DONATE_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        Donate data = donateService.getById(id);
        model.addAttribute("data", data);
        return "admin/donate/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"DONATE_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(Donate data) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isEmpty(data.getName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：捐赠人姓名不能为空");
            }else if (StringUtils.isEmpty(data.getNickName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：捐赠人昵称不能为空");
            }else if (StringUtils.isEmpty(data.getDonateMoney())) {
                result.setSuccess(false);
                result.setMessage("修改失败：捐赠金额不能为空");
            } else if (StringUtils.isEmpty(data.getDonateSource())) {
                result.setSuccess(false);
                result.setMessage("修改失败：捐赠方式不能为空");
            } else {
                boolean success = donateService.edit(data);
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
    @RequiresPermissions(value = {"DONATE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = donateService.delete(id);
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
    @RequiresPermissions(value = {"DONATE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = donateService.deleteList(Arrays.asList(ids));
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
