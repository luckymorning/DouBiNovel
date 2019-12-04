package com.cn.lucky.morning.model.web.intercept;

import com.cn.lucky.morning.model.service.SystemSettingService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private SystemSettingService systemSettingService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            super.postHandle(request, response, handler, modelAndView);
            return;
        }
        if (response.getStatus() == 200){
            modelAndView.addObject("systemInfo",systemSettingService.getSetting());
        }
        super.postHandle(request, response, handler, modelAndView);
    }

}
