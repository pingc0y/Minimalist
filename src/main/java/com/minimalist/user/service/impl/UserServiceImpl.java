package com.minimalist.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minimalist.user.entity.User;
import com.minimalist.user.mapper.UserMapper;
import com.minimalist.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectBynamePassword(String username, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        wrapper.eq("password",password);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Map<String,Object> selectByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",name);
        List list = userMapper.selectList(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",(int)list.size());
        return map;
    }

    @Override
    public Map<String, Object> select(int from, int size, Map<String, String> conditionMap) {
        Page<User> page = new Page<User>(from,size);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("creation_time");
        for (String key : conditionMap.keySet()) {
            String value = conditionMap.get(key);
            if (value != null && value.length() != 0) {
                wrapper.like(key, value);
            }
        }
        IPage<User> userIPage = userMapper.selectByPgEw(page, wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",page.getRecords());
        map.put("count",(int)page.getTotal());
        return map;
    }

    @Override
    public boolean updateStatusById(String userId, String status) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("id",userId);
        User user = userMapper.selectOne(wrapper);
        user.setStatus(Integer.parseInt(status));
        int i = userMapper.updateById(user);
        return i==0 ? false : true;
    }

    @Override
    public User selectByname(String name) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("username",name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> selectByTime(int i) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.orderByDesc("creation_time");
        Page<User> page = new Page<User>(1,i);
        userMapper.selectPage(page,wrapper);
        return page.getRecords();
    }
}
