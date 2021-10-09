package com.minimalist.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minimalist.menu.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<Menu> selectAll();

    Menu selectById(String id);


    List<Menu> selectUser();
}
