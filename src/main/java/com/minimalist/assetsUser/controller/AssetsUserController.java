package com.minimalist.assetsUser.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.service.AssetsUserService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/assetsUser")
public class AssetsUserController {
    @Autowired
    AssetsUserService assetsUserService;

    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap) {
        int page = Integer.parseInt(conditionMap.remove("page"));
        int limit = Integer.parseInt(conditionMap.remove("limit"));
        Map<String, Object> map = assetsUserService.select(page, limit, conditionMap);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        return ResultUtil.success(map.get("data"), (int) map.get("count"));

    }

    @RequestMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        Map<String, Object> map = assetsUserService.selectByName(name);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        ArrayList<AssetsUser> data = (ArrayList<AssetsUser>) (map.get("data"));
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<HashMap<String, Object>>();
        for (AssetsUser ass : data) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name",ass.getName());
            maps.put("value",ass.getId());
            maps.put("disabled",false);
            maps.put("selected",false);
            hashMaps.add(maps);
        }
        return ResultUtil.success(hashMaps, (int) map.get("count"));
    }

    //根据ID修改资产
    @RequestMapping("/updateById")
    public Result updateById(@RequestBody Map<String,String> map){
        try {
            AssetsUser assetsUser = new AssetsUser();
            assetsUser.setId(map.get("id"));
            assetsUser.setName(map.get("name"));
            assetsUser.setUsername(map.get("username"));
            assetsUser.setProtocol(map.get("protocol"));
            assetsUser.setPassword(map.get("password"));
            assetsUser.setSecretKey(map.get("secretKey"));
            assetsUser.setRemark(map.get("remark"));
            assetsUser.setActiveDirectory(map.get("activeDirectory"));
            boolean b = assetsUserService.updateById(assetsUser);
            return b ? ResultUtil.success() : ResultUtil.error(1, "修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(1, "修改失败");
        }
    }

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,String> map){
        try {
            AssetsUser assetsUser = new AssetsUser();
            assetsUser.setName(map.get("name"));
            assetsUser.setUsername(map.get("username"));
            assetsUser.setProtocol(map.get("protocol"));
            assetsUser.setPassword(map.get("password"));
            assetsUser.setSecretKey(map.get("secretKey"));
            assetsUser.setRemark(map.get("remark"));
            assetsUser.setActiveDirectory(map.get("activeDirectory"));
            boolean b = assetsUserService.save(assetsUser);
            return b ? ResultUtil.success() :ResultUtil.error(1,"新增失败");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(1, "新增失败");
        }
    }

    //删除
    @RequestMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") String id) {
        List<String> ids = Arrays.asList(id.split(","));
        boolean b= assetsUserService.removeByIds(ids);
        return b ? ResultUtil.success() :ResultUtil.error(1,"禁用失败");
    }





}
