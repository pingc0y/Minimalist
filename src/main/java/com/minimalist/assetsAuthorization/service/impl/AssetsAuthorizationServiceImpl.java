package com.minimalist.assetsAuthorization.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.mapper.AssetsAuthorizationMapper;
import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;

import com.minimalist.assetsUser.mapper.AssetsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssetsAuthorizationServiceImpl extends ServiceImpl<AssetsAuthorizationMapper, AssetsAuthorization> implements AssetsAuthorizationService {
    @Autowired
    AssetsAuthorizationMapper assetsAuthorizationMapper;
    @Autowired
    AssetsService assetsService;

    @Override
    public Map<String, Object> select(int from, int size, Map<String, String> conditionMap) {
        Page<Assets> page = new Page<Assets>(from,size);
        QueryWrapper wrapper = new QueryWrapper();
        String assetsName = conditionMap.remove("assetsName");

        if(conditionMap!=null) {
            for (String key : conditionMap.keySet()) {
                String value = conditionMap.get(key);
                if (value != null && value.length() != 0) {
                    wrapper.like(key, value);
                }
            }
        }
        List<Assets> assetss = (List<Assets>)assetsService.selectByName(assetsName).get("data");
        if(assetsName!=null && assetss!=null && assetss.size()>0 &&assetsName.length()>0){
            ArrayList<String> ids = new ArrayList<>();
            for (Assets assets : assetss) {
                ids.add(assets.getId());
            }
            for (String id : ids) {
                wrapper.eq("assets_id",id);
                wrapper.or();
            }
        }else if (assetsName!=null && assetsName.length()>0){
            wrapper.eq("assets_id",0);
        }
        assetsAuthorizationMapper.selectByPgEw(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",page.getRecords());
        map.put("count",(int)page.getTotal());
        return map;
    }

    @Override
    public void detectionUpdate() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.gt("end_time",new Date());
        wrapper.lt("start_time",new Date());
         assetsAuthorizationMapper.detectionUpdate(new Date());
    }
}
