package com.dfire.Thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xiaosuda
 * @date 2018/1/8
 */
public class ShutdownNowTest {

    public static void main(String [] args){
        Integer threadNum = Runtime.getRuntime().availableProcessors();
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat("ShutdownNowTestPool");
        ThreadFactory threadFactory = threadFactoryBuilder.build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, threadNum, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        Integer taskNum = 10;

        for (int i = 0; i < taskNum; i++) {
            String taskName = "Task-" + i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int x = 100000;
                    //为了模拟长时间任务，避免任务队列中没有任务
                    while (x-- > 0) {

                    }
                    System.out.println(taskName);
                }
            });
        }

        List<Runnable> runnableList = threadPoolExecutor.shutdownNow();
        System.out.println("--------------------------------未执行的任务--------------------------------");
        for (Runnable runnable : runnableList) {
            new Thread(runnable).start();
        }
    }
}
