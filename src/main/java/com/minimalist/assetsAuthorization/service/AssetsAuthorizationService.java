package com.minimalist.assetsAuthorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.assetsAuthorization.entity.AssetsAuthorization;

import java.util.Map;

public interface AssetsAuthorizationService extends IService<AssetsAuthorization> {

    Map<String, Object> select(int page, int limit, Map<String, String> conditionMap);

}
