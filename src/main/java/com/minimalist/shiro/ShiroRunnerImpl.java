package com.minimalist.shiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ShiroRunnerImpl implements ApplicationRunner {
    @Autowired
    ShiroUp shiroUp;

    @Override
    public void run(ApplicationArguments args){
        shiroUp.updatePermission();
    }
}