package com.minimalist.userGroup.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minimalist.assets.entity.Assets;
import com.minimalist.log.entity.Log;
import com.minimalist.user.entity.User;
import com.minimalist.userGroup.entity.UserGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper extends BaseMapper<UserGroup> {

    IPage<UserGroup> selectByPgEw(Page<?> page, @Param(Constants.WRAPPER) Wrapper ew);
}
