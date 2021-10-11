package com.minimalist.controller;

import com.alibaba.fastjson.JSONObject;
import com.minimalist.assets.entity.Assets;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: minimalist
 * @description:
 * @author: pingc
 * @create: 2021-07-30 17:21
 **/
@Controller
@RequestMapping("/view/userGroup")
public class UserGroupWebController {
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    //文件路径
    private static String VIEW_PATH = "view/userGroup/";
    @RequestMapping("/userGroup")
    public String systemUser(){
        return VIEW_PATH + "userGroup";
    }


    @RequestMapping("/operate/edit/{id}")
    public String systemEdit(@PathVariable(value = "id") String id){
        UserGroup userGroup = userGroupService.getById(id);

        ArrayList<HashMap<String, Object>> users = new ArrayList<HashMap<String, Object>>();
        for (String s : userGroup.getUserId().split(",")) {
            try {


            User use = userService.getById(s);
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name", use.getName());
            maps.put("value",use.getId());
            maps.put("disabled",false);
            maps.put("selected",true);
            users.add(maps);
            }catch (Exception e){}
        }
        request.setAttribute("userGroup",userGroup);
        request.setAttribute("user", JSONObject.toJSONString(users));
        return  VIEW_PATH + "operate/edit";
    }

    @RequestMapping({"/operate/add"})
    public String systemAdd(){
        return  VIEW_PATH + "operate/add";
    }
}
