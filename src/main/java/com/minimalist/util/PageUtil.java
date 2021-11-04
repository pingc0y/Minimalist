package com.minimalist.util;

import com.minimalist.video.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
         * 自定义List分页工具
         * @author hanwl
         */
        public class PageUtil {

            /**
             * 开始分页
             * @param list
             * @param pageNum 页码
             * @param pageSize 每页多少条数据
             * @return
             */
            public static List<Video> startPage(List<Video> list, Integer pageNum,
                                                     Integer pageSize) {
                if (list == null) {
                    return null;
                }
                if (list.size() == 0) {
                    return null;
                }
    
                Integer count = list.size(); // 记录总数
                Integer pageCount = 0; // 页数
                if (count % pageSize == 0) {
                    pageCount = count / pageSize;
                } else {
                    pageCount = count / pageSize + 1;
                }
    
                int fromIndex = 0; // 开始索引
                int toIndex = 0; // 结束索引
    
                if (pageNum != pageCount) {
                    fromIndex = (pageNum - 1) * pageSize;
                    toIndex = fromIndex + pageSize;
                } else {
                    fromIndex = (pageNum - 1) * pageSize;
                    toIndex = count;
                }

                List<Video> pageList = list.subList(fromIndex, toIndex);
    
                return pageList;
            }
        }