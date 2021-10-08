package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.log.service.LogService;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.timing.UserIsTiming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    public String VIEW_PATH = "view/console/";

    @RequestMapping({"/","index"})
    public String index(){
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user",user);
        UserIsTiming.userCount.add(user.getUsername());
        return "index";
    }

    @RequestMapping({"/component/code/index"})
    public String component(){
        return "component/code/index";
    }

    @RequestMapping("/login*")
    public String login(){
        return "login";
    }

    //仪表盘
    @RequestMapping("/view/console/console")
    public String console1(){
        int users = logService.selectByUserCount();
        int count = logService.selectByCount();
        Map<String,String> map = logService.selectByUserKing();
        int userCount = UserIsTiming.userCount.size();
        List<User> list = userService.selectByTime(10);
        request.setAttribute("userCount",userCount);
        request.setAttribute("users",users);
        request.setAttribute("count",count);
        User user = userService.selectByname(map.get("name"));
        request.setAttribute("userking",user.getName());
        request.setAttribute("list",list);
        return VIEW_PATH+"console";
    }













}
