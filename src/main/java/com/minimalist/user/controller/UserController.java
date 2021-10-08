package com.minimalist.user.controller;



import com.minimalist.assets.entity.Assets;
import com.minimalist.user.entity.User;
import com.minimalist.user.service.UserService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap){
        int page =Integer.parseInt(conditionMap.remove("page"));
        int limit =Integer.parseInt(conditionMap.remove("limit"));
        Map<String,Object> map = userService.select(page,limit,conditionMap);
        return ResultUtil.success(map.get("data"),(int)map.get("count"));
    }

    @RequestMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        Map<String, Object> map = userService.selectByName(name);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        ArrayList<User> data = (ArrayList<User>) (map.get("data"));
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<HashMap<String, Object>>();
        for (User use : data) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name",use.getName());
            maps.put("value",use.getId());
            maps.put("disabled",false);
            maps.put("selected",false);
            hashMaps.add(maps);
        }
        return ResultUtil.success(hashMaps, (int) map.get("count"));
    }

    @RequestMapping("/updateById")
    public Result updateById(@RequestBody Map<String,String> map){
        User user = new User();
        user.setId(map.get("id"));
        user.setName(map.get("name"));
        user.setUsername(map.get("username"));
        user.setPassword(map.get("password"));
        boolean b = userService.updateById(user);
        return b ? ResultUtil.success() :ResultUtil.error(1,"修改失败");
    }

    @RequestMapping("/updateStatusById/{userId}/{status}")
    public Result updateStatusById(@PathVariable("userId")String userId,@PathVariable("status")String status){
        boolean b = userService.updateStatusById(userId,status);
        if(b){
            return  ResultUtil.success();
        }else {
            return  ResultUtil.error(1,"失败");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody User user){
        boolean save = userService.save(user);
        return save ? ResultUtil.success():ResultUtil.error(0,"失败");
    }

    @RequestMapping("/deleteUserById/{id}")
    public Result deleteUserById(@PathVariable(value = "id") String id){
        boolean b = false;
        for (String s : id.split(",")) {
            b =  userService.removeById(s);
        }
        return b ? ResultUtil.success(): ResultUtil.error(1,"失败");
    }

    @RequestMapping("/updatePassword/{id}")
    public Result updatePassword(@PathVariable(value="id") String id, @RequestBody Map<String,String> map){
        String passwordA = map.get("passwordA");
        String passwordB = map.get("passwordB");
        boolean b = false;
        User user = userService.getById(id);
        if(user.getPassword().equals(passwordA)){
            user.setPassword(passwordB);
            b = userService.updateById(user);
        }else{
            return b ? ResultUtil.success() :ResultUtil.error(1,"修改失败,原密码不正确");
        }
        return b ? ResultUtil.success() :ResultUtil.error(1,"修改失败");
    }

    @RequestMapping("/login")
    public Result login(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password,@RequestParam(value = "validateCode")  String validateCode){
        UsernamePasswordToken token = new  UsernamePasswordToken(username,password);
        Subject currentUser = SecurityUtils.getSubject();
        if(!((String)request.getSession().getAttribute("ValidateCode")).toLowerCase().equals(validateCode.toLowerCase())){
            return ResultUtil.error(1,"验证码错误！");
        }
        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
            return ResultUtil.error(1,"账号或密码错误！");
        }
        return ResultUtil.success();
    }



}


