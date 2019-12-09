package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.analysis.BookSourceAnalysis;
import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.service.BookSourceService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/bookSource")
public class BookSourceController {
    @Resource
    private BookSourceService bookSourceService;
    @Resource
    private BookSourceAnalysis bookSourceAnalysis;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"BOOK_SOURCE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String list() {
        return "admin/bookSource/list";
    }

    @RequestMapping("/listJSON")
    @ResponseBody
    @RequiresPermissions(value = {"BOOK_SOURCE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult listJSON(BaseQuery query) {
        MvcResult result = MvcResult.create();
        try {
            PageTemplate<BookSource> pageTemplate = bookSourceService.getByQuery(query);
            result.setData(pageTemplate);
        } catch (Exception e) {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("获取出错," + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    @RequiresPermissions(value = {"BOOK_SOURCE_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String add() {
        return "admin/bookSource/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    @RequiresPermissions(value = {"BOOK_SOURCE_ADD", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doAdd(BookSource bookSource) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isBlank(bookSource.getName())) {
                result.setSuccess(false);
                result.setMessage("添加失败：书源名称不能为空");
            } else if (StringUtils.isBlank(bookSource.getBaseUrl())) {
                result.setSuccess(false);
                result.setMessage("添加失败：书源根路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchUrl())) {
                result.setSuccess(false);
                result.setMessage("添加失败：书源查询路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchUrl())) {
                result.setSuccess(false);
                result.setMessage("添加失败：书源查询路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchResultSelector())) {
                result.setSuccess(false);
                result.setMessage("添加失败：结果集筛选不能为空");
            } else if (StringUtils.isBlank(bookSource.getResultItemBookNameSelector())) {
                result.setSuccess(false);
                result.setMessage("添加失败：结果集书籍名称筛选不能为空");
            } else if (StringUtils.isBlank(bookSource.getResultItemBookUrlSelector())) {
                result.setSuccess(false);
                result.setMessage("添加失败：结果集书籍详情地址筛选不能为空");
            } else {
                if (!bookSource.getSearchUrl().startsWith("/")) {
                    bookSource.setSearchUrl("/" + bookSource.getSearchUrl());
                }
                while (bookSource.getBaseUrl().endsWith("/")) {
                    bookSource.setBaseUrl(bookSource.getBaseUrl().substring(0, bookSource.getBaseUrl().length() - 1));
                }
                boolean success = bookSourceService.add(bookSource);
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
    @RequiresPermissions(value = {"BOOK_SOURCE_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String edit(Long id, Model model) {
        model.addAttribute("data", bookSourceService.getById(id));
        return "admin/bookSource/edit";
    }

    @RequestMapping("/doEdit")
    @ResponseBody
    @RequiresPermissions(value = {"BOOK_SOURCE_UPDATE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult doEdit(BookSource bookSource) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isBlank(bookSource.getName())) {
                result.setSuccess(false);
                result.setMessage("修改失败：书源名称不能为空");
            } else if (StringUtils.isBlank(bookSource.getBaseUrl())) {
                result.setSuccess(false);
                result.setMessage("修改失败：书源根路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchUrl())) {
                result.setSuccess(false);
                result.setMessage("修改失败：书源查询路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchUrl())) {
                result.setSuccess(false);
                result.setMessage("修改失败：书源查询路径不能为空");
            } else if (StringUtils.isBlank(bookSource.getSearchResultSelector())) {
                result.setSuccess(false);
                result.setMessage("修改失败：结果集筛选不能为空");
            } else if (StringUtils.isBlank(bookSource.getResultItemBookNameSelector())) {
                result.setSuccess(false);
                result.setMessage("修改失败：结果集书籍名称筛选不能为空");
            } else if (StringUtils.isBlank(bookSource.getResultItemBookUrlSelector())) {
                result.setSuccess(false);
                result.setMessage("修改失败：结果集书籍详情地址筛选不能为空");
            } else {
                if (!bookSource.getSearchUrl().startsWith("/")) {
                    bookSource.setSearchUrl("/" + bookSource.getSearchUrl());
                }
                while (bookSource.getBaseUrl().endsWith("/")) {
                    bookSource.setBaseUrl(bookSource.getBaseUrl().substring(0, bookSource.getBaseUrl().length() - 1));
                }
                boolean success = bookSourceService.edit(bookSource);
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
    @RequiresPermissions(value = {"BOOK_SOURCE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = bookSourceService.delete(id);
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
    @RequiresPermissions(value = {"BOOK_SOURCE_DELETE", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            boolean success = bookSourceService.deleteList(Arrays.asList(ids));
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

    @RequestMapping("/doTest")
    @ResponseBody
    @RequiresPermissions(value = {"BOOK_SOURCE_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public MvcResult bookSourceTest(@Param("key") String key, BookSource bookSource, int type) {
        MvcResult result;
        try {
            switch (type) {
                case 0:
                    result = bookSourceAnalysis.searchByName(key, bookSource).get();
                    break;
                case 1:
                    result = bookSourceAnalysis.loadBookInfo(key, bookSource).get();
                    break;
                case 2:
                    result = bookSourceAnalysis.loadContent(key, bookSource).get();
                    break;
                default:
                    result = MvcResult.createFail("未知测试类型");
                    break;
            }
        } catch (Exception e) {
            result = MvcResult.createFail(2, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
