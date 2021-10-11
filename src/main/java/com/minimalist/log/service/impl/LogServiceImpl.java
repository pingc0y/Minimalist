package com.minimalist.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.log.entity.Log;
import com.minimalist.log.mapper.LogMapper;
import com.minimalist.log.service.LogService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Map<String, Map<String, String>> selectByStatistics() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = new Date();
        day1.setTime(new Date().getTime()-24*60*60*1000*7);
        String day1s = simpleDateFormat.format(day1);
        Date day2 = new Date();
        day2.setTime(new Date().getTime()-24*60*60*1000*6);
        String day2s = simpleDateFormat.format(day2);
        Date day3 = new Date();
        day3.setTime(new Date().getTime()-24*60*60*1000*5);
        String day3s = simpleDateFormat.format(day3);
        Date day4 = new Date();
        day4.setTime(new Date().getTime()-24*60*60*1000*4);
        String day4s = simpleDateFormat.format(day4);
        Date day5 = new Date();
        day5.setTime(new Date().getTime()-24*60*60*1000*3);
        String day5s = simpleDateFormat.format(day5);
        Date day6 = new Date();
        day6.setTime(new Date().getTime()-24*60*60*1000*2);
        String day6s = simpleDateFormat.format(day6);
        Date day7 = new Date();
        day7.setTime(new Date().getTime()-24*60*60*1000*1);
        String day7s = simpleDateFormat.format(day7);
        Map<String, String> loginMap = logMapper.selectLogin(day1s, day2s, day3s, day4s, day5s, day6s, day7s);
        Map<String, String> trunnelMap = logMapper.selectTrunnel(day1s, day2s, day3s, day4s, day5s, day6s, day7s);
        Map<String, String> activeMap = logMapper.selectActive(day1s, day2s, day3s, day4s, day5s, day6s, day7s);
        HashMap<String, Map<String, String>> statisticsMap = new HashMap<>();
        statisticsMap.put("login",loginMap);
        statisticsMap.put("trunnel",trunnelMap);
        statisticsMap.put("active",activeMap);
        return statisticsMap;
    }
}
