package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.tool.Datas;
import com.cn.lucky.morning.model.domain.SystemSetting;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.MailService;
import com.cn.lucky.morning.model.service.SystemSettingService;
import com.cn.lucky.morning.model.service.UserService;
import com.cn.lucky.morning.model.web.tools.CodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Random;

@Controller
public class LoginRegisterController {
    @Resource
    private MailService mailService;
    @Resource
    private SystemSettingService systemSettingService;
    @Resource
    private UserService userService;
    @Resource
    private TemplateEngine templateEngine;


    @RequestMapping(method = RequestMethod.POST,value = "/register/sendRegisterMail")
    @ResponseBody
    public MvcResult sendRegisterMail(String email, HttpSession session){
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(email)){
            result.setSuccess(false);
            result.setMessage("发送验证码失败：邮箱不能为空");
        }else if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            result.setSuccess(false);
            result.setMessage("发送验证码失败：邮箱格式不正确");
        }else if (userService.getByPhoneOrCodeOrEmail(email)!=null){
            result.setSuccess(false);
            result.setMessage("发送验证码失败：该邮箱已被注册");
        }else {
            try {
                String code = String.format("%5d",new Random().nextInt(100000));
                SystemSetting setting = systemSettingService.getSetting();
                Context context = new Context();
                context.setVariable("siteName", setting.getSitename());
                context.setVariable("mail",email);
                context.setVariable("code",code);
                String emailContent = templateEngine.process("mail/register_mail", context);

                mailService.sendHtmlMail(email,String.format("欢迎注册 %s ",setting.getSitename()),emailContent);
                session.setAttribute(Const.session.REGISTER_CODE,code);
                session.setAttribute(Const.session.REGISTER_EMAIL,email);
            }catch (Exception e){
                result.setSuccess(false);
                if (StringUtils.isEmpty(e.getMessage())){
                    result.setMessage("发送验证码失败：原因未知");
                }else {
                    result.setMessage("发送验证码失败："+e.getMessage());
                }
            }
        }
        return result;
    }

    @RequestMapping(value = {"/register/index","/register/","/register"})
    public String register(){
        return "front/register";
    }

    @RequestMapping("/register/doRegister")
    @ResponseBody
    public MvcResult doRegister(User user,String registerCode, String rePassword,HttpSession session){
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(user.getEmail())){
            result.setSuccess(false);
            result.setMessage("注册失败：邮箱不能为空");
        }else if (StringUtils.isEmpty(user.getName())){
            result.setSuccess(false);
            result.setMessage("注册失败：用户昵称不能为空");
        }else if (StringUtils.isEmpty(user.getPassword())){
            result.setSuccess(false);
            result.setMessage("注册失败：用户密码不能为空");
        }else if (!user.getEmail().matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            result.setSuccess(false);
            result.setMessage("注册失败：邮箱格式不正确");
        }else if (!Objects.equals(user.getPassword(),rePassword)) {
            result.setSuccess(false);
            result.setMessage("注册失败：密码不一致");
        }else if (!Objects.equals(user.getEmail(),session.getAttribute(Const.session.REGISTER_EMAIL))){
            result.setSuccess(false);
            result.setMessage("注册失败：邮箱不一致不正确");
        }else if (!Objects.equals(registerCode,session.getAttribute(Const.session.REGISTER_CODE))){
            result.setSuccess(false);
            result.setMessage("注册失败：验证码不正确");
        }else {
            try {
                user.setRoleId(1001L);
                user.setCode(Datas.getUUID());
                user.setPassword(CodeUtils.MD5Pwd(user.getCode(),user.getPassword()));
                user.setDescription("平台前端普通用户");
                boolean isSuccess = userService.add(user);
                if (!isSuccess){
                    result.setSuccess(false);
                    result.setMessage("注册失败：原因未知");
                }else {
                    session.removeAttribute(Const.session.REGISTER_EMAIL);
                    session.removeAttribute(Const.session.REGISTER_CODE);
                }
            }catch (Exception e){
                result.setSuccess(false);
                if (StringUtils.isEmpty(e.getMessage())){
                    result.setMessage("注册失败：原因未知");
                }else {
                    result.setMessage("注册失败："+e.getMessage());
                }
            }
        }

        return result;
    }
}
