package com.minimalist.log.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minimalist.log.entity.Log;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LogMapper extends BaseMapper<Log> {
    @Select("select distinct user_name  from log where creation_time > #{time}")
    List<String> selectUserCount(String time);

    @Select("select count(*) from log where creation_time > #{time}")
    Integer selectCount(String time);

    @Select("select distinct user_name as name,count(*) AS count   FROM log  where creation_time > #{time} GROUP BY user_name  ORDER BY count  DESC  LIMIT 1")
    Map<String,String> selectUserKing(String time);
}
