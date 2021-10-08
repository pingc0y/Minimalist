package com.minimalist.userGroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.assets.entity.Assets;
import com.minimalist.log.entity.Log;
import com.minimalist.userGroup.entity.UserGroup;

import java.util.Map;

public interface UserGroupService extends IService<UserGroup> {
    Map<String,Object> select(int from, int size, Map<String,String>conditionMap);

    Map<String, Object> selectByName(String name);
}
