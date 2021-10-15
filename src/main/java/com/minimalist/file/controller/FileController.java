package com.minimalist.file.controller;

import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: minimalist
 * @description: rdp文件上传
 * @author: pingc
 * @create: 2021-10-07 17:35
 **/
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${upload.filePath}")
    private String filePath;

    @RequestMapping("/uploadFile/{directory}")
    public Result uploadFile(MultipartFile setfilex,@PathVariable(value = "directory") String directory) throws Exception{
        //把上传的文件保存到本地
        try {
            File dir = new File(filePath+ directory);
            if (!dir.exists()) {// 判断目录是否存在
                dir.mkdir();
            }
            setfilex.transferTo(new File(filePath+ directory + "/" + setfilex.getOriginalFilename()));

        }catch (Exception e){
            return ResultUtil.error(1,"上传失败");

        }
        return ResultUtil.success(setfilex.getOriginalFilename()+"上传成功",1);
    }
}
