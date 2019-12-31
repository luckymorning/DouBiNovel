package com.cn.lucky.morning.model.web.exception;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@ControllerAdvice
public class MyException {
    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception{
        String accept = req.getHeader("accept");
        if (e instanceof UnauthorizedException){
            if (accept.contains("text/html")){
                resp.sendRedirect("/403");
            }else {
                MvcResult result = MvcResult.createFail(403,"无访问权限");
                resp.getOutputStream().write(result.toString().getBytes("utf8"));
            }
        }else {
            String msg = e.getMessage();
            if (StringUtils.isBlank(msg)){
                msg = "未知错误";
            }
            if (accept.contains("text/html")){
                resp.sendRedirect("/msgError?msg="+ URLEncoder.encode(msg,"utf8"));
            }else {
                MvcResult result = MvcResult.createFail(500,msg);
                resp.getOutputStream().write(result.toString().getBytes("utf8"));
            }
        }
    }
}
