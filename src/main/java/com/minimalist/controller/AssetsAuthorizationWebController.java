package com.minimalist.controller;

import com.alibaba.fastjson.JSONObject;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.service.AssetsUserService;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("view/assetsAuthorization/")
public class AssetsAuthorizationWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsAuthorizationService assetsAuthorizationService;
    @Autowired
    AssetsService assetsService;
    @Autowired
    AssetsUserService assetsUserService;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    UserService userService;

    public  String VIEW_PATH="view/assetsAuthorization/";

    @RequestMapping("/assetsAuthorization")
    public String studentOpen(){
        return VIEW_PATH+"assetsAuthorization";
    }

    @RequestMapping("/operate/drawer/{id}")
    public String change(@PathVariable(value = "id") String id){
        AssetsAuthorization assetsAuthorization = assetsAuthorizationService.getById(id);

        ArrayList<HashMap<String, Object>> assets = new ArrayList<>();
        for (String s : assetsAuthorization.getAssetsId().split(",")) {
            Assets ass = assetsService.getById(s);
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name", ass.getHostname());
            maps.put("value",ass.getId());
            maps.put("disabled",false);
            maps.put("selected",true);
            assets.add(maps);
        }

        ArrayList<HashMap<String, Object>> assetsUsers = new ArrayList<>();
        for (String s : assetsAuthorization.getAssetsUserId().split(",")) {
            AssetsUser assuse = assetsUserService.getById(s);
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name", assuse.getName());
            maps.put("value",assuse.getId());
            maps.put("disabled",false);
            maps.put("selected",true);
            assetsUsers.add(maps);
        }
        ArrayList<HashMap<String, Object>> users = new ArrayList<>();
        for (String s : assetsAuthorization.getUserId().split(",")) {
            User use = userService.getById(s);
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name", use.getName());
            maps.put("value",use.getId());
            maps.put("disabled",false);
            maps.put("selected",true);

            users.add(maps);
        }
        ArrayList<HashMap<String, Object>> userGroups = new ArrayList<>();
        for (String s : assetsAuthorization.getUserGroupId().split(",")) {
            UserGroup usegro = userGroupService.getById(s);
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name", usegro.getName());
            maps.put("value",usegro.getId());
            maps.put("disabled",false);
            maps.put("selected",true);
            userGroups.add(maps);
        }
        request.setAttribute("assetsAuthorization",assetsAuthorization);
        request.setAttribute("assets", JSONObject.toJSONString(assets));
        request.setAttribute("assetsUser",JSONObject.toJSONString(assetsUsers));
        request.setAttribute("user",JSONObject.toJSONString(users));
        request.setAttribute("userGroup",JSONObject.toJSONString(userGroups));
        return  VIEW_PATH + "operate/drawer";
    }

    @RequestMapping("/operate/add")
    public String grouping(){
        return  VIEW_PATH + "operate/add";
    }

}
