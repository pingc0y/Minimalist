package com.minimalist.assetsMy.controller;

import com.minimalist.assetsMy.entity.AssetsMy;
import com.minimalist.assetsMy.service.AssetsMyService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import com.minimalist.util.TelnetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/assetsMy")
public class AssetsMyController {
    @Autowired
    AssetsMyService assetsMyService;

    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap) {
        int page = Integer.parseInt(conditionMap.remove("page"));
        int limit = Integer.parseInt(conditionMap.remove("limit"));
        Map<String, Object> map = assetsMyService.select(page, limit, conditionMap);
        if((map.get("data")).toString() == "[]"){
            return ResultUtil.error(1,"无数据");
        }
            ArrayList<AssetsMy> data = (ArrayList<AssetsMy>) (map.get("data"));
            for (AssetsMy ass : data) {
                boolean telnet = TelnetUtil.telnet(ass.getAddress(), ass.getPort(), 200);
                if (telnet) {
                    ass.setStatus(0);
                } else {
                    ass.setStatus(1);
                }
            }
            return ResultUtil.success(data, (int) map.get("count"));

    }


}
