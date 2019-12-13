package com.cn.lucky.morning.model.web.shiro;

import com.cn.lucky.morning.model.common.constant.Const;
import com.cn.lucky.morning.model.domain.Authority;
import com.cn.lucky.morning.model.domain.Role;
import com.cn.lucky.morning.model.domain.User;
import com.cn.lucky.morning.model.service.AuthorityService;
import com.cn.lucky.morning.model.service.RoleService;
import com.cn.lucky.morning.model.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userInfoService;
    @Resource
    private RoleService roleService;
    @Resource
    private AuthorityService authorityService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->com.cn.lucky.morning.model.web.shiro.MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user  = (User) principals.getPrimaryPrincipal();
        Role role = roleService.getById(user.getRoleId());
        if (role == null){
            return null;
        }
        authorizationInfo.addRole(role.getId().toString());
        if (Objects.equals(Const.role.IS_SUPER,role.getIsSuper())){
            authorizationInfo.addStringPermission(Const.role.ROLE_SUPER);
        }
        if (!StringUtils.isEmpty(role.getAuthority())){
            String [] authorityStrs = role.getAuthority().split(",");
            for (String id : authorityStrs){
                Authority authority = authorityService.getById(Long.valueOf(id));
                if (authority!=null){
                    authorizationInfo.addStringPermission(authority.getCode());
                }
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
//        System.out.println("com.cn.lucky.morning.model.web.shiro.MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String account = (String)token.getPrincipal();
//        System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User userInfo = userInfoService.getByPhoneOrCodeOrEmail(account);
//        System.out.println("----->>userInfo="+ JSON.toJSONString(userInfo));
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCode()+"salt"),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
