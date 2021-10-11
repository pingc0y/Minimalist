package com.minimalist.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.log.entity.Log;

import java.util.Map;

public interface LogService extends IService<Log> {
    Map<String,Object>  selectByLogin(Integer page, Integer limit);

    Map<String,Object>  selectByOperate(Integer page, Integer limit);

    int selectByUserCount();

    Map<String,String> selectByUserKing();

    int selectByCount();

    Map<String,Map<String,String>> selectByStatistics();
}
