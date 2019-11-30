package com.cn.lucky.morning.model.web.exception;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyException {
    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception{
        String accept = req.getHeader("accept");
        if (e instanceof UnauthenticatedException){
            if (accept.contains("text/html")){
                resp.sendRedirect("/403");
            }else {
                MvcResult result = MvcResult.createFail(403,"无访问权限");
                resp.getOutputStream().write(result.toString().getBytes("utf8"));
            }
        }else {
            if (accept.contains("text/html")){
                resp.sendRedirect("/error");
            }else {
                MvcResult result = MvcResult.createFail(403,"无访问权限");
                resp.getOutputStream().write(result.toString().getBytes("utf8"));
            }
        }
    }
}
