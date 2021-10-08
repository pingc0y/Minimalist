package com.minimalist.assetsAuthorization.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;
import com.minimalist.assetsAuthorization.mapper.AssetsAuthorizationMapper;
import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;

import com.minimalist.assetsUser.mapper.AssetsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AssetsAuthorizationServiceImpl extends ServiceImpl<AssetsAuthorizationMapper, AssetsAuthorization> implements AssetsAuthorizationService {
    @Autowired
    AssetsAuthorizationMapper assetsAuthorizationMapper;

    @Override
    public Map<String, Object> select(int from, int size, Map<String, String> conditionMap) {
        Page<Assets> page = new Page<Assets>(from,size);
        QueryWrapper wrapper = new QueryWrapper();
        if(conditionMap!=null) {
            for (String key : conditionMap.keySet()) {
                String value = conditionMap.get(key);
                if (value != null && value.length() != 0) {
                    wrapper.like(key, value);
                }
            }
        }
        assetsAuthorizationMapper.selectByPgEw(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",page.getRecords());
        map.put("count",(int)page.getTotal());
        return map;
    }
}
