package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("view/assets/")
public class AssetsWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsService assetsService;

    public  String VIEW_PATH="view/assets/";

    @RequestMapping("/assets")
    public String studentOpen(){
        return VIEW_PATH+"assets";
    }

    @RequestMapping("/operate/drawer/{id}")
    public String change(@PathVariable(value = "id") String id){
        Assets assets = assetsService.getById(id);
        request.setAttribute("assets",assets);
        return  VIEW_PATH + "operate/drawer";
    }

    @RequestMapping("/operate/add")
    public String grouping(){
        return  VIEW_PATH + "operate/add";
    }

}
