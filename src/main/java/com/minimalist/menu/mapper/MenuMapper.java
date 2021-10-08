package com.minimalist.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minimalist.menu.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectAll();

    List<Menu> selectByHiherId(Integer hiherId);

}
