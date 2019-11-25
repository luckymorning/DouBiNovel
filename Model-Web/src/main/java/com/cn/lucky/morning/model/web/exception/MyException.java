package com.cn.lucky.morning.model.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyException {
    @ExceptionHandler(value = UnauthorizedException.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception{
        resp.sendRedirect("/403");
    }
}
