package com.minimalist.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationContextHeader implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHeader.context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz){
        return context.getBeansOfType(clazz);
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz){
        return context.getBean(name, clazz);
    }
}