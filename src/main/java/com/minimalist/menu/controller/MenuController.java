package com.minimalist.menu.controller;


import com.alibaba.druid.sql.visitor.functions.If;
import com.minimalist.menu.entity.Menu;
import com.minimalist.menu.service.MenuService;
import com.minimalist.user.entity.User;
import com.minimalist.util.ReflectUtil;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/selectAll")
    public List<Menu> selectAll(){
        User user = (User)request.getSession().getAttribute("user");
        List<Menu> menus = new ArrayList<>();
        if(user.getRoles()==1){
             menus = menuService.selectAll();

        }else if(user.getRoles()==2){
            menus = menuService.selectUser();
        }

        return menus;
    }

    @RequestMapping("/select")
    public Result select(){
        List<Menu> menus = menuService.list();
        return ResultUtil.success(menus, menus.size());
    }

    @RequestMapping("/update")
    public Result updateById(@RequestBody Menu menu){
        boolean b = menuService.updateById(menu);
        return b ? ResultUtil.success() : ResultUtil.error(1,"修改失败");
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        boolean b = menuService.save(menu);
        return b ? ResultUtil.success() : ResultUtil.error(1,"新增失败");
    }



}

