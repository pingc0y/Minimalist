package com.minimalist.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: minimalist
 * @description: 文件工具类
 * @author: pingc
 * @create: 2021-10-08 11:47
 **/
public class FileUtil {
    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @return
     */
    public static List<Map<String,String>> getAllFile(String directoryPath) {

        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(getAllFile(file.getAbsolutePath()));
            } else {
                HashMap<String, String> map = new HashMap<>();
                //绝对路径
                String filePath  = file.getAbsolutePath();
                //修改时间
                Long update = file.lastModified();
                //文件名称
                String temp[]=filePath.split("\\\\");
                String fileName = temp[temp.length-1];
                String filenameq = fileName.substring(0,fileName.lastIndexOf("."));    //去后缀
                map.put("filePath",filePath);
                map.put("fileName",filenameq);
                map.put("fileUpDate",String.valueOf(update));
                list.add(map);
            }
        }
        return list;
    }

}
