package com.dfire.Thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class MyThreadPoolTest {

    public static void main(String [] args){
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        ThreadFactory threadFactory = threadFactoryBuilder.setNameFormat("2dFire").build();

        MyThreadPool myThreadPool = new MyThreadPool(1, 10 , 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        Integer taskNum = 10;

        for (int i = 0; i < taskNum; i++) {

            String threadName = "thread-" + i;
            myThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName(threadName);
                    int x = Integer.MAX_VALUE;
                    while (x-- > 0) {

                    }
                    System.out.println(threadName + "执行完成");
                }
            });
        }
        myThreadPool.shutdown();
    }

}
