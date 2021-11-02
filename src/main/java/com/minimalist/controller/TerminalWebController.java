package com.minimalist.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.service.AssetsUserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: minimalist
 * @description:
 * @author: pingc
 * @create: 2021-07-31 17:35
 **/
@Controller
@RequestMapping("view/terminal/")
public class TerminalWebController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AssetsUserService assetsUserService;
    @Autowired
    AssetsService assetsService;

    public  String VIEW_PATH="view/terminal/";

    @RequestMapping("/terminal")
    public String terminal(String assets,String assetsUser){
        String[] assetsUserId = assetsUser.split(",");
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        String protocol = "";
        for (String s : assetsUserId) {
            AssetsUser assetsU = assetsUserService.getById(s);
            if(assetsU!=null) {
                HashMap<String, String> map = new HashMap<>();
                String username = assetsU.getUsername();
                protocol = assetsU.getProtocol();
                map.put("id", s);
                map.put("username", username);
                hashMaps.add(map);
            }
        }
        String hostname = assetsService.getById(assets).getHostname();
        request.setAttribute("assetsId",assets);
        request.setAttribute("hostname",hostname);
        request.setAttribute("assetsUserId",hashMaps);
        request.setAttribute("assetsUserCount",hashMaps.size());
        request.setAttribute("protocol",protocol);
        return VIEW_PATH+"terminal";
    }


    @RequestMapping("/index/{id}")
    public String index(@PathVariable String id){
        request.setAttribute("id",id);
        return VIEW_PATH+"index";
    }



}
