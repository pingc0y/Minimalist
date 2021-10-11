package com.minimalist.video.controller;

import com.minimalist.assets.service.AssetsService;
import com.minimalist.assetsUser.service.AssetsUserService;
import com.minimalist.terminal.servlet.HttpTunnelServlet;
import com.minimalist.user.service.UserService;
import com.minimalist.userGroup.entity.UserGroup;
import com.minimalist.userGroup.service.UserGroupService;
import com.minimalist.util.DateUtil;
import com.minimalist.util.FileUtil;
import com.minimalist.util.Result;
import com.minimalist.util.ResultUtil;
import com.minimalist.video.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    UserService userService;
    @Autowired
    AssetsService assetsService;
    @Autowired
    AssetsUserService assetsUserService;

    public Map<String,Video> videos  = new HashMap<>();


    @RequestMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) Map<String,String> conditionMap){
        if(videos.size()==0){
            flushAll();
        }
        flushRecently();
        ArrayList<Video> videoList = new ArrayList<>();
        for (String key : this.videos.keySet()) {
            Video video = videos.get(key);
            boolean t = true;
            String userName = conditionMap.get("userName");
            if(t && userName!=null && userName.length() >0){
                if(!video.getUserName().contains(userName)){
                    t = false;
                }
            }
            String assetsName = conditionMap.get("assetsName");
            if(t && assetsName!=null && assetsName.length() >0){
                if(!video.getAssetsName().contains(assetsName)){
                   t = false;
                }
            }
            String assetsUserName = conditionMap.get("assetsUserName");
            if(t && assetsUserName!=null && assetsUserName.length() >0){
                if(!video.getAssetsUserName().contains(assetsUserName)){
                    t = false;
                }
            }
            String createTime = conditionMap.get("createTime");
            if(t && createTime!=null && createTime.length() >0){


                if(video.getCreateTime().getTime() < DateUtil.StringToLong(createTime)){
                    t = false;
                }
            }
            String updateTime = conditionMap.get("updateTime");
            if(t && updateTime!=null && updateTime.length() >0){
                if(video.getUpdateTime().getTime() > DateUtil.StringToLong(updateTime)){
                    t = false;
                }
            }
            if(t){
                videoList.add(video);
            }
        }
            return ResultUtil.success(videoList,videoList.size());
    }

    @RequestMapping("/flushAll")
    public Result flushAll(){
        List<Map<String, String>> allFile = FileUtil.getAllFile(uploadPath);
        for (Map<String, String> map : allFile) {
            String[] ids = map.get("fileName").split(",");
            String userName = ids[0];
            String assetsName = assetsService.getById(ids[1]).getHostname();
            String assetsUserName = assetsUserService.getById(ids[2]).getUsername();
            String filePath = map.get("filePath");
            String createTime = ids[3];
            String updateTime = map.get("fileUpDate");
            Video video = new Video(map.get("fileName"),userName,assetsName,assetsUserName,filePath,new Date(Long.parseLong(String.valueOf(createTime))),new Date(Long.parseLong(String.valueOf(updateTime))));
            videos.put(map.get("fileName"),video);
        }
        return ResultUtil.success("成功",0);
    }


    @RequestMapping("/FlushRecently")
    public Result flushRecently(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dt = new Date();
        String date = sdf.format(dt);
        List<Map<String, String>> allFile = FileUtil.getAllFile(uploadPath+date);
        List<Map<String, String>> allFile1 = FileUtil.getAllFile(uploadPath+(Integer.valueOf(date)-1));

        for (Map<String, String> map : allFile) {
            String[] ids = map.get("fileName").split(",");
            String userName = ids[0];
            String assetsName = assetsService.getById(ids[1]).getHostname();
            String assetsUserName = assetsUserService.getById(ids[2]).getUsername();
            String filePath = map.get("filePath");
            String createTime = ids[3];
            String updateTime = map.get("fileUpDate");
            Video video = new Video(map.get("fileName"),userName,assetsName,assetsUserName,filePath,new Date(Long.parseLong(String.valueOf(createTime))),new Date(Long.parseLong(String.valueOf(updateTime))));
            videos.put(map.get("fileName"),video);
        }
        for (Map<String, String> map : allFile1) {
            String[] ids = map.get("fileName").split(",");
            String userName = ids[0];
            String assetsName = assetsService.getById(ids[1]).getHostname();
            String assetsUserName = assetsUserService.getById(ids[2]).getUsername();
            String filePath = map.get("filePath");
            String createTime = ids[3];
            String updateTime = map.get("fileUpDate");
            Video video = new Video(map.get("fileName"),userName,assetsName,assetsUserName,filePath,new Date(Long.parseLong(String.valueOf(createTime))),new Date(Long.parseLong(String.valueOf(updateTime))));
            videos.put(map.get("fileName"),video);
        }

        return ResultUtil.success("成功",0);
    }




}
