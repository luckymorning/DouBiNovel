package com.cn.lucky.morning.model.web.controller.admin;

import com.cn.lucky.morning.model.common.base.BaseQuery;
import com.cn.lucky.morning.model.common.cache.CacheService;
import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.common.mvc.MvcResult;
import com.cn.lucky.morning.model.common.tool.IpUtil;
import com.cn.lucky.morning.model.domain.LoginLog;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.*;
import com.cn.lucky.morning.model.web.tools.CaptchaUtils;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {
    @Resource
    private CacheService cacheService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private UserService userService;
    @Resource
    private BookSourceService bookSourceService;
    @Resource
    private DonateService donateService;
    @Resource
    private UpdateLogService updateLogService;
    @Resource
    private SystemNotificationService systemNotificationService;

    @RequestMapping(value = {"", "/", "/index"})
    @RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String index(Model model) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "redirect:/admin/logout";
        }
        model.addAttribute("user", user);
        return "admin/index";
    }

    @RequestMapping("/welcome")
    @RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public String welcome(Model model) {
        long userCount = userService.countUser();
        model.addAttribute("userCount",userCount);

        long bookSourceCount = bookSourceService.countBookSource();
        model.addAttribute("bookSourceCount",bookSourceCount);

        long donateCount = donateService.countDonate();
        model.addAttribute("donateCount",donateCount);

        long updateLogCount = updateLogService.countUpdateLogs();
        model.addAttribute("updateLogCount",updateLogCount);

        BaseQuery notificationQuery = new BaseQuery();
        notificationQuery.set("order","id desc");
        notificationQuery.setSize(6);
        model.addAttribute("notifications",systemNotificationService.getByQuery(notificationQuery).getList());

        return "admin/welcome";
    }

    /**
     * 生成验证码
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/verificationCode")
    public void setVerificationCode(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        String random = CaptchaUtils.random();
        //输出图片
        CaptchaUtils.outputImage(random, response.getOutputStream());
        //存入结果
        session.setAttribute(Const.session.VERIFICATION_CODE, CaptchaUtils.num);
    }

    @RequestMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/admin/index";
        }
        return "admin/login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doLogin")
    @ResponseBody
    public MvcResult doLogin(String username, String password, String captcha, boolean rememberMe, HttpSession session, HttpServletRequest request) {
        MvcResult result = MvcResult.create(false);
        if (captcha == null || !Objects.equals(captcha, session.getAttribute(Const.session.VERIFICATION_CODE).toString())) {
            result.setMessage("验证码错误");
        } else if (StringUtils.isEmpty(username)) {
            result.setMessage("账号不能为空");
        } else if (StringUtils.isEmpty(password)) {
            result.setMessage("密码不能为空");
        } else {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            try {
                subject.login(token);
                if (subject.isAuthenticated()) {
                    try {
                        subject.checkPermission(Const.role.ROLE_SUPER);
                    } catch (Exception e) {
                        subject.checkPermission("ADMIN_VIEW");
                    }
                    result.setSuccess(true);
                    result.setMessage("登录成功");
                    User user = (User) subject.getPrincipal();
                    session.setAttribute(Const.session.LOGIN_ADMIN,user);
                    LoginLog loginLog = new LoginLog();
                    loginLog.setUserId(user.getId());
                    loginLog.setName(user.getName());
                    loginLog.setLoginType(0);
                    String ip = IpUtil.getIpAddr(request);
                    loginLog.setLoginIp(ip);
                    loginLogService.add(loginLog);
                } else {
                    result.setSuccess(false);
                    result.setMessage("登录失败");
                    token.clear();
                }
            } catch (UnknownAccountException uae) {
                result.setMessage("未知账户");
            } catch (IncorrectCredentialsException ice) {
                result.setMessage("密码不正确");
            } catch (LockedAccountException lae) {
                result.setMessage("账户已锁定");
            } catch (ExcessiveAttemptsException eae) {
                result.setMessage("用户名或密码错误次数过多");
            } catch (AuthenticationException ae) {
                result.setMessage("用户名或密码不正确");
            } catch (UnauthorizedException e) {
                result.setMessage("该账号无后台登录权限");
            } finally {
                if (!result.isSuccess()) {
                    token.clear();
                    subject.logout();
                }
            }
        }
        return result;
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "admin/login";
    }

    @RequestMapping("/clearCache")
    @ResponseBody
    @RequiresPermissions(value = {"ADMIN_VIEW", Const.role.ROLE_SUPER}, logical = Logical.OR)
    public Map<String, Object> clearCache() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            boolean isSuccess = cacheService.flushAll();
            if (isSuccess) {
                map.put("code", 1);
                map.put("msg", "清除成功");
            } else {
                map.put("code", 2);
                map.put("msg", "清除失败,redis清空失败");
            }
        } catch (Exception e) {
            map.put("code", 2);
            if (StringUtils.isEmpty(e.getMessage())) {
                map.put("msg", "未知异常");
            } else {
                map.put("msg", e.getMessage());
            }
        }
        return map;
    }
}
