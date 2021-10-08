package com.minimalist.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: minimalist
 * @description: 时间工具
 * @author: pingc
 * @create: 2021-10-08 18:30
 **/
public class DateUtil {

    public static Long StringToLong(String data)  {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(data);
            long ts = date.getTime();
            return ts;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }


    }
}
