package com.minimalist.controller;

import com.minimalist.assets.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: minimalist
 * @description: 录像web
 * @author: pingc
 * @create: 2021-10-08 14:11
 **/
@Controller
@RequestMapping("/view/video")
public class VideoWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsService assetsService;

    public  String VIEW_PATH="view/video/";

    @RequestMapping("/video")
    public String video(){
        return VIEW_PATH+"video";
    }
    @RequestMapping("/play")
    public String play(String path){
        request.setAttribute("path",path);
        return VIEW_PATH+"play";
    }
}
