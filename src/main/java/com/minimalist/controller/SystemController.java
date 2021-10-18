package com.minimalist.controller;

import com.minimalist.menu.service.MenuService;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.timing.UserIsTiming;
import com.minimalist.util.GeneratorValidateCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/view/system")
public class SystemController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    MenuService menuService;
    //文件路径
    private static String VIEW_PATH = "view/system/";


    @RequestMapping(value = "/getValidateCode", method = RequestMethod.GET)
    @ResponseBody
    public String getValidateCode(HttpServletResponse response) {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        GeneratorValidateCode instance = new GeneratorValidateCode();

        //储存验证码到session中，key为ValidateCode
        request.getSession().setAttribute("ValidateCode", instance.getCode());

        try {
            instance.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }






    @RequestMapping("/logging")
    public String systemLogging(){
        return  VIEW_PATH + "logging";
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        /**
         * 用户登出，销毁认证
         */
        User user = (User) request.getSession().getAttribute("user");
        UserIsTiming.userCount.remove(user.getUsername());
        subject.logout();
        return "login";
    }



}







