package com.minimalist.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @program: minimalist
 * @description: 文件上传大小配置
 * @author: pingc
 * @create: 2021-10-07 18:40
 **/


@Configuration    //配置类
public class FileConfig {
        /**
         * 设置文件上传的大小限制
         * @return
         */
        @Bean
        public MultipartConfigElement multipartConfigElement(){
            MultipartConfigFactory factory = new MultipartConfigFactory();
            //设置单个文件的上传大小
            factory.setMaxFileSize(DataSize.of(200l, DataUnit.MEGABYTES));
            //设置一次请求上传文件的总容量
            factory.setMaxRequestSize(DataSize.of(200l, DataUnit.MEGABYTES));
            return factory.createMultipartConfig();
        }
    }

