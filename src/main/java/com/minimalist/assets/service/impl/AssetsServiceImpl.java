package com.minimalist.assets.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.mapper.AssetsMapper;
import com.minimalist.assets.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Service
public class AssetsServiceImpl extends ServiceImpl<AssetsMapper, Assets> implements AssetsService {
    @Autowired
    AssetsMapper assetsMapper;

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
        assetsMapper.selectByPgEw(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",page.getRecords());
        map.put("count",(int)page.getTotal());
        return map;
    }

    @Override
    public Integer disabledById(String id) {
        String[] split = id.split(",");
        Integer i = 0;
        for (String s : split) {
            Assets assets = new Assets();
            assets.setId(s);
            assets.setActivate(1);
            i+=assetsMapper.updateById(assets);
        }
        return i;
    }
    @Override
    public Integer activateById(String id) {
        String[] split = id.split(",");
        Integer i = 0;
        for (String s : split) {
            Assets assets = new Assets();
            assets.setId(s);
            assets.setActivate(0);
            i+=assetsMapper.updateById(assets);
        }
        return i;
    }

    @Override
    public Map<String, Object> selectByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("hostname",name);
        List list = assetsMapper.selectList(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",list.size());
        return map;
    }
}
