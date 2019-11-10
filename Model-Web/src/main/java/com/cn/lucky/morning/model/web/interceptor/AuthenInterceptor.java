package com.cn.lucky.morning.model.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class AuthenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //执行链接地址请求前
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute(Const.session.LOGIN_ADMIN);
//        if (user == null) {
////            String url = request.getRequestURI();
////            if(url != null && url.lastIndexOf("admin") >= 0)
////                response.sendRedirect("/admin/login");
////            else
////                response.sendRedirect("/login");
//
//            response.sendRedirect("/admin/login");
//            return false;
//        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //执行链接请求后到加载页面之间
//        if (modelAndView == null) {
//            super.postHandle(request, response, handler, modelAndView);
//            return;
//        }
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute(Const.session.LOGIN_ADMIN);
//        if (user != null) {
//            AuthTool authTool = new AuthTool(user, roleService, authorityService);
//            authTool.auths();
//            modelAndView.addObject("auth", authTool);
//            modelAndView.addObject("user", user);
//            modelAndView.addObject("role", roleService.getById(user.getRoleId()));
//        }
//        String url = request.getRequestURI();
//        if(response.getStatus()==500){
//            if(url != null && url.lastIndexOf("admin") >= 0)
//                modelAndView.setViewName("/public/welcome");//应该为错误页面
//        }else if(response.getStatus()==404){
//            if(url != null && url.lastIndexOf("admin") >= 0)
//                modelAndView.setViewName("/public/404");//应该为404错误页面
//        }
        super.postHandle(request, response, handler, modelAndView);
    }

}
