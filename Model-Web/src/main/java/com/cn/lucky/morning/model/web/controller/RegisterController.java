package com.cn.lucky.morning.model.web.controller;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.domain.SystemSetting;
import com.cn.lucky.morning.model.service.MailService;
import com.cn.lucky.morning.model.service.SystemSettingService;
import com.cn.lucky.morning.model.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Resource
    private MailService mailService;
    @Resource
    private SystemSettingService systemSettingService;
    @Resource
    private UserService userService;
    @Resource
    private TemplateEngine templateEngine;


    @RequestMapping(method = RequestMethod.POST,value = "/sendRegisterMail")
    @ResponseBody
    public MvcResult sendRegisterMail(String mail, HttpSession session){
        MvcResult result = MvcResult.create();
        if (StringUtils.isEmpty(mail)){
            result.setSuccess(false);
            result.setMessage("邮箱不能为空");
        }else if (!mail.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            result.setSuccess(false);
            result.setMessage("邮箱格式不正确");
        }else if (userService.getByPhoneOrCodeOrEmail(mail)!=null){
            result.setSuccess(false);
            result.setMessage("该邮箱已被注册");
        }else {
            try {
                String code = String.format("%4d",new Random().nextInt(10000));
                SystemSetting setting = systemSettingService.getSetting();
                Context context = new Context();
                context.setVariable("siteName", setting.getSitename());
                context.setVariable("mail",mail);
                context.setVariable("code",code);
                String emailContent = templateEngine.process("mail/register_mail", context);

                mailService.sendHtmlMail(mail,String.format("欢迎注册 %s ",setting.getSitename()),emailContent);
                session.setAttribute(Const.session.REGISTER_CODE,code);
            }catch (Exception e){
                result.setSuccess(false);
                if (StringUtils.isEmpty(e.getMessage())){
                    result.setMessage("发送验证码失败");
                }else {
                    result.setMessage(e.getMessage());
                }
            }
        }
        return result;
    }
}
