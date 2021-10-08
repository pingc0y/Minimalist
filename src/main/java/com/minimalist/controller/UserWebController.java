package com.minimalist.controller;

import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: minimalist
 * @description:
 * @author: pingc
 * @create: 2021-07-30 17:21
 **/
@Controller
@RequestMapping("/view/user")
public class UserWebController {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;
    //文件路径
    private static String VIEW_PATH = "view/user/";
    @RequestMapping("/user")
    public String systemUser(){
        return VIEW_PATH + "user";
    }


    @RequestMapping("/operate/edit/{id}")
    public String systemEdit(@PathVariable(value = "id") String id){
        User user = userService.getById(id);
        request.setAttribute("user",user);
        return  VIEW_PATH + "operate/edit";
    }

    @RequestMapping({"/operate/add"})
    public String systemAdd(){
        return  VIEW_PATH + "operate/add";
    }
}
