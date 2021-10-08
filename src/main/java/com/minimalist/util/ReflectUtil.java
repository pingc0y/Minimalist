package com.minimalist.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    //java对象集合转map
    public static List<Map<String, Object>> objectToMaps(Object objs) throws Exception {
        if (objs == null) {
            return null;
        }
        List<Map<String, Object>> maps =new ArrayList<>();

        ((List<Object>)objs).forEach( o->{
            Field[] declaredFields = o.getClass().getDeclaredFields();
            Map<String, Object> map = new HashMap<String, Object>();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            maps.add(map);
        });

        return maps;
    }
}
