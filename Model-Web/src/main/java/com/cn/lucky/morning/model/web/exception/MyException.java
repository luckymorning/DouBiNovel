package com.cn.lucky.morning.model.web.exception;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.service.SystemSettingService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@ControllerAdvice
public class MyException {
    @Resource
    private SystemSettingService systemSettingService;

    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception {
        String msg = "未知错误";
        if (!StringUtils.isBlank(e.getMessage())) {
            msg = e.getMessage();
        }
        if (!isAjax(req)) {
            ModelAndView modelAndView = new ModelAndView();
            if (e instanceof UnauthorizedException) {
                modelAndView.addObject("msg", "无访问权限");
                modelAndView.setViewName("public/403");
            } else {
                modelAndView.addObject("msg", msg);
                modelAndView.setViewName("public/error");
            }
            modelAndView.addObject("systemInfo", systemSettingService.getSetting());
            return modelAndView;
        } else {
            MvcResult result = null;
            if (e instanceof UnauthorizedException) {
                result = MvcResult.createFail(403, "无访问权限");
            } else {
                result = MvcResult.createFail(500, msg);
            }
            return result;
        }
    }

    private boolean isAjax(HttpServletRequest req) {
        return (req.getHeader("X-Requested-With") != null) && "XMLHttpRequest".equals(req.getHeader("X-Requested-With").toString());
    }
}
