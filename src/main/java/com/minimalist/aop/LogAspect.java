package com.minimalist.aop;

import com.minimalist.log.entity.Log;
import com.minimalist.log.service.LogService;
import com.minimalist.user.entity.User;
import com.minimalist.timing.UserIsTiming;
import com.minimalist.util.IpAndAddrUtil;
import com.minimalist.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.minimalist.*.controller.*.*(..)) || execution (public * com.minimalist.controller.TerminalWebController.index(..))")
    public void webLog(){}

    @Pointcut(" execution (public * com.minimalist.controller.*.*(..))")
    public void userCount(){}


    @Autowired
    LogService logService;

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object webLog(ProceedingJoinPoint pjp) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String browserName = IpAndAddrUtil.getBrowserName(request);
        String ip = IpAndAddrUtil.getIp(request);
        String osName = IpAndAddrUtil.getOsName(request);
        Log log = new Log();
        log.setBrowserName(browserName);
        log.setIp(ip);
        log.setOsName(osName);
        log.setCreationTime(new Date());
        log.setPath(request.getServletPath());
        log.setMethod(request.getMethod());

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String timeDate = formatter.format(new Date());
//        System.out.println("请求时间:"+ timeDate);
//        System.out.println("操作系统:"+osName);
//        System.out.println("浏览器:"+browserName);
//        System.out.println("请求url: " + request.getServletPath());
//        System.out.println("请求类型 : " + request.getMethod());
//        System.out.println("IP : " + ip);
        User user = (User)request.getSession().getAttribute("user");

        String username = "";
        if(user!=null){
           username =  user.getUsername();
        }
        if(request.getServletPath().equals("/user/login")){
            username = request.getParameter("username");
        }
        //            System.out.println("用户:"+username);
        log.setUserName(username);
        UserIsTiming.userCount.add(username);
        try {
            Object o =  pjp.proceed();
            String msg = "成功";
            if(o instanceof Result){
                 msg = ((Result) o).getMsg();
            }
//            System.out.println("结果是 :" + msg);
            log.setMsg(msg);
            logService.save(log);
            return o;
        } catch (Throwable e) {
            String msg = "失败";
            log.setMsg(msg);
            logService.save(log);
            e.printStackTrace();
            return null;
        }
    }
    @Around("userCount()")
    public Object userCount(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        User user = (User)request.getSession().getAttribute("user");

        String username = "";
        if(user!=null){
            username =  user.getUsername();
            UserIsTiming.userCount.add(username);
        }else if(request.getServletPath().equals("/user/login")){
            username = request.getParameter("username");
            UserIsTiming.userCount.add(username);
        }
        //            System.out.println("用户:"+username);
        return pjp.proceed();
    }
}
