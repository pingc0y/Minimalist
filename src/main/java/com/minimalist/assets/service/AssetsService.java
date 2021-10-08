package com.minimalist.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.assets.entity.Assets;
import com.minimalist.log.entity.Log;

import java.util.Map;

public interface AssetsService extends IService<Assets> {
    //查询资产
    Map<String,Object> select(int from, int size,Map<String,String>conditionMap);

    //禁用
    Integer disabledById(String id);

    //启用
    Integer activateById(String id);

    //通过name查询资产
    Map<String, Object> selectByName(String name);
}
