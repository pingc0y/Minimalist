package com.minimalist.assetsUser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.mapper.AssetsMapper;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.assetsUser.mapper.AssetsUserMapper;
import com.minimalist.assetsUser.service.AssetsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetsUserServiceImpl extends ServiceImpl<AssetsUserMapper, AssetsUser> implements AssetsUserService {
    @Autowired
    AssetsUserMapper assetsUserMapper;

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
        assetsUserMapper.selectByPgEw(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",page.getRecords());
        map.put("count",(int)page.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> selectByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",name);
        List list = assetsUserMapper.selectList(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",(int)list.size());
        return map;
    }
}
