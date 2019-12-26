package com.cn.lucky.morning.model.web.intercept;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.SystemSettingService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
        if (response.getStatus() == 200) {
            modelAndView.addObject("systemInfo", systemSettingService.getSetting());
            User user = null;

            HttpSession session = request.getSession();
            String url = request.getRequestURI();
            if (url != null && url.lastIndexOf("admin") >= 0) {
                user = (User) session.getAttribute(Const.session.LOGIN_ADMIN);
            } else {
                user = (User) session.getAttribute(Const.session.LOGIN_USER);
                if (user == null && (url.lastIndexOf("bookshelf")>=0 || url.lastIndexOf("user")>=0)){
                    response.sendRedirect("/index");
                }
            }
            if (user != null) {
                modelAndView.addObject("user", user);
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }

}
