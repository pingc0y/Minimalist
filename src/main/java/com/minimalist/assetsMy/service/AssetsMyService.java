package com.minimalist.assetsMy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.assetsMy.entity.AssetsMy;

import java.util.Map;

public interface AssetsMyService {
    //查询资产
    Map<String,Object> select(int from, int size,Map<String,String>conditionMap);

}
