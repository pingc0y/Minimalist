package com.minimalist.userGroup.controller;

import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.log.service.LogService;
import com.minimalist.user.entity.User;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.service.UserGroupService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    @Autowired
    UserGroupService userGroupService;


    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap){
        int page =Integer.parseInt(conditionMap.remove("page"));
        int limit =Integer.parseInt(conditionMap.remove("limit"));
        Map<String,Object> map = userGroupService.select(page,limit,conditionMap);
        return ResultUtil.success(map.get("data"),(int)map.get("count"));
    }

    @RequestMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        Map<String, Object> map = userGroupService.selectByName(name);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        ArrayList<UserGroup> data = (ArrayList<UserGroup>) (map.get("data"));
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<HashMap<String, Object>>();
        for (UserGroup ass : data) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name",ass.getName());
            maps.put("value",ass.getId());
            maps.put("disabled",false);
            maps.put("selected",false);
            hashMaps.add(maps);
        }
        return ResultUtil.success(hashMaps, (int) map.get("count"));
    }


    @RequestMapping("/updateById")
    public Result updateById(@RequestBody Map<String,String> map){
        UserGroup userGroup = new UserGroup();
        userGroup.setId(map.get("id"));
        userGroup.setName(map.get("name"));
        if(map.get("userId")!=null && map.get("userId").length() >1) {
            userGroup.setUserId(map.get("userId").replace("\"", ""));
        }
        boolean b = userGroupService.updateById(userGroup);
        return b ? ResultUtil.success() :ResultUtil.error(1,"修改失败");
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,String> map){
        UserGroup userGroup = new UserGroup();
        userGroup.setName(map.get("name"));
        userGroup.setUserId(map.get("userId").toString().replace("\"",""));
        boolean b = userGroupService.save(userGroup);
        return b ? ResultUtil.success():ResultUtil.error(0,"失败");
    }

    @RequestMapping("/deleteById/{id}")
    public Result deleteUserById(@PathVariable(value = "id") String id){
        boolean b = false;
        for (String s : id.split(",")) {
            b =  userGroupService.removeById(s);
        }
        return b ? ResultUtil.success(): ResultUtil.error(1,"失败");
    }


}
