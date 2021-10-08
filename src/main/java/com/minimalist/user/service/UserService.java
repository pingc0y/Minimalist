package com.minimalist.user.service;

import com.minimalist.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    //验证登录用
    User selectBynamePassword(String username, String password);

    //用户查询
    Map<String,Object> selectByName(String name);

    //查询用户
    Map<String,Object> select(int from, int size, Map<String,String>conditionMap);

    boolean updateStatusById(String userId, String status);

    User selectByname(String name);

    List<User> selectByTime(int i);



    //查询全部


}
