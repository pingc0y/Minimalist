package com.minimalist.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minimalist.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select name from user where id = #{id}")
     String selectNameById(String id);

    IPage<User> selectByPgEw(Page<?> page, @Param(Constants.WRAPPER) Wrapper ew);

    User selectByEw(@Param(Constants.WRAPPER) Wrapper ew);

}
