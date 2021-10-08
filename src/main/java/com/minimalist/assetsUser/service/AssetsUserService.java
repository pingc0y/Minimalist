package com.minimalist.assetsUser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.assetsUser.entity.AssetsUser;
import com.minimalist.log.entity.Log;

import java.util.Map;

public interface AssetsUserService extends IService<AssetsUser> {

    Map<String, Object> select(int page, int limit, Map<String, String> conditionMap);

    //通过name查询
    Map<String, Object> selectByName(String name);
}
