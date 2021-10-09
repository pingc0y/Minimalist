package com.minimalist.shiro;


import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection  principals) {
        //获取当前登录的用户的角色id
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");

        List<String> authority = new ArrayList<String>();
        authority.add(""+user.getRoles());
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        sa.addStringPermissions(authority);//把多个菜单权限给用户

        return sa;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken  actionToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) actionToken;
        // 从token中拿到用户名和密码
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //在这做数据库查询验证账号密码是否正确
        User user = userService.selectBynamePassword(username,password);
        AuthenticationInfo authcInfo = null;
        //模拟登录逻辑，正确返回AuthenticationInfo对象，错误返回null报错
        if(user!=null &&user.getStatus()==0) {
            /**
             * 登录成功
             * 创建SimpleAuthenticationInfo的对象
             * 构造函数有3个参数
             * 参数1、用户名
             * 参数2、密码
             * 参数3、当前realm对象的类名
             */
            authcInfo = new SimpleAuthenticationInfo(username,password,this.getName());
            /**
             * 把user放到session中
             * 这个session用的shiro的session
             * 兼容平常用的session，里面封装了更多的东西
             * 在其他的controller中可以使用我们平常使用的session来获取其中的内容
             */
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute("user", user);
            //登录后清除权限缓存
            clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        }
        return authcInfo;
    }

}


