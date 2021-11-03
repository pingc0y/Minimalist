package com.minimalist.userGroup.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assets.mapper.AssetsMapper;
import com.minimalist.assets.service.AssetsService;
import com.minimalist.user.entity.User;
import com.minimalist.user.mapper.UserMapper;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.mapper.UserGroupMapper;
import com.minimalist.userGroup.service.UserGroupService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Autowired
    UserGroupMapper userGroupMapper;
    @Override
    public Map<String, Object> select(int from, int size, Map<String, String> conditionMap) {
        Page<UserGroup> page = new Page<UserGroup>(from,size);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("creation_time");
        for (String key : conditionMap.keySet()) {
            String value = conditionMap.get(key);
            if (value != null && value.length() != 0) {
                wrapper.like(key, value);
            }
        }
        userGroupMapper.selectByPgEw(page, wrapper);
        Map<String,Object> map = new HashMap<>();
        List<UserGroup> records = page.getRecords();
        for (UserGroup record : records) {
            if(record.getUserId().length()>5){
            record.setUserNum(record.getUserId().split(",").length);
            }else{
                record.setUserNum(0);
            }
        }
        map.put("data",records);
        map.put("count",(int)page.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> selectByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",name);
        List list = userGroupMapper.selectList(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",list.size());
        return map;
    }
}
