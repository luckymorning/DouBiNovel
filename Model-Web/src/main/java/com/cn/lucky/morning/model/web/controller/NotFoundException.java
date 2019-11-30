package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.mvc.MvcResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class NotFoundException implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/403")
    public String error403(){
        return "public/403";
    }

    @RequestMapping("/404")
    public String error404(){
        return "public/404";
    }

    @RequestMapping(value = {"/msgError"})
    public String msgError(String msg, Model model){
        model.addAttribute("msg",msg);
        return "public/error";
    }

    @RequestMapping(value = {"/error"})
    public void error(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String accept = request.getHeader("accept");
        int code = response.getStatus();
        switch (code){
            case 404:
                if (accept.contains("text/html")){
                    response.sendRedirect("/404");
                }else {
                    MvcResult result = MvcResult.createFail(404,"路径不存在");
                    response.getOutputStream().write(result.toString().getBytes("utf8"));
                }
                break;
            default:
                if (accept.contains("text/html")){
                    response.sendRedirect("/msgError?msg="+ URLEncoder.encode("未知异常","utf8"));
                }else {
                    MvcResult result = MvcResult.createFail(2,"未知异常");
                    response.getOutputStream().write(result.toString().getBytes("utf8"));
                }
                break;
        }
    }

}
