package com.minimalist.timing;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserIsTiming {

    public static HashSet<String> userCount = new HashSet<>();

    @Scheduled(cron = "0 0/20 * * * ?")
    public void userTiming(){
        userCount.clear();
    }

}
