package com.minimalist.log.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minimalist.log.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LogMapper extends BaseMapper<Log> {
    @Select("select distinct user_name  from log where  creation_time > #{time}")
    List<String> selectUserCount(String time);

    @Select("select count(*) from log where  creation_time > #{time}")
    Integer selectCount(String time);

    @Select("select distinct user_name as name,count(*) AS count  FROM log  where creation_time > #{time} GROUP BY user_name  ORDER BY count  DESC  LIMIT 1")
    Map<String,String> selectUserKing(String time);


    @Select("select (select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day1} AND creation_time < #{day2} ) as #{day1},(select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day2} AND creation_time < #{day3} ) as #{day2},(select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day3} AND creation_time < #{day4} ) as #{day3},(select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day4} AND creation_time < #{day5} ) as #{day4},(select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day5} AND creation_time < #{day6} ) as #{day5},(select COUNT(id) from log where path = '/user/login' AND  creation_time >#{day6} AND creation_time < #{day7} ) as #{day6},(select COUNT(id) from log where path = '/user/login' AND  creation_time > #{day7} ) as #{day7}")
    Map<String,String> selectLogin(@Param("day1")String day1,@Param("day2")String day2,@Param("day3")String day3,@Param("day4")String day4,@Param("day5")String day5,@Param("day6")String day6,@Param("day7")String day7);

    @Select("select (select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day1} AND creation_time < #{day2} ) as #{day1},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day2} AND creation_time < #{day3} ) as #{day2},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day3} AND creation_time < #{day4} ) as #{day3},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day4} AND creation_time < #{day5} ) as #{day4},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day5} AND creation_time < #{day6} ) as #{day5},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time >#{day6} AND creation_time < #{day7} ) as #{day6},(select COUNT(id) from log where path LIKE '/view/terminal/index/%' AND  creation_time > #{day7} ) as #{day7}")
    Map<String,String> selectTrunnel(@Param("day1")String day1,@Param("day2")String day2,@Param("day3")String day3,@Param("day4")String day4,@Param("day5")String day5,@Param("day6")String day6,@Param("day7")String day7);

    @Select("select (select COUNT(id) from log  where creation_time > #{day1} AND creation_time < #{day2} ) as #{day1},(select COUNT(id) from log where  creation_time > #{day2} AND creation_time < #{day3} ) as #{day2},(select COUNT(id) from log where  creation_time > #{day3} AND creation_time < #{day4} ) as #{day3},(select COUNT(id) from log where  creation_time > #{day4} AND creation_time < #{day5} ) as #{day4},(select COUNT(id) from log where  creation_time > #{day5} AND creation_time < #{day6} ) as #{day5},(select COUNT(id) from log where  creation_time >#{day6} AND creation_time < #{day7} ) as #{day6},(select COUNT(id) from log where  creation_time > #{day7} ) as #{day7}")
    Map<String,String> selectActive(@Param("day1")String day1,@Param("day2")String day2,@Param("day3")String day3,@Param("day4")String day4,@Param("day5")String day5,@Param("day6")String day6,@Param("day7")String day7);

}
