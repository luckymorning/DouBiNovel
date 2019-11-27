package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.base.PageTemplate;
import com.cn.lucky.morning.model.domain.AuthorityGroup;
import com.cn.lucky.morning.model.service.AuthorityGroupService;
import com.cn.lucky.morning.model.service.AuthorityService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin/authority")
public class AuthorityGroupController {
    @Autowired
    private AuthorityGroupService authorityGroupService;
    @Autowired
    private AuthorityService authorityService;

    @RequestMapping("/listJSON")
    @ResponseBody
    public Map<String,Object> listJSON(String name, Integer page, Integer limit){
        Map<String,Object> result = Maps.newHashMap();
        try{
            BaseQuery query = new BaseQuery();
            query.set("name",name);
            if (page!=null && page >0){
                query.setCurrent(page);
            }
            if (limit!=null && limit>0){
                query.setSize(limit);
            }
            PageTemplate<AuthorityGroup> pageTemplate = authorityGroupService.getByQuery(query);
            result.put("code",0);
            result.put("count",pageTemplate.getTotalSize());
            result.put("data",pageTemplate.getList());
        }catch (Exception e){
            result.put("code",-1);
            result.put("msg","获取出错,"+e.getMessage());
        }
        return result;
    }

    @RequestMapping("/list")
    public String list(){
        return "admin/authority_group/list";
    }
}
