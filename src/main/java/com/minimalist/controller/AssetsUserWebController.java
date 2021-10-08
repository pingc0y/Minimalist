package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.service.AssetsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("view/assetsUser/")
public class AssetsUserWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsUserService assetsUserService;

    public  String VIEW_PATH="view/assetsUser/";

    @RequestMapping("/assetsUser")
    public String studentOpen(){
        return VIEW_PATH+"assetsUser";
    }

    @RequestMapping("/operate/drawer/{id}")
    public String change(@PathVariable(value = "id") String id){
        AssetsUser assetsUser = assetsUserService.getById(id);
        request.setAttribute("assetsUser",assetsUser);
        return  VIEW_PATH + "operate/drawer";
    }

    @RequestMapping("/operate/add")
    public String grouping(){
        return  VIEW_PATH + "operate/add";
    }

}
