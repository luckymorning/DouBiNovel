package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.BookInfo;
import com.cn.lucky.morning.model.domain.BookSource;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.BookAnalysisService;
import com.cn.lucky.morning.model.service.BookInfoService;
import com.cn.lucky.morning.model.service.BookSourceService;
import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/bookshelf")
public class BookShelfController {
    @Resource
    private BookInfoService bookInfoService;
    @Resource
    private BookSourceService bookSourceService;
    @Resource
    private BookAnalysisService bookAnalysisService;

    @RequestMapping("/index")
    public String index(){
        return "front/bookshelf/index";
    }

    @RequestMapping("/books")
    @ResponseBody
    public MvcResult books(BaseQuery query){
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            query.set("creatorId",user.getId());
            PageTemplate<BookInfo> pageTemplate = bookInfoService.getByQuery(query);
            result.setData(pageTemplate);
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage(e.getMessage());
            if (StringUtils.isBlank(e.getMessage())){
                result.setMessage("未知错误");
            }
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doAdd")
    @ResponseBody
    public MvcResult doAdd(String bookUrl) {
        MvcResult result = MvcResult.create();
        try {
            if (StringUtils.isBlank(bookUrl)){
                result.setSuccess(false);
                result.setMessage("链接不能为空");
            }else {
                List<BookSource> bookSources = bookSourceService.getAll();
                boolean bookSourceIsContainUrl = false;
                if (bookSources != null && bookSources.size() > 0){
                    for (BookSource bookSource : bookSources){
                        if (bookUrl.contains(bookSource.getBaseUrl())){
                            bookSourceIsContainUrl = true;
                            break;
                        }
                    }
                }
                if (!bookSourceIsContainUrl){
                    result.setSuccess(false);
                    result.setMessage("书源库中不包含此链接，无法加入书架中");
                }else {
                    User user = (User) SecurityUtils.getSubject().getPrincipal();
                    BaseQuery query = new BaseQuery();
                    query.set("creatorId",user.getId());
                    query.set("bookUrl",bookUrl);
                    PageTemplate<BookInfo> pageTemplate = bookInfoService.getByQuery(query);
                    if (pageTemplate.getTotalSize() > 0){
                        result.setSuccess(false);
                        result.setMessage("此书籍已存在");
                    }else {
                        result = bookAnalysisService.loadBookDetail(bookUrl);
                        if (result.isSuccess()){
                            BookInfo bookInfo = result.getVal("info");
                            bookInfo.setId(null);
                            bookInfo.setCreatorId(user.getId());
                            boolean isSuccess = bookInfoService.add(bookInfo);
                            if (!isSuccess){
                                result.setSuccess(false);
                                result.setMessage("加入书架失败，请稍后再试");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage(e.getMessage());
            if (StringUtils.isBlank(e.getMessage())) {
                result.setMessage("未知错误");
            }
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public MvcResult delete(Long id) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
           BookInfo bookInfo = bookInfoService.getById(id);
           if (bookInfo==null){
               result.setSuccess(false);
               result.setMessage("书籍不存在");
           }else if (!Objects.equal(bookInfo.getCreatorId(), user.getId())) {
                result.setSuccess(false);
                result.setMessage("只能删除自己的书籍");
            } else {
                boolean success = bookInfoService.delete(id);
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
    public MvcResult deleteList(Long[] ids) {
        MvcResult result = MvcResult.create();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            for (Long id : ids) {
                BookInfo bookInfo = bookInfoService.getById(id);
                if (bookInfo==null){
                    result.setSuccess(false);
                    result.setMessage("书籍不存在");
                    break;
                }else if (!Objects.equal(bookInfo.getCreatorId(), user.getId())) {
                    result.setSuccess(false);
                    result.setMessage("只能删除自己的书籍");
                    break;
                }
            }
            if (result.isSuccess()) {
                boolean success = bookInfoService.deleteList(Arrays.asList(ids));
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
