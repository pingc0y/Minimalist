package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("view/assetsMy/")
public class AssetsMyWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsService assetsService;

    public  String VIEW_PATH="view/assetsMy/";

    @RequestMapping("/assetsMy")
    public String studentOpen(){
        return VIEW_PATH+"assetsMy";
    }

}
