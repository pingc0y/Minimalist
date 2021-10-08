package com.minimalist.scheduled;

import com.minimalist.video.controller.VideoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduled {
    @Autowired
    VideoController videoController;

    //定时更新录像信息
    @Scheduled(cron="0 0 0 * * ?")
    public void job() {
        videoController.flushRecently();
    }
}