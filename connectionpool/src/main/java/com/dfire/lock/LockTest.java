package com.dfire.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xiaosuda
 * @date 2018/1/11
 */
public class LockTest {

    private Lock lock = new ReentrantLock();

    public static void main(String [] args){
        LockTest lockTest = new LockTest();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 2; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    lockTest.sleepTime();
                }
            });
        }

    }

    public void sleepTime() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println(name + "我要睡觉了");
            Thread.sleep(5000);
            System.out.println(name + "我睡醒了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
