package com.dfire.Thread;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xiaosuda
 * @date 2018/1/8
 */
public class ScheduleTest {

    public static void main(String [] args){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(LocalTime.now());
        }, 0,1, TimeUnit.SECONDS);
    }
}
