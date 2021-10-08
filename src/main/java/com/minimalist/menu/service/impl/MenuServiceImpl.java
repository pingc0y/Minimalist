package com.minimalist.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minimalist.menu.entity.Menu;
import com.minimalist.menu.mapper.MenuMapper;
import com.minimalist.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> selectAll() {
        List<Menu> menus = menuMapper.selectAll();
        return menus;
    }

    @Override
    public Menu selectById(String id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<Menu> selectByType0() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",0);
        return menuMapper.selectList(wrapper);
    }
}
