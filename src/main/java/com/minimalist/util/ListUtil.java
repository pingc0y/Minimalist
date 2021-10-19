package com.minimalist.util;

import com.minimalist.video.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: minimalist
 * @description: 集合分页工具
 * @author: pingc
 * @create: 2021-10-19 17:09
 **/
public class ListUtil {
    /**

     * 利用subList方法进行分页

     * @param list 分页数据

     * @param pagesize  页面大小

     * @param currentPage   当前页面

     */

    public static ArrayList<Video> pageBySubList(ArrayList<Video> list, int pagesize, int currentPage) {

        int totalcount = list.size();


        int pagecount = 0;

        ArrayList<Video> subList = new ArrayList<>();

        int m = totalcount % pagesize;

        if (m > 0) {

            pagecount = totalcount / pagesize + 1;

        } else {

            pagecount = totalcount / pagesize;

        }

        if (m == 0) {

            subList.addAll(list.subList((currentPage - 1) * pagesize, pagesize * (currentPage)));

        } else {

            if (currentPage == pagecount) {

                subList.addAll(list.subList((currentPage - 1) * pagesize, totalcount));

            } else {

                subList.addAll(list.subList((currentPage - 1) * pagesize, pagesize * (currentPage)));

            }

        }

        return subList;

    }
}
