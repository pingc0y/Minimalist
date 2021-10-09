package com.minimalist.assetsAuthorization.controller;


import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assetsAuthorization")
public class AssetsAuthorizationController {
    @Autowired
    AssetsAuthorizationService assetsAuthorizationService;
    @Autowired
    AssetsService assetsService;

    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap) {
        int page = Integer.parseInt(conditionMap.remove("page"));
        int limit = Integer.parseInt(conditionMap.remove("limit"));
        Map<String, Object> map = assetsAuthorizationService.select(page, limit, conditionMap);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        List<AssetsAuthorization> assetsAuthorizations = (List<AssetsAuthorization>)map.get("data");
        for (AssetsAuthorization assetsAuthorization : assetsAuthorizations) {
            assetsAuthorization.setAssetsName(assetsService.getById(assetsAuthorization.getAssetsId()).getHostname());
        }
        return ResultUtil.success(map.get("data"), (int) map.get("count"));

    }

    //根据ID修改资产
    @RequestMapping("/updateById")
    public Result updateById(@RequestBody Map<String,String> map){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            AssetsAuthorization assetsAuthorization = new AssetsAuthorization();
            assetsAuthorization.setId(map.get("id"));
            assetsAuthorization.setName(map.get("name"));
            if(map.get("userId")!=null && map.get("userId").length() >1){
                assetsAuthorization.setUserId(map.get("userId").replace("\"",""));
            }
            assetsAuthorization.setAssetsId(map.get("assetsId").replace("\"",""));
            assetsAuthorization.setAssetsUserId(map.get("assetsUserId").replace("\"",""));
            if(map.get("activate")!=null && map.get("activate").length() !=0) {
                assetsAuthorization.setActivate(Integer.parseInt(map.get("activate")));
            } else {
                assetsAuthorization.setActivate(1);
            }
            if(map.get("userGroupId")!=null && map.get("userGroupId").length() >1) {
                assetsAuthorization.setUserGroupId(map.get("userGroupId").replace("\"", ""));
            }            assetsAuthorization.setStartTime(simpleDateFormat.parse(map.get("startTime")));
            assetsAuthorization.setEndTime(simpleDateFormat.parse(map.get("endTime")));
            boolean b = assetsAuthorizationService.updateById(assetsAuthorization);
            assetsAuthorizationService.detectionUpdate();
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            AssetsAuthorization assetsAuthorization = new AssetsAuthorization();
            assetsAuthorization.setName(map.get("name"));
            if(map.get("userId")!=null && map.get("userId").length() >1){
                assetsAuthorization.setUserId(map.get("userId").replace("\"",""));
            }
            assetsAuthorization.setAssetsId(map.get("assetsId").replace("\"",""));
            assetsAuthorization.setAssetsUserId(map.get("assetsUserId").replace("\"",""));
            if(map.get("activate")!=null && map.get("activate").length() !=0) {
                assetsAuthorization.setActivate(Integer.parseInt(map.get("activate")));
            } else {
                assetsAuthorization.setActivate(1);
                }
            if(map.get("userGroupId")!=null && map.get("userGroupId").length() >1) {
                assetsAuthorization.setUserGroupId(map.get("userGroupId").replace("\"", ""));
            }
            assetsAuthorization.setStartTime(simpleDateFormat.parse(map.get("startTime")));
            assetsAuthorization.setEndTime(simpleDateFormat.parse(map.get("endTime")));
            boolean b = assetsAuthorizationService.save(assetsAuthorization);
            assetsAuthorizationService.detectionUpdate();
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
        boolean b= assetsAuthorizationService.removeByIds(ids);
        return b ? ResultUtil.success() :ResultUtil.error(1,"禁用失败");
    }

}
