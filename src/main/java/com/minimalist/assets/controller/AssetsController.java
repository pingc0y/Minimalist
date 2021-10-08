package com.minimalist.assets.controller;

import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.log.service.LogService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import com.minimalist.util.TelnetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/assets")
public class AssetsController {
    @Autowired
    AssetsService assetsService;

    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap) {
        int page = Integer.parseInt(conditionMap.remove("page"));
        int limit = Integer.parseInt(conditionMap.remove("limit"));
        Map<String, Object> map = assetsService.select(page, limit, conditionMap);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
            ArrayList<Assets> data = (ArrayList<Assets>) (map.get("data"));
            for (Assets ass : data) {
                boolean telnet = TelnetUtil.telnet(ass.getAddress(), ass.getPort(), 200);
                if (telnet) {
                    ass.setStatus(0);
                } else {
                    ass.setStatus(1);
                }
            }
            return ResultUtil.success(data, (int) map.get("count"));

    }

    @RequestMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        Map<String, Object> map = assetsService.selectByName(name);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
        ArrayList<Assets> data = (ArrayList<Assets>) (map.get("data"));
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<HashMap<String, Object>>();
        for (Assets ass : data) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("name",ass.getHostname());
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
        try{
            Assets assets = new Assets();
            assets.setId(map.get("id"));
            assets.setHostname(map.get("hostname"));
            assets.setPort(Integer.parseInt(map.get("port")));
            assets.setProtocol(map.get("protocol"));
            assets.setAddress(map.get("address"));
            assets.setRemark(map.get("remark"));
            if(map.get("activate")!=null){
                assets.setActivate(Integer.parseInt(map.get("activate")));
            }else{
                assets.setActivate(1);
            }
            boolean b = assetsService.updateById(assets);
            return b ? ResultUtil.success() :ResultUtil.error(1,"修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(1,"修改失败");
        }
    }

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,String> map){
        try {
            Assets assets = new Assets();
            assets.setHostname(map.get("hostname"));
            assets.setPort(Integer.parseInt(map.get("port")));
            assets.setProtocol(map.get("protocol"));
            assets.setAddress(map.get("address"));
            assets.setRemark(map.get("remark"));
            if (map.get("activate") != null) {
                assets.setActivate(Integer.parseInt(map.get("activate")));
            } else {
                assets.setActivate(1);
            }
            boolean b = assetsService.save(assets);
            return b ? ResultUtil.success() :ResultUtil.error(1,"新增失败");
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(1,"新增失败");
        }

    }

    //禁用
    @RequestMapping("/disabled/{id}")
    public Result disabled(@PathVariable("id") String id) {
        int b = assetsService.disabledById(id);
        return b!=0 ? ResultUtil.success() :ResultUtil.error(1,"禁用失败");
    }

    //启用
    @RequestMapping("/activate/{id}")
    public Result start(@PathVariable("id") String id) {
        int b = assetsService.activateById(id);
        return b!=0 ? ResultUtil.success() :ResultUtil.error(1,"启用失败");
    }

    //删除
    @RequestMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") String id) {
        List<String> ids = Arrays.asList(id.split(","));
        boolean b= assetsService.removeByIds(ids);
        return b ? ResultUtil.success() :ResultUtil.error(1,"禁用失败");
    }

}
