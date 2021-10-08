package com.minimalist.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.log.entity.Log;
import com.minimalist.log.mapper.LogMapper;
import com.minimalist.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Autowired
    LogMapper logMapper;
    @Override
    public Map<String,Object> selectByLogin(Integer page, Integer limit) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.orderByDesc("creation_time");
        wrapper.eq("path","/user/login");
        wrapper.orderByDesc("creation_time");
        Page<Log> pagee =new Page<Log>(page,limit);
        logMapper.selectPage(pagee,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",pagee.getRecords());
        map.put("count",(int)pagee.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> selectByOperate(Integer page, Integer limit) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.ne("path","/user/login");
        wrapper.orderByDesc("creation_time");
        Page<Log> pagee =new Page<Log>(page,limit);
        logMapper.selectPage(pagee,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("data",pagee.getRecords());
        map.put("count",(int)pagee.getTotal());
        return map;
    }

    @Override
    public int selectByUserCount() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(date.getTime());
        return logMapper.selectUserCount(today).size();
    }

    @Override
    public Map<String, String> selectByUserKing() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(date.getTime());
        return logMapper.selectUserKing(today);
    }

    @Override
    public int selectByCount() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(date.getTime());
        return logMapper.selectCount(today);
    }
}
