package com.minimalist.log.controller;

import com.minimalist.log.service.LogService;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    @RequestMapping("/loginLog")
    public Result loginLog(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        Map<String,Object> logs = logService.selectByLogin(page,limit);
      return ResultUtil.success(logs.get("data"),(int)logs.get("count"));
    }

    @RequestMapping("/operateLog")
    public Result operateLog(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        Map<String,Object> logs = logService.selectByOperate(page,limit);
        return ResultUtil.success(logs.get("data"),(int)logs.get("count"));
    }




}
