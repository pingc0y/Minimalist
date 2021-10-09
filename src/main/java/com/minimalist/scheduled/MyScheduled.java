package com.minimalist.scheduled;

import com.minimalist.assetsAuthorization.service.AssetsAuthorizationService;
import com.minimalist.video.controller.VideoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduled {
    @Autowired
    VideoController videoController;
    @Autowired
    AssetsAuthorizationService assetsAuthorizationService;

    //定时更新录像信息
    @Scheduled(cron="0 0 2 * * ?")
    public void job() {
        videoController.flushRecently();
    }

    //定时检测授权信息
    @Scheduled(cron="0 0 0 * * ?")
    public void assets() {
        assetsAuthorizationService.detectionUpdate();
    }
}